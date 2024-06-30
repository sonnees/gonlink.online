package auth

import (
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
	"net/url"
	"os"

	"github.com/sonnees/grpc-gateway/internal/session"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/github"
)

var (
	githubOauthConfig *oauth2.Config
)

func HandleGithubLogin() http.HandlerFunc {
	sessionName := os.Getenv("SESSION_NAME_AUTH")

	githubOauthConfig = &oauth2.Config{
		ClientID:     os.Getenv("GITHUB_CLIENT_ID"),
		ClientSecret: os.Getenv("GITHUB_CLIENT_SECRET"),
		RedirectURL:  os.Getenv("REDIRECT_URL"),
		Scopes:       []string{"user:email", "read:user"},
		Endpoint:     github.Endpoint,
	}

	return func(w http.ResponseWriter, r *http.Request) {
        sess, _ := session.Store.Get(r, sessionName)
        sess.Values["original_url"] = r.Referer()
		log.Println(r)
        if err := sess.Save(r, w); err != nil {
            http.Error(w, "Không thể lưu session", http.StatusInternalServerError)
            return
        }
        url := githubOauthConfig.AuthCodeURL("state", oauth2.AccessTypeOnline)
        http.Redirect(w, r, url, http.StatusTemporaryRedirect)
    }
}

func HandleGithubCallback() http.HandlerFunc {
	sessionName := os.Getenv("SESSION_NAME_AUTH")

	return func(w http.ResponseWriter, r *http.Request) {
		state := r.URL.Query().Get("state")
		if state != "state" {
			http.Error(w, "Invalid state parameter", http.StatusBadRequest)
			return
		}
		code := r.URL.Query().Get("code")
		token, err := githubOauthConfig.Exchange(r.Context(), code)
		if err != nil {
			http.Error(w, "Failed to exchange token: "+err.Error(), http.StatusInternalServerError)
			return
		}
		if token == nil {
			http.Error(w, "Failed to exchange token: token is nil", http.StatusInternalServerError)
			return
		}

		// Lấy thông tin người dùng từ GitHub API
		client := githubOauthConfig.Client(r.Context(), token)
		resp, err := client.Get("https://api.github.com/user")
		if err != nil {
			http.Error(w, "Failed to get user info: "+err.Error(), http.StatusInternalServerError)
			return
		}
		defer resp.Body.Close()

		userData, err := io.ReadAll(resp.Body)
		if err != nil {
			http.Error(w, "Failed to read user info: "+err.Error(), http.StatusInternalServerError)
			return
		}

		var userInfo map[string]interface{}
		if err := json.Unmarshal(userData, &userInfo); err != nil {
			http.Error(w, "Failed to parse user info: "+err.Error(), http.StatusInternalServerError)
			return
		}

		// Lấy danh sách email của người dùng từ GitHub API
		emailResp, err := client.Get("https://api.github.com/user/emails")
		if err != nil {
			http.Error(w, "Failed to get user emails: "+err.Error(), http.StatusInternalServerError)
			return
		}
		defer emailResp.Body.Close()

		emailData, err := io.ReadAll(emailResp.Body)
		if err != nil {
			http.Error(w, "Failed to read user emails: "+err.Error(), http.StatusInternalServerError)
			return
		}

		var emails []map[string]interface{}
		if err := json.Unmarshal(emailData, &emails); err != nil {
			http.Error(w, "Failed to parse user emails: "+err.Error(), http.StatusInternalServerError)
			return
		}

		// Tìm email chính
		var primaryEmail string
		for _, email := range emails {
			if email["primary"].(bool) {
				primaryEmail = email["email"].(string)
				break
			}
		}

		sess, _ := session.Store.Get(r, sessionName)
        sess.Values["authenticated"] = true
        sess.Values["name"] = userInfo["login"]
        sess.Values["email"] = primaryEmail
        sess.Values["avatar"] = userInfo["avatar_url"]

        if err := sess.Save(r, w); err != nil {
            http.Error(w, "Không thể lưu session", http.StatusInternalServerError)
            return
        }

        originalURL, ok := sess.Values["original_url"].(string)
        if !ok || originalURL == "" {
			
            originalURL = "http://localhost:5173/page/home" // Mặc định quay về trang chủ của frontend
        }
		log.Println(originalURL, ok)

        // Chuyển hướng về trang frontend với thông tin xác thực
        redirectURL := fmt.Sprintf("%s?authenticated=true&name=%s&email=%s&avatar=%s",
            "http://localhost:5173/page/home",
            url.QueryEscape(sess.Values["name"].(string)),
            url.QueryEscape(sess.Values["email"].(string)),
            url.QueryEscape(sess.Values["avatar"].(string)))

        http.Redirect(w, r, redirectURL, http.StatusFound)
	}
}
