package auth

import (
    "encoding/json"
    "fmt"
    "io"
    "net/http"
    "os"
    "log"

    "golang.org/x/oauth2"
)

var (
    linkedinOauthConfig *oauth2.Config
)

type LinkedInUserInfo struct {
    ID            string `json:"id"`
    LocalizedFirstName string `json:"localizedFirstName"`
    LocalizedLastName  string `json:"localizedLastName"`
    ProfilePicture struct {
        DisplayImage struct {
            Elements []struct {
                Identifiers []struct {
                    Identifier string `json:"identifier"`
                } `json:"identifiers"`
            } `json:"elements"`
        } `json:"displayImage~"`
    } `json:"profilePicture"`
}

func HandleLinkedInLogin() http.HandlerFunc {
    linkedinOauthConfig = &oauth2.Config{
        ClientID:     os.Getenv("LINKEDIN_CLIENT_ID"),
        ClientSecret: os.Getenv("LINKEDIN_CLIENT_SECRET"),
        RedirectURL:  os.Getenv("LINKEDIN_REDIRECT_URL"),
        Scopes: []string{
            "r_liteprofile",  // Chỉ sử dụng scope này
        },
        Endpoint: oauth2.Endpoint{
            AuthURL:  "https://www.linkedin.com/oauth/v2/authorization",
            TokenURL: "https://www.linkedin.com/oauth/v2/accessToken",
        },
    }

    return func(w http.ResponseWriter, r *http.Request) {
        state, err := generateState()
        if err != nil {
            http.Error(w, "Failed to generate state", http.StatusInternalServerError)
            return
        }

        stateStore.Set(state, r.Referer())
        url := linkedinOauthConfig.AuthCodeURL(state, oauth2.AccessTypeOnline)
        log.Printf("Generated LinkedIn auth URL: %s", url)
        http.Redirect(w, r, url, http.StatusTemporaryRedirect)
    }
}

func HandleLinkedInCallback() http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        // Log query parameters for debugging
        log.Printf("Callback received with query params: %v", r.URL.Query())

        state := r.URL.Query().Get("state")
        log.Printf("Received state: %s", state)

        // Check for LinkedIn error response
        if errMsg := r.URL.Query().Get("error"); errMsg != "" {
            errDesc := r.URL.Query().Get("error_description")
            log.Printf("LinkedIn returned error: %s - %s", errMsg, errDesc)
            http.Error(w, fmt.Sprintf("LinkedIn authentication error: %s", errDesc), http.StatusBadRequest)
            return
        }

        originalURL, valid := stateStore.Verify(state)
        if !valid {
            http.Error(w, "Invalid state parameter", http.StatusBadRequest)
            return
        }

        code := r.URL.Query().Get("code")
        log.Printf("Received code: %s", code)
        if code == "" {
            log.Printf("Error: No code parameter received")
            http.Error(w, "No code parameter received", http.StatusBadRequest)
            return
        }

        token, err := linkedinOauthConfig.Exchange(r.Context(), code)
        if err != nil {
            log.Printf("Token exchange error: %v", err)
            http.Error(w, "Failed to exchange token: "+err.Error(), http.StatusInternalServerError)
            return
        }

        client := linkedinOauthConfig.Client(r.Context(), token)
        userInfo, err := fetchLinkedInUserInfo(client)
        if err != nil {
            log.Printf("Failed to fetch user info: %v", err)
            http.Error(w, "Failed to fetch user info: "+err.Error(), http.StatusInternalServerError)
            return
        }

        // Combine first and last name
        fullName := fmt.Sprintf("%s %s", userInfo.LocalizedFirstName, userInfo.LocalizedLastName)
        
        // Get profile picture URL (if available)
        var pictureURL string
        if len(userInfo.ProfilePicture.DisplayImage.Elements) > 0 && 
           len(userInfo.ProfilePicture.DisplayImage.Elements[0].Identifiers) > 0 {
            pictureURL = userInfo.ProfilePicture.DisplayImage.Elements[0].Identifiers[0].Identifier
        }

        // Sử dụng ID LinkedIn làm email vì chúng ta không có quyền truy cập email
        email := fmt.Sprintf("linkedin_%s@placeholder.com", userInfo.ID)

        // Create JWT token
        tokenJWT, err := createToken(email, fullName, pictureURL)
        if err != nil {
            log.Printf("Failed to create token: %v", err)
            http.Error(w, "Failed to create token: "+err.Error(), http.StatusInternalServerError)
            return
        }

        if originalURL == "" {
            originalURL = os.Getenv("DOMAIN_FE")
        }
        originalURL += os.Getenv("PAGE_HOME")
        redirectURL := originalURL + "?token=" + tokenJWT
        log.Printf("Redirecting to: %s", redirectURL)
        http.Redirect(w, r, redirectURL, http.StatusFound)
    }
}

func fetchLinkedInUserInfo(client *http.Client) (*LinkedInUserInfo, error) {
    profileURL := "https://api.linkedin.com/v2/me?projection=(id,localizedFirstName,localizedLastName,profilePicture(displayImage~:playableStreams))"
    profileResp, err := client.Get(profileURL)
    if err != nil {
        return nil, fmt.Errorf("failed to fetch profile: %v", err)
    }
    defer profileResp.Body.Close()

    profileData, err := io.ReadAll(profileResp.Body)
    if err != nil {
        return nil, fmt.Errorf("failed to read profile response: %v", err)
    }

    var userInfo LinkedInUserInfo
    if err := json.Unmarshal(profileData, &userInfo); err != nil {
        return nil, fmt.Errorf("failed to parse profile data: %v", err)
    }

    return &userInfo, nil
}