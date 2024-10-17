// Code generated by protoc-gen-go-grpc. DO NOT EDIT.
// versions:
// - protoc-gen-go-grpc v1.4.0
// - protoc             v5.27.1
// source: traffic-service.proto

package proto

import (
	context "context"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.62.0 or later.
const _ = grpc.SupportPackageIsVersion8

const (
	Traffic_SearchGeneralTraffics_FullMethodName     = "/online.gonlink.Traffic/searchGeneralTraffics"
	Traffic_GetAllMonthTraffics_FullMethodName       = "/online.gonlink.Traffic/getAllMonthTraffics"
	Traffic_GetDayTrafficInRange_FullMethodName      = "/online.gonlink.Traffic/getDayTrafficInRange"
	Traffic_GetRealTimeTraffic_FullMethodName        = "/online.gonlink.Traffic/getRealTimeTraffic"
	Traffic_GetRealTimeTrafficAccount_FullMethodName = "/online.gonlink.Traffic/getRealTimeTrafficAccount"
)

// TrafficClient is the client API for Traffic service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type TrafficClient interface {
	SearchGeneralTraffics(ctx context.Context, in *GeneralTrafficsSearchRequest, opts ...grpc.CallOption) (*BaseGrpc, error)
	GetAllMonthTraffics(ctx context.Context, in *MonthTrafficsGetAllRequest, opts ...grpc.CallOption) (*BaseGrpc, error)
	GetDayTrafficInRange(ctx context.Context, in *DayTrafficInRangeRequest, opts ...grpc.CallOption) (*BaseGrpc, error)
	GetRealTimeTraffic(ctx context.Context, in *RealTimeTrafficRequest, opts ...grpc.CallOption) (*BaseGrpc, error)
	GetRealTimeTrafficAccount(ctx context.Context, in *RealTimeTrafficAccountRequest, opts ...grpc.CallOption) (*BaseGrpc, error)
}

type trafficClient struct {
	cc grpc.ClientConnInterface
}

func NewTrafficClient(cc grpc.ClientConnInterface) TrafficClient {
	return &trafficClient{cc}
}

