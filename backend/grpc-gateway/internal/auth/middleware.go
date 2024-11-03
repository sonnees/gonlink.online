package auth

import (
	"net/http"
	"strings"
)

var unprotectedPaths = []string{
	"/auth/github/login",
	"/auth/github/login/callback",
	"/auth/google/login",
	"/auth/google/login/callback",
	"/api/v1/shortens/gen",
	"/api/v1/shortens/get",
	"/api/v1/shortens/check-need-password",
	"/api/v1/shortens/check-exist-short-code",
	"/api/v1/qr-codes",
}

func comparativePaths(paths []string, path string) bool {
	print(path)
	for _, p := range paths {
		if strings.HasPrefix(path, p) {
			return true
		}
	}
	return false
}

func AuthMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		println(r.URL.Path)
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