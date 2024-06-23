gen-proto:
	protoc -I ../grpc-gateway \
		--go_out=./gen/go/ --go_opt=paths=source_relative \
		--go-grpc_out=./gen/go/ --go-grpc_opt=paths=source_relative \
		--grpc-gateway_out ./gen/go --grpc-gateway_opt logtostderr=true --grpc-gateway_opt paths=source_relative \
		--openapiv2_out=./openapi --openapiv2_opt=logtostderr=true \
		../grpc-gateway/shorten-service.proto
		
run-proxy:
	go run proxy.go

all: gen-proto 
	$(MAKE) run-proxy