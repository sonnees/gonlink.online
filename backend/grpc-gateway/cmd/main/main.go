package main

import (
    "context"
    "flag"
    "net/http"
    "os"
    "log"

    "github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
    "github.com/joho/godotenv"
    "google.golang.org/grpc"
    "google.golang.org/grpc/credentials/insecure"
    "google.golang.org/grpc/grpclog"

    "github.com/sonnees/grpc-gateway/internal/auth"
    "github.com/sonnees/grpc-gateway/pkg/cors"

    gw "github.com/sonnees/grpc-gateway/api/generated"
)

func run() error {
    // Load environment variables
    godotenv.Load()

    // Get gRPC server endpoint from environment or use default
    grpcServerEndpoint := os.Getenv("GRPC_SERVER_ENDPOINT")
    if grpcServerEndpoint == "" {
        grpcServerEndpoint = "grpc-service:9090"
    }
    log.Printf("Connecting to gRPC server at: %s", grpcServerEndpoint)

    ctx := context.Background()
    ctx, cancel := context.WithCancel(ctx)
    defer cancel()

    mux := runtime.NewServeMux()
    opts := []grpc.DialOption{
        grpc.WithTransportCredentials(insecure.NewCredentials()),
        grpc.WithBlock(), // Make the dial blocking
    }

    // Initialize all handlers with error checking
    services := []struct {
        name     string
        register func(ctx context.Context, mux *runtime.ServeMux, endpoint string, opts []grpc.DialOption) error
    }{
        {"Account", gw.RegisterAccountHandlerFromEndpoint},
        {"QRCode", gw.RegisterQRCodeHandlerFromEndpoint},
        {"UrlShortener", gw.RegisterUrlShortenerHandlerFromEndpoint},
        {"Traffic", gw.RegisterTrafficHandlerFromEndpoint},
    }

    for _, service := range services {
        if err := service.register(ctx, mux, grpcServerEndpoint, opts); err != nil {
            log.Printf("Failed to register %s handler: %v", service.name, err)
            return err
        }
        log.Printf("Successfully registered %s handler", service.name)
    }

    httpMux := http.NewServeMux()
    httpMux.HandleFunc("/auth/github/login", auth.HandleGithubLogin())
    httpMux.HandleFunc("/auth/github/login/callback", auth.HandleGithubCallback())
    httpMux.HandleFunc("/auth/google/login", auth.HandleGoogleLogin())
    httpMux.HandleFunc("/auth/google/login/callback", auth.HandleGoogleCallback())
    httpMux.HandleFunc("/auth/linkedin/login", auth.HandleLinkedInLogin())
    httpMux.HandleFunc("/auth/linkedin/login/callback", auth.HandleLinkedInCallback())
    httpMux.Handle("/", auth.AuthMiddleware(cors.SetupCORS(mux)))

    log.Printf("Starting HTTP server on :8080")
    return http.ListenAndServe(":8080", cors.SetupCORS(httpMux))
}

func main() {
    flag.Parse()
    if err := run(); err != nil {
        grpclog.Fatal(err)
    }
}