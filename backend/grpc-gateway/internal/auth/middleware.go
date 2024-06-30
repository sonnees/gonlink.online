package auth

import (
    "context"
    "github.com/sonnees/grpc-gateway/internal/session"
    "net/http"
    "os"
    "strings"
)

var unprotectedPaths = []string{
    "/auth/github/login",
    "/auth/github/login/callback",
    "/welcome",
}

func comparativePaths(paths []string, path string) bool {
    for _, p := range paths {
        if strings.HasPrefix(path, p) {
            return true
        }
    }
    return false
}

func AuthMiddleware(next http.Handler) http.Handler {
    sessionName := os.Getenv("SESSION_NAME_AUTH")
    return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
        if comparativePaths(unprotectedPaths, r.URL.Path) {
            next.ServeHTTP(w, r)
            return
        }

        sess, err := session.Store.Get(r, sessionName)
        if err != nil {
            http.Error(w, "Không thể lấy session", http.StatusInternalServerError)
            return
        }

        auth, ok := sess.Values["authenticated"].(bool)
        if !ok || !auth {
            http.Error(w, "Unauthorized", http.StatusUnauthorized)
            return
        }

        // Thêm thông tin người dùng vào context của request
        ctx := r.Context()
        ctx = context.WithValue(ctx, UserContextKey, map[string]interface{}{
            "name":   sess.Values["name"],
            "email":  sess.Values["email"],
            "avatar": sess.Values["avatar"],
        })
        r = r.WithContext(ctx)

        next.ServeHTTP(w, r)
    })
}