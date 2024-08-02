package main

import (
	"context"
	"flag"
	"net/http"
	"os"

	"github.com/gorilla/sessions"
	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
	"github.com/joho/godotenv"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"google.golang.org/grpc/grpclog"

	"github.com/sonnees/grpc-gateway/internal/auth"
	"github.com/sonnees/grpc-gateway/internal/session"
	"github.com/sonnees/grpc-gateway/pkg/cors"

	gw "github.com/sonnees/grpc-gateway/api/generated"
)

var (
    grpcServerEndpoint = flag.String("grpc-server-endpoint", "localhost:9090", "gRPC server endpoint")
    grpcServer2Endpoint = flag.String("grpc-server2-endpoint", "localhost:9091", "Second gRPC server endpoint")
)

func run() error {
	godotenv.Load()
    sessionKey := os.Getenv("SESSION_SECRET")
    store := sessions.NewCookieStore([]byte(sessionKey))
    store.Options = &sessions.Options{
        Path:     "/",
        MaxAge:   3600 * 24, // 24 giờ
        HttpOnly: true,
        Secure:   false, // Đặt thành true khi sử dụng HTTPS
        SameSite: http.SameSiteLaxMode,
    }
    session.Setup(store)


	ctx := context.Background()
	ctx, cancel := context.WithCancel(ctx)
	defer cancel()

	mux := runtime.NewServeMux()
	opts := []grpc.DialOption{grpc.WithTransportCredentials(insecure.NewCredentials())}

	err := gw.RegisterUrlShortenerServiceHandlerFromEndpoint(ctx, mux, *grpcServerEndpoint, opts)
	if err != nil {
		return err
	}
	err = gw.RegisterAccountServiceHandlerFromEndpoint(ctx, mux, *grpcServer2Endpoint, opts)
    if err != nil {
        return err
    }
	err = gw.RegisterQRCodeServiceHandlerFromEndpoint(ctx, mux, *grpcServer2Endpoint, opts)
    if err != nil {
        return err
    }



	httpMux := http.NewServeMux()
	httpMux.HandleFunc("/auth/github/login", auth.HandleGithubLogin())
	httpMux.HandleFunc("/auth/github/login/callback", auth.HandleGithubCallback())

	httpMux.Handle("/", auth.AuthMiddleware(cors.SetupCORS(mux)))

	return http.ListenAndServe(":8080", cors.SetupCORS(httpMux))
}

func main() {
	flag.Parse()
	if err := run(); err != nil {
		grpclog.Fatal(err)
	}
}
