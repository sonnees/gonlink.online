// github.go
package auth

import (
    "encoding/json"
    "crypto/rand"
    "encoding/base64"
    "errors"
    "fmt"
    "io"
    "net/http"
    "os"
    "time"
    "sync"

    "github.com/golang-jwt/jwt/v5"
    "golang.org/x/oauth2"
    "golang.org/x/oauth2/github"
)

// StateStore manages OAuth state tokens
type StateStore struct {
    states map[string]stateData
    mutex  sync.RWMutex
}

type stateData struct {
    OriginalURL string
    ExpiresAt   time.Time
}

var (
    githubOauthConfig *oauth2.Config
    stateStore       = &StateStore{
        states: make(map[string]stateData),
    }
)

// Token generation and validation functions
func createToken(email, name, avatar string) (string, error) {
    jwtSecret := os.Getenv("JWT_SECRET")
    if jwtSecret == "" {
        return "", errors.New("JWT_SECRET is not set")
    }

    role := "user" // Default role for GitHub users

    claims := jwt.MapClaims{
        "email":  email,
        "name":   name,
        "avatar": avatar,
        "role":   role,
        "iat":    time.Now().Unix(),
        "exp":    time.Now().Add(time.Hour * 24).Unix(), // Token expires in 24 hours
    }

    token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
    return token.SignedString([]byte(jwtSecret))
}

func ValidateToken(tokenString string) (*jwt.Token, error) {
    jwtSecret := os.Getenv("JWT_SECRET")
    if jwtSecret == "" {
        return nil, errors.New("JWT_SECRET is not set")
    }

    return jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
        if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
            return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
        }
        return []byte(jwtSecret), nil
    })
}

func generateState() (string, error) {
    b := make([]byte, 32)
    if _, err := rand.Read(b); err != nil {
        return "", err
    }
    return base64.URLEncoding.EncodeToString(b), nil
}

func (s *StateStore) Set(state, originalURL string) {
    s.mutex.Lock()
    defer s.mutex.Unlock()
    s.states[state] = stateData{
        OriginalURL: originalURL,
        ExpiresAt:   time.Now().Add(15 * time.Minute),
    }
}

func (s *StateStore) Verify(state string) (string, bool) {
    s.mutex.Lock()
    defer s.mutex.Unlock()
    
    data, exists := s.states[state]
    if !exists {
        return "", false
    }
    
    if time.Now().After(data.ExpiresAt) {
        delete(s.states, state)
        return "", false
    }
    
    delete(s.states, state)
    return data.OriginalURL, true
}

func HandleGithubLogin() http.HandlerFunc {
    githubOauthConfig = &oauth2.Config{
        ClientID:     os.Getenv("GITHUB_CLIENT_ID"),
        ClientSecret: os.Getenv("GITHUB_CLIENT_SECRET"),
        RedirectURL:  os.Getenv("REDIRECT_URL"),
        Scopes:       []string{"user:email", "read:user"},
        Endpoint:     github.Endpoint,
    }
    
    return func(w http.ResponseWriter, r *http.Request) {
        state, err := generateState()
        if err != nil {
            http.Error(w, "Failed to generate state", http.StatusInternalServerError)
            return
        }
        
        stateStore.Set(state, r.Referer())
        url := githubOauthConfig.AuthCodeURL(state, oauth2.AccessTypeOnline)
        http.Redirect(w, r, url, http.StatusTemporaryRedirect)
    }
}

func HandleGithubCallback() http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        println("HandleGithubCallback")
        state := r.URL.Query().Get("state")
        originalURL, valid := stateStore.Verify(state)
        if !valid {
            http.Error(w, "Invalid state parameter", http.StatusBadRequest)
            return
        }

        code := r.URL.Query().Get("code")
        token, err := githubOauthConfig.Exchange(r.Context(), code)
        if err != nil {
            http.Error(w, "Failed to exchange token: "+err.Error(), http.StatusInternalServerError)
            return
        }

        // Get user info from GitHub API
        client := githubOauthConfig.Client(r.Context(), token)
        userInfo, primaryEmail, err := fetchGithubUserInfo(client)
        if err != nil {
            http.Error(w, "Failed to fetch user info: "+err.Error(), http.StatusInternalServerError)
            return
        }

        // Create JWT token
        tokenJWT, err := createToken(primaryEmail, userInfo["login"].(string), userInfo["avatar_url"].(string))
        if err != nil {
            http.Error(w, "Failed to create token: "+err.Error(), http.StatusInternalServerError)
            return
        }

        if originalURL == "" {
            originalURL =  os.Getenv("DOMAIN_FE")
        }
        originalURL +=  os.Getenv("PAGE_HOME")
        redirectURL := fmt.Sprintf("%s?token=%s", originalURL, tokenJWT)
        println("redirectURL: ", redirectURL)
        http.Redirect(w, r, redirectURL, http.StatusFound)
    }
}

func fetchGithubUserInfo(client *http.Client) (map[string]interface{}, string, error) {
    println("fetchGithubUserInfo")
    // Fetch user profile
    resp, err := client.Get("https://api.github.com/user")
    if err != nil {
        return nil, "", err
    }
    defer resp.Body.Close()

    userData, err := io.ReadAll(resp.Body)
    if err != nil {
        return nil, "", err
    }

    var userInfo map[string]interface{}
    if err := json.Unmarshal(userData, &userInfo); err != nil {
        return nil, "", err
    }

    // Fetch user emails
    emailResp, err := client.Get("https://api.github.com/user/emails")
    if err != nil {
        return nil, "", err
    }
    defer emailResp.Body.Close()

    emailData, err := io.ReadAll(emailResp.Body)
    if err != nil {
        return nil, "", err
    }

    var emails []map[string]interface{}
    if err := json.Unmarshal(emailData, &emails); err != nil {
        return nil, "", err
    }

    var primaryEmail string
    for _, email := range emails {
        if email["primary"].(bool) {
            primaryEmail = email["email"].(string)
            break
        }
    }

    return userInfo, primaryEmail, nil
}