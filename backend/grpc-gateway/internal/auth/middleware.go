package auth

import (
	"net/http"
	"strings"
)

var unprotectedPaths = []string{
    "/auth/github/login",
    "/auth/github/login/callback",
    "/shorten-service/api/v1/get-original-url",
    "/shorten-service/api/v1/generate-shortcode",
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
    return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
        if comparativePaths(unprotectedPaths, r.URL.Path) {
            next.ServeHTTP(w, r)
            return
        }

        // Lấy token từ header Authorization
        authHeader := r.Header.Get("Authorization")
        if authHeader == "" {
            http.Error(w, "Missing authorization token", http.StatusUnauthorized)
            return
        }
        next.ServeHTTP(w, r)
    })
}
