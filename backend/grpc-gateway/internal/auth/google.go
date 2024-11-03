package auth

import (
    "encoding/json"
    "io"
    "net/http"
    "os"

    "golang.org/x/oauth2"
    "golang.org/x/oauth2/google"
)

var (
    googleOauthConfig *oauth2.Config
)

type GoogleUserInfo struct {
    Email         string `json:"email"`
    Name          string `json:"name"`
    Picture       string `json:"picture"`
    VerifiedEmail bool   `json:"verified_email"`
}

func HandleGoogleLogin() http.HandlerFunc {
    googleOauthConfig = &oauth2.Config{
        ClientID:     os.Getenv("GG_CLIENT_ID"),
        ClientSecret: os.Getenv("GG_CLIENT_SECRET"),
        RedirectURL:  os.Getenv("GG_REDIRECT_URL"),
        Scopes: []string{
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile",
        },
        Endpoint: google.Endpoint,
    }

    return func(w http.ResponseWriter, r *http.Request) {
        state, err := generateState()
        if err != nil {
            http.Error(w, "Failed to generate state", http.StatusInternalServerError)
            return
        }

        stateStore.Set(state, r.Referer())
        url := googleOauthConfig.AuthCodeURL(state, oauth2.AccessTypeOnline)
        http.Redirect(w, r, url, http.StatusTemporaryRedirect)
    }
}

func HandleGoogleCallback() http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        state := r.URL.Query().Get("state")
        originalURL, valid := stateStore.Verify(state)
        if !valid {
            http.Error(w, "Invalid state parameter", http.StatusBadRequest)
            return
        }

        code := r.URL.Query().Get("code")
        token, err := googleOauthConfig.Exchange(r.Context(), code)
        if err != nil {
            http.Error(w, "Failed to exchange token: "+err.Error(), http.StatusInternalServerError)
            return
        }

        client := googleOauthConfig.Client(r.Context(), token)
        userInfo, err := fetchGoogleUserInfo(client)
        if err != nil {
            http.Error(w, "Failed to fetch user info: "+err.Error(), http.StatusInternalServerError)
            return
        }

        // Create JWT token
        tokenJWT, err := createToken(userInfo.Email, userInfo.Name, userInfo.Picture)
        if err != nil {
            http.Error(w, "Failed to create token: "+err.Error(), http.StatusInternalServerError)
            return
        }

        if originalURL == "" {
            originalURL = os.Getenv("DOMAIN_FE")
        }
        originalURL += os.Getenv("PAGE_HOME")
        redirectURL := originalURL + "?token=" + tokenJWT
        http.Redirect(w, r, redirectURL, http.StatusFound)
    }
}

func fetchGoogleUserInfo(client *http.Client) (*GoogleUserInfo, error) {
    resp, err := client.Get("https://www.googleapis.com/oauth2/v2/userinfo")
    if err != nil {
        return nil, err
    }
    defer resp.Body.Close()

    userData, err := io.ReadAll(resp.Body)
    if err != nil {
        return nil, err
    }

    var userInfo GoogleUserInfo
    if err := json.Unmarshal(userData, &userInfo); err != nil {
        return nil, err
    }

    return &userInfo, nil
}