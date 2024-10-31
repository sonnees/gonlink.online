gen-proto:
	protoc -I ../grpc-gateway/api/proto \
		--go_out=./api/generated --go_opt=paths=source_relative \
		--go-grpc_out=./api/generated --go-grpc_opt=paths=source_relative \
		--grpc-gateway_out ./api/generated --grpc-gateway_opt logtostderr=true --grpc-gateway_opt paths=source_relative \
		--openapiv2_out=./pkg/protocol/rest/openapi --openapiv2_opt=logtostderr=true \
		../grpc-gateway/api/proto/common.proto \
		../grpc-gateway/api/proto/account-service.proto \
		../grpc-gateway/api/proto/qr-code-service.proto \
		../grpc-gateway/api/proto/url-shortener-service.proto \
		../grpc-gateway/api/proto/traffic-service.proto
run-proxy:
	go run ../grpc-gateway/cmd/main/main.go
all: gen-proto
	$(MAKE) run-proxy