func (c *trafficClient) SearchGeneralTraffics(ctx context.Context, in *GeneralTrafficsSearchRequest, opts ...grpc.CallOption) (*BaseGrpc, error) {
	cOpts := append([]grpc.CallOption{grpc.StaticMethod()}, opts...)
	out := new(BaseGrpc)
	err := c.cc.Invoke(ctx, Traffic_SearchGeneralTraffics_FullMethodName, in, out, cOpts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *trafficClient) GetAllMonthTraffics(ctx context.Context, in *MonthTrafficsGetAllRequest, opts ...grpc.CallOption) (*BaseGrpc, error) {
	cOpts := append([]grpc.CallOption{grpc.StaticMethod()}, opts...)
	out := new(BaseGrpc)
	err := c.cc.Invoke(ctx, Traffic_GetAllMonthTraffics_FullMethodName, in, out, cOpts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *trafficClient) GetDayTrafficInRange(ctx context.Context, in *DayTrafficInRangeRequest, opts ...grpc.CallOption) (*BaseGrpc, error) {
	cOpts := append([]grpc.CallOption{grpc.StaticMethod()}, opts...)
	out := new(BaseGrpc)
	err := c.cc.Invoke(ctx, Traffic_GetDayTrafficInRange_FullMethodName, in, out, cOpts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *trafficClient) GetRealTimeTraffic(ctx context.Context, in *RealTimeTrafficRequest, opts ...grpc.CallOption) (*BaseGrpc, error) {
	cOpts := append([]grpc.CallOption{grpc.StaticMethod()}, opts...)
	out := new(BaseGrpc)
	err := c.cc.Invoke(ctx, Traffic_GetRealTimeTraffic_FullMethodName, in, out, cOpts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *trafficClient) GetRealTimeTrafficAccount(ctx context.Context, in *RealTimeTrafficAccountRequest, opts ...grpc.CallOption) (*BaseGrpc, error) {
	cOpts := append([]grpc.CallOption{grpc.StaticMethod()}, opts...)
	out := new(BaseGrpc)
	err := c.cc.Invoke(ctx, Traffic_GetRealTimeTrafficAccount_FullMethodName, in, out, cOpts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// TrafficServer is the server API for Traffic service.
// All implementations must embed UnimplementedTrafficServer
// for forward compatibility
type TrafficServer interface {
	SearchGeneralTraffics(context.Context, *GeneralTrafficsSearchRequest) (*BaseGrpc, error)
	GetAllMonthTraffics(context.Context, *MonthTrafficsGetAllRequest) (*BaseGrpc, error)
	GetDayTrafficInRange(context.Context, *DayTrafficInRangeRequest) (*BaseGrpc, error)
	GetRealTimeTraffic(context.Context, *RealTimeTrafficRequest) (*BaseGrpc, error)
	GetRealTimeTrafficAccount(context.Context, *RealTimeTrafficAccountRequest) (*BaseGrpc, error)
	mustEmbedUnimplementedTrafficServer()
}

// UnimplementedTrafficServer must be embedded to have forward compatible implementations.
type UnimplementedTrafficServer struct {
}

func (UnimplementedTrafficServer) SearchGeneralTraffics(context.Context, *GeneralTrafficsSearchRequest) (*BaseGrpc, error) {
	return nil, status.Errorf(codes.Unimplemented, "method SearchGeneralTraffics not implemented")
}
func (UnimplementedTrafficServer) GetAllMonthTraffics(context.Context, *MonthTrafficsGetAllRequest) (*BaseGrpc, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetAllMonthTraffics not implemented")
}
func (UnimplementedTrafficServer) GetDayTrafficInRange(context.Context, *DayTrafficInRangeRequest) (*BaseGrpc, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetDayTrafficInRange not implemented")
}
func (UnimplementedTrafficServer) GetRealTimeTraffic(context.Context, *RealTimeTrafficRequest) (*BaseGrpc, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetRealTimeTraffic not implemented")
}
func (UnimplementedTrafficServer) GetRealTimeTrafficAccount(context.Context, *RealTimeTrafficAccountRequest) (*BaseGrpc, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetRealTimeTrafficAccount not implemented")
}
func (UnimplementedTrafficServer) mustEmbedUnimplementedTrafficServer() {}

// UnsafeTrafficServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to TrafficServer will
// result in compilation errors.
type UnsafeTrafficServer interface {
	mustEmbedUnimplementedTrafficServer()
}

func RegisterTrafficServer(s grpc.ServiceRegistrar, srv TrafficServer) {
	s.RegisterService(&Traffic_ServiceDesc, srv)
}

func _Traffic_SearchGeneralTraffics_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(GeneralTrafficsSearchRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(TrafficServer).SearchGeneralTraffics(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Traffic_SearchGeneralTraffics_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(TrafficServer).SearchGeneralTraffics(ctx, req.(*GeneralTrafficsSearchRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _Traffic_GetAllMonthTraffics_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(MonthTrafficsGetAllRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(TrafficServer).GetAllMonthTraffics(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Traffic_GetAllMonthTraffics_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(TrafficServer).GetAllMonthTraffics(ctx, req.(*MonthTrafficsGetAllRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _Traffic_GetDayTrafficInRange_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(DayTrafficInRangeRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(TrafficServer).GetDayTrafficInRange(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Traffic_GetDayTrafficInRange_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(TrafficServer).GetDayTrafficInRange(ctx, req.(*DayTrafficInRangeRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _Traffic_GetRealTimeTraffic_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(RealTimeTrafficRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(TrafficServer).GetRealTimeTraffic(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Traffic_GetRealTimeTraffic_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(TrafficServer).GetRealTimeTraffic(ctx, req.(*RealTimeTrafficRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _Traffic_GetRealTimeTrafficAccount_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(RealTimeTrafficAccountRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(TrafficServer).GetRealTimeTrafficAccount(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: Traffic_GetRealTimeTrafficAccount_FullMethodName,
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(TrafficServer).GetRealTimeTrafficAccount(ctx, req.(*RealTimeTrafficAccountRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// Traffic_ServiceDesc is the grpc.ServiceDesc for Traffic service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var Traffic_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "online.gonlink.Traffic",
	HandlerType: (*TrafficServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "searchGeneralTraffics",
			Handler:    _Traffic_SearchGeneralTraffics_Handler,
		},
		{
			MethodName: "getAllMonthTraffics",
			Handler:    _Traffic_GetAllMonthTraffics_Handler,
		},
		{
			MethodName: "getDayTrafficInRange",
			Handler:    _Traffic_GetDayTrafficInRange_Handler,
		},
		{
			MethodName: "getRealTimeTraffic",
			Handler:    _Traffic_GetRealTimeTraffic_Handler,
		},
		{
			MethodName: "getRealTimeTrafficAccount",
			Handler:    _Traffic_GetRealTimeTrafficAccount_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "traffic-service.proto",
}
