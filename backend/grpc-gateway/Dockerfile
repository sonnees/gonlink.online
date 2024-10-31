# Multi-stage build
FROM golang:1.22.4-alpine AS builder

# Install git and necessary build tools
RUN apk add --no-cache git

# Set working directory
WORKDIR /app

# Copy go.mod and go.sum files
COPY go.mod go.sum ./

# Download dependencies
RUN go mod download

# Copy source code
COPY . .

# Build the application
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o grpc-gateway ./cmd/main/main.go

# Final stage
FROM alpine:latest

# Install CA certificates for HTTPS
RUN apk --no-cache add ca-certificates

WORKDIR /root/

# Copy the binary from builder
COPY --from=builder /app/grpc-gateway .
COPY --from=builder /app/.env .

# Set necessary environment variables
ENV GITHUB_CLIENT_ID="Ov23linxe63dBqWDk4fJ"
ENV GITHUB_CLIENT_SECRET="932406842c0a0f27e52990b3c5211a8854c3ef70"
ENV REDIRECT_URL="http://localhost:8080/auth/github/login/callback"
ENV JWT_SECRET="7b1f58b60b06295403c1887b7f258a955e2f594cec6c1fe12d80f6a247795fce"
ENV GRPC_SERVER_ENDPOINT="7b1f58b60b06295403c1887b7f258a955e2f594cec6c1fe12d80f6a247795fce"

# Expose the port
EXPOSE 8080

# Run the application
CMD ["./grpc-gateway"]