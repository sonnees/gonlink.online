// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.34.2
// 	protoc        v5.27.1
// source: url-shortener-service.proto

package proto

import (
	_ "google.golang.org/genproto/googleapis/api/annotations"
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

// === GenerateShortCode ===
type GenerateShortCodeRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	OriginalUrl string `protobuf:"bytes,1,opt,name=originalUrl,proto3" json:"originalUrl,omitempty"`
	TrafficDate string `protobuf:"bytes,2,opt,name=trafficDate,proto3" json:"trafficDate,omitempty"`
	ZoneId      string `protobuf:"bytes,3,opt,name=zoneId,proto3" json:"zoneId,omitempty"`
}

func (x *GenerateShortCodeRequest) Reset() {
	*x = GenerateShortCodeRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GenerateShortCodeRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GenerateShortCodeRequest) ProtoMessage() {}

func (x *GenerateShortCodeRequest) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GenerateShortCodeRequest.ProtoReflect.Descriptor instead.
func (*GenerateShortCodeRequest) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{0}
}

func (x *GenerateShortCodeRequest) GetOriginalUrl() string {
	if x != nil {
		return x.OriginalUrl
	}
	return ""
}

func (x *GenerateShortCodeRequest) GetTrafficDate() string {
	if x != nil {
		return x.TrafficDate
	}
	return ""
}

func (x *GenerateShortCodeRequest) GetZoneId() string {
	if x != nil {
		return x.ZoneId
	}
	return ""
}

type GenerateShortCodeAccountRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	OriginalUrl string `protobuf:"bytes,1,opt,name=originalUrl,proto3" json:"originalUrl,omitempty"`
	TrafficDate string `protobuf:"bytes,2,opt,name=trafficDate,proto3" json:"trafficDate,omitempty"`
	ZoneId      string `protobuf:"bytes,3,opt,name=zoneId,proto3" json:"zoneId,omitempty"`
}

func (x *GenerateShortCodeAccountRequest) Reset() {
	*x = GenerateShortCodeAccountRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GenerateShortCodeAccountRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GenerateShortCodeAccountRequest) ProtoMessage() {}

func (x *GenerateShortCodeAccountRequest) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GenerateShortCodeAccountRequest.ProtoReflect.Descriptor instead.
func (*GenerateShortCodeAccountRequest) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{1}
}

func (x *GenerateShortCodeAccountRequest) GetOriginalUrl() string {
	if x != nil {
		return x.OriginalUrl
	}
	return ""
}

func (x *GenerateShortCodeAccountRequest) GetTrafficDate() string {
	if x != nil {
		return x.TrafficDate
	}
	return ""
}

func (x *GenerateShortCodeAccountRequest) GetZoneId() string {
	if x != nil {
		return x.ZoneId
	}
	return ""
}

type GenerateShortCodeResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	ShortCode   string `protobuf:"bytes,1,opt,name=shortCode,proto3" json:"shortCode,omitempty"`
	Base64Image string `protobuf:"bytes,2,opt,name=base64Image,proto3" json:"base64Image,omitempty"`
	IsOwner     bool   `protobuf:"varint,3,opt,name=isOwner,proto3" json:"isOwner,omitempty"`
}

func (x *GenerateShortCodeResponse) Reset() {
	*x = GenerateShortCodeResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GenerateShortCodeResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GenerateShortCodeResponse) ProtoMessage() {}

func (x *GenerateShortCodeResponse) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GenerateShortCodeResponse.ProtoReflect.Descriptor instead.
func (*GenerateShortCodeResponse) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{2}
}

func (x *GenerateShortCodeResponse) GetShortCode() string {
	if x != nil {
		return x.ShortCode
	}
	return ""
}

func (x *GenerateShortCodeResponse) GetBase64Image() string {
	if x != nil {
		return x.Base64Image
	}
	return ""
}

func (x *GenerateShortCodeResponse) GetIsOwner() bool {
	if x != nil {
		return x.IsOwner
	}
	return false
}

// === GetOriginalUrl ===
type GetOriginalUrlRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	ShortCode  string `protobuf:"bytes,1,opt,name=shortCode,proto3" json:"shortCode,omitempty"`
	ClientTime string `protobuf:"bytes,2,opt,name=clientTime,proto3" json:"clientTime,omitempty"`
	ZoneId     string `protobuf:"bytes,3,opt,name=zoneId,proto3" json:"zoneId,omitempty"`
}

func (x *GetOriginalUrlRequest) Reset() {
	*x = GetOriginalUrlRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetOriginalUrlRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetOriginalUrlRequest) ProtoMessage() {}

func (x *GetOriginalUrlRequest) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetOriginalUrlRequest.ProtoReflect.Descriptor instead.
func (*GetOriginalUrlRequest) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{3}
}

func (x *GetOriginalUrlRequest) GetShortCode() string {
	if x != nil {
		return x.ShortCode
	}
	return ""
}

func (x *GetOriginalUrlRequest) GetClientTime() string {
	if x != nil {
		return x.ClientTime
	}
	return ""
}

func (x *GetOriginalUrlRequest) GetZoneId() string {
	if x != nil {
		return x.ZoneId
	}
	return ""
}

type GetOriginalUrlResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	OriginalUrl string `protobuf:"bytes,1,opt,name=originalUrl,proto3" json:"originalUrl,omitempty"`
}

func (x *GetOriginalUrlResponse) Reset() {
	*x = GetOriginalUrlResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[4]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetOriginalUrlResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetOriginalUrlResponse) ProtoMessage() {}

func (x *GetOriginalUrlResponse) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[4]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetOriginalUrlResponse.ProtoReflect.Descriptor instead.
func (*GetOriginalUrlResponse) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{4}
}

func (x *GetOriginalUrlResponse) GetOriginalUrl() string {
	if x != nil {
		return x.OriginalUrl
	}
	return ""
}

// === GeneralTraffics ===
type GeneralTrafficsSearchRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Page          *int32  `protobuf:"varint,1,opt,name=page,proto3,oneof" json:"page,omitempty"`
	Size          *int32  `protobuf:"varint,2,opt,name=size,proto3,oneof" json:"size,omitempty"`
	SortDirection *string `protobuf:"bytes,3,opt,name=sortDirection,proto3,oneof" json:"sortDirection,omitempty"`
}

func (x *GeneralTrafficsSearchRequest) Reset() {
	*x = GeneralTrafficsSearchRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[5]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GeneralTrafficsSearchRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GeneralTrafficsSearchRequest) ProtoMessage() {}

func (x *GeneralTrafficsSearchRequest) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[5]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GeneralTrafficsSearchRequest.ProtoReflect.Descriptor instead.
func (*GeneralTrafficsSearchRequest) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{5}
}

func (x *GeneralTrafficsSearchRequest) GetPage() int32 {
	if x != nil && x.Page != nil {
		return *x.Page
	}
	return 0
}

func (x *GeneralTrafficsSearchRequest) GetSize() int32 {
	if x != nil && x.Size != nil {
		return *x.Size
	}
	return 0
}

func (x *GeneralTrafficsSearchRequest) GetSortDirection() string {
	if x != nil && x.SortDirection != nil {
		return *x.SortDirection
	}
	return ""
}

type PageInfo struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	CurrentPage   int32 `protobuf:"varint,1,opt,name=currentPage,proto3" json:"currentPage,omitempty"`
	TotalPages    int32 `protobuf:"varint,2,opt,name=totalPages,proto3" json:"totalPages,omitempty"`
	TotalElements int64 `protobuf:"varint,3,opt,name=totalElements,proto3" json:"totalElements,omitempty"`
	PageSize      int32 `protobuf:"varint,4,opt,name=pageSize,proto3" json:"pageSize,omitempty"`
}

func (x *PageInfo) Reset() {
	*x = PageInfo{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[6]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *PageInfo) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*PageInfo) ProtoMessage() {}

func (x *PageInfo) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[6]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use PageInfo.ProtoReflect.Descriptor instead.
func (*PageInfo) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{6}
}

func (x *PageInfo) GetCurrentPage() int32 {
	if x != nil {
		return x.CurrentPage
	}
	return 0
}

func (x *PageInfo) GetTotalPages() int32 {
	if x != nil {
		return x.TotalPages
	}
	return 0
}

func (x *PageInfo) GetTotalElements() int64 {
	if x != nil {
		return x.TotalElements
	}
	return 0
}

func (x *PageInfo) GetPageSize() int32 {
	if x != nil {
		return x.PageSize
	}
	return 0
}

type GeneralTraffic struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	ShortCode   string `protobuf:"bytes,1,opt,name=shortCode,proto3" json:"shortCode,omitempty"`
	OriginalUrl string `protobuf:"bytes,2,opt,name=originalUrl,proto3" json:"originalUrl,omitempty"`
	TrafficDate string `protobuf:"bytes,3,opt,name=trafficDate,proto3" json:"trafficDate,omitempty"`
	Traffic     int64  `protobuf:"varint,4,opt,name=traffic,proto3" json:"traffic,omitempty"`
}

func (x *GeneralTraffic) Reset() {
	*x = GeneralTraffic{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[7]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GeneralTraffic) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GeneralTraffic) ProtoMessage() {}

func (x *GeneralTraffic) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[7]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GeneralTraffic.ProtoReflect.Descriptor instead.
func (*GeneralTraffic) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{7}
}

func (x *GeneralTraffic) GetShortCode() string {
	if x != nil {
		return x.ShortCode
	}
	return ""
}

func (x *GeneralTraffic) GetOriginalUrl() string {
	if x != nil {
		return x.OriginalUrl
	}
	return ""
}

func (x *GeneralTraffic) GetTrafficDate() string {
	if x != nil {
		return x.TrafficDate
	}
	return ""
}

func (x *GeneralTraffic) GetTraffic() int64 {
	if x != nil {
		return x.Traffic
	}
	return 0
}

type GeneralTrafficsSearchResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	GeneralTraffic []*GeneralTraffic `protobuf:"bytes,1,rep,name=generalTraffic,proto3" json:"generalTraffic,omitempty"`
	PageInfo       *PageInfo         `protobuf:"bytes,2,opt,name=pageInfo,proto3" json:"pageInfo,omitempty"`
}

func (x *GeneralTrafficsSearchResponse) Reset() {
	*x = GeneralTrafficsSearchResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_url_shortener_service_proto_msgTypes[8]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GeneralTrafficsSearchResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GeneralTrafficsSearchResponse) ProtoMessage() {}

func (x *GeneralTrafficsSearchResponse) ProtoReflect() protoreflect.Message {
	mi := &file_url_shortener_service_proto_msgTypes[8]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GeneralTrafficsSearchResponse.ProtoReflect.Descriptor instead.
func (*GeneralTrafficsSearchResponse) Descriptor() ([]byte, []int) {
	return file_url_shortener_service_proto_rawDescGZIP(), []int{8}
}

func (x *GeneralTrafficsSearchResponse) GetGeneralTraffic() []*GeneralTraffic {
	if x != nil {
		return x.GeneralTraffic
	}
	return nil
}

func (x *GeneralTrafficsSearchResponse) GetPageInfo() *PageInfo {
	if x != nil {
		return x.PageInfo
	}
	return nil
}

var File_url_shortener_service_proto protoreflect.FileDescriptor

var file_url_shortener_service_proto_rawDesc = []byte{
	0x0a, 0x1b, 0x75, 0x72, 0x6c, 0x2d, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x65, 0x6e, 0x65, 0x72, 0x2d,
	0x73, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x0e, 0x6f,
	0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x1a, 0x1c, 0x67,
	0x6f, 0x6f, 0x67, 0x6c, 0x65, 0x2f, 0x61, 0x70, 0x69, 0x2f, 0x61, 0x6e, 0x6e, 0x6f, 0x74, 0x61,
	0x74, 0x69, 0x6f, 0x6e, 0x73, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x1a, 0x0c, 0x63, 0x6f, 0x6d,
	0x6d, 0x6f, 0x6e, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x22, 0x76, 0x0a, 0x18, 0x47, 0x65, 0x6e,
	0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x52, 0x65,
	0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x20, 0x0a, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e, 0x61,
	0x6c, 0x55, 0x72, 0x6c, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x6f, 0x72, 0x69, 0x67,
	0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x12, 0x20, 0x0a, 0x0b, 0x74, 0x72, 0x61, 0x66, 0x66,
	0x69, 0x63, 0x44, 0x61, 0x74, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x74, 0x72,
	0x61, 0x66, 0x66, 0x69, 0x63, 0x44, 0x61, 0x74, 0x65, 0x12, 0x16, 0x0a, 0x06, 0x7a, 0x6f, 0x6e,
	0x65, 0x49, 0x64, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52, 0x06, 0x7a, 0x6f, 0x6e, 0x65, 0x49,
	0x64, 0x22, 0x7d, 0x0a, 0x1f, 0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f,
	0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x41, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x52, 0x65, 0x71,
	0x75, 0x65, 0x73, 0x74, 0x12, 0x20, 0x0a, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c,
	0x55, 0x72, 0x6c, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69,
	0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x12, 0x20, 0x0a, 0x0b, 0x74, 0x72, 0x61, 0x66, 0x66, 0x69,
	0x63, 0x44, 0x61, 0x74, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x74, 0x72, 0x61,
	0x66, 0x66, 0x69, 0x63, 0x44, 0x61, 0x74, 0x65, 0x12, 0x16, 0x0a, 0x06, 0x7a, 0x6f, 0x6e, 0x65,
	0x49, 0x64, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52, 0x06, 0x7a, 0x6f, 0x6e, 0x65, 0x49, 0x64,
	0x22, 0x75, 0x0a, 0x19, 0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f, 0x72,
	0x74, 0x43, 0x6f, 0x64, 0x65, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x1c, 0x0a,
	0x09, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09,
	0x52, 0x09, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x12, 0x20, 0x0a, 0x0b, 0x62,
	0x61, 0x73, 0x65, 0x36, 0x34, 0x49, 0x6d, 0x61, 0x67, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09,
	0x52, 0x0b, 0x62, 0x61, 0x73, 0x65, 0x36, 0x34, 0x49, 0x6d, 0x61, 0x67, 0x65, 0x12, 0x18, 0x0a,
	0x07, 0x69, 0x73, 0x4f, 0x77, 0x6e, 0x65, 0x72, 0x18, 0x03, 0x20, 0x01, 0x28, 0x08, 0x52, 0x07,
	0x69, 0x73, 0x4f, 0x77, 0x6e, 0x65, 0x72, 0x22, 0x6d, 0x0a, 0x15, 0x47, 0x65, 0x74, 0x4f, 0x72,
	0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74,
	0x12, 0x1c, 0x0a, 0x09, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x18, 0x01, 0x20,
	0x01, 0x28, 0x09, 0x52, 0x09, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x12, 0x1e,
	0x0a, 0x0a, 0x63, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x54, 0x69, 0x6d, 0x65, 0x18, 0x02, 0x20, 0x01,
	0x28, 0x09, 0x52, 0x0a, 0x63, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x54, 0x69, 0x6d, 0x65, 0x12, 0x16,
	0x0a, 0x06, 0x7a, 0x6f, 0x6e, 0x65, 0x49, 0x64, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52, 0x06,
	0x7a, 0x6f, 0x6e, 0x65, 0x49, 0x64, 0x22, 0x3a, 0x0a, 0x16, 0x47, 0x65, 0x74, 0x4f, 0x72, 0x69,
	0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65,
	0x12, 0x20, 0x0a, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x18,
	0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55,
	0x72, 0x6c, 0x22, 0x9f, 0x01, 0x0a, 0x1c, 0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x6c, 0x54, 0x72,
	0x61, 0x66, 0x66, 0x69, 0x63, 0x73, 0x53, 0x65, 0x61, 0x72, 0x63, 0x68, 0x52, 0x65, 0x71, 0x75,
	0x65, 0x73, 0x74, 0x12, 0x17, 0x0a, 0x04, 0x70, 0x61, 0x67, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28,
	0x05, 0x48, 0x00, 0x52, 0x04, 0x70, 0x61, 0x67, 0x65, 0x88, 0x01, 0x01, 0x12, 0x17, 0x0a, 0x04,
	0x73, 0x69, 0x7a, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x05, 0x48, 0x01, 0x52, 0x04, 0x73, 0x69,
	0x7a, 0x65, 0x88, 0x01, 0x01, 0x12, 0x29, 0x0a, 0x0d, 0x73, 0x6f, 0x72, 0x74, 0x44, 0x69, 0x72,
	0x65, 0x63, 0x74, 0x69, 0x6f, 0x6e, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x48, 0x02, 0x52, 0x0d,
	0x73, 0x6f, 0x72, 0x74, 0x44, 0x69, 0x72, 0x65, 0x63, 0x74, 0x69, 0x6f, 0x6e, 0x88, 0x01, 0x01,
	0x42, 0x07, 0x0a, 0x05, 0x5f, 0x70, 0x61, 0x67, 0x65, 0x42, 0x07, 0x0a, 0x05, 0x5f, 0x73, 0x69,
	0x7a, 0x65, 0x42, 0x10, 0x0a, 0x0e, 0x5f, 0x73, 0x6f, 0x72, 0x74, 0x44, 0x69, 0x72, 0x65, 0x63,
	0x74, 0x69, 0x6f, 0x6e, 0x22, 0x8e, 0x01, 0x0a, 0x08, 0x50, 0x61, 0x67, 0x65, 0x49, 0x6e, 0x66,
	0x6f, 0x12, 0x20, 0x0a, 0x0b, 0x63, 0x75, 0x72, 0x72, 0x65, 0x6e, 0x74, 0x50, 0x61, 0x67, 0x65,
	0x18, 0x01, 0x20, 0x01, 0x28, 0x05, 0x52, 0x0b, 0x63, 0x75, 0x72, 0x72, 0x65, 0x6e, 0x74, 0x50,
	0x61, 0x67, 0x65, 0x12, 0x1e, 0x0a, 0x0a, 0x74, 0x6f, 0x74, 0x61, 0x6c, 0x50, 0x61, 0x67, 0x65,
	0x73, 0x18, 0x02, 0x20, 0x01, 0x28, 0x05, 0x52, 0x0a, 0x74, 0x6f, 0x74, 0x61, 0x6c, 0x50, 0x61,
	0x67, 0x65, 0x73, 0x12, 0x24, 0x0a, 0x0d, 0x74, 0x6f, 0x74, 0x61, 0x6c, 0x45, 0x6c, 0x65, 0x6d,
	0x65, 0x6e, 0x74, 0x73, 0x18, 0x03, 0x20, 0x01, 0x28, 0x03, 0x52, 0x0d, 0x74, 0x6f, 0x74, 0x61,
	0x6c, 0x45, 0x6c, 0x65, 0x6d, 0x65, 0x6e, 0x74, 0x73, 0x12, 0x1a, 0x0a, 0x08, 0x70, 0x61, 0x67,
	0x65, 0x53, 0x69, 0x7a, 0x65, 0x18, 0x04, 0x20, 0x01, 0x28, 0x05, 0x52, 0x08, 0x70, 0x61, 0x67,
	0x65, 0x53, 0x69, 0x7a, 0x65, 0x22, 0x8c, 0x01, 0x0a, 0x0e, 0x47, 0x65, 0x6e, 0x65, 0x72, 0x61,
	0x6c, 0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x12, 0x1c, 0x0a, 0x09, 0x73, 0x68, 0x6f, 0x72,
	0x74, 0x43, 0x6f, 0x64, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x09, 0x73, 0x68, 0x6f,
	0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x12, 0x20, 0x0a, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e,
	0x61, 0x6c, 0x55, 0x72, 0x6c, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x6f, 0x72, 0x69,
	0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x12, 0x20, 0x0a, 0x0b, 0x74, 0x72, 0x61, 0x66,
	0x66, 0x69, 0x63, 0x44, 0x61, 0x74, 0x65, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x74,
	0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x44, 0x61, 0x74, 0x65, 0x12, 0x18, 0x0a, 0x07, 0x74, 0x72,
	0x61, 0x66, 0x66, 0x69, 0x63, 0x18, 0x04, 0x20, 0x01, 0x28, 0x03, 0x52, 0x07, 0x74, 0x72, 0x61,
	0x66, 0x66, 0x69, 0x63, 0x22, 0x9d, 0x01, 0x0a, 0x1d, 0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x6c,
	0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x73, 0x53, 0x65, 0x61, 0x72, 0x63, 0x68, 0x52, 0x65,
	0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x46, 0x0a, 0x0e, 0x67, 0x65, 0x6e, 0x65, 0x72, 0x61,
	0x6c, 0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x18, 0x01, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1e,
	0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e,
	0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x6c, 0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x52, 0x0e,
	0x67, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x6c, 0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x12, 0x34,
	0x0a, 0x08, 0x70, 0x61, 0x67, 0x65, 0x49, 0x6e, 0x66, 0x6f, 0x18, 0x02, 0x20, 0x01, 0x28, 0x0b,
	0x32, 0x18, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e,
	0x6b, 0x2e, 0x50, 0x61, 0x67, 0x65, 0x49, 0x6e, 0x66, 0x6f, 0x52, 0x08, 0x70, 0x61, 0x67, 0x65,
	0x49, 0x6e, 0x66, 0x6f, 0x32, 0xbc, 0x04, 0x0a, 0x13, 0x55, 0x72, 0x6c, 0x53, 0x68, 0x6f, 0x72,
	0x74, 0x65, 0x6e, 0x65, 0x72, 0x53, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x12, 0x80, 0x01, 0x0a,
	0x11, 0x67, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f,
	0x64, 0x65, 0x12, 0x28, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c,
	0x69, 0x6e, 0x6b, 0x2e, 0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f, 0x72,
	0x74, 0x43, 0x6f, 0x64, 0x65, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x20, 0x2e, 0x6f,
	0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x53, 0x74,
	0x61, 0x6e, 0x64, 0x61, 0x72, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22, 0x1f,
	0x82, 0xd3, 0xe4, 0x93, 0x02, 0x19, 0x3a, 0x01, 0x2a, 0x22, 0x14, 0x2f, 0x61, 0x70, 0x69, 0x2f,
	0x76, 0x31, 0x2f, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x65, 0x6e, 0x73, 0x2f, 0x67, 0x65, 0x6e, 0x12,
	0x96, 0x01, 0x0a, 0x18, 0x67, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f, 0x72,
	0x74, 0x43, 0x6f, 0x64, 0x65, 0x41, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x12, 0x2f, 0x2e, 0x6f,
	0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x47, 0x65,
	0x6e, 0x65, 0x72, 0x61, 0x74, 0x65, 0x53, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x41,
	0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x20, 0x2e,
	0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x53,
	0x74, 0x61, 0x6e, 0x64, 0x61, 0x72, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22,
	0x27, 0x82, 0xd3, 0xe4, 0x93, 0x02, 0x21, 0x3a, 0x01, 0x2a, 0x22, 0x1c, 0x2f, 0x61, 0x70, 0x69,
	0x2f, 0x76, 0x31, 0x2f, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x65, 0x6e, 0x73, 0x2f, 0x61, 0x63, 0x63,
	0x6f, 0x75, 0x6e, 0x74, 0x2d, 0x67, 0x65, 0x6e, 0x12, 0x7a, 0x0a, 0x0e, 0x67, 0x65, 0x74, 0x4f,
	0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x12, 0x25, 0x2e, 0x6f, 0x6e, 0x6c,
	0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x47, 0x65, 0x74, 0x4f,
	0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73,
	0x74, 0x1a, 0x20, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69,
	0x6e, 0x6b, 0x2e, 0x53, 0x74, 0x61, 0x6e, 0x64, 0x61, 0x72, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f,
	0x6e, 0x73, 0x65, 0x22, 0x1f, 0x82, 0xd3, 0xe4, 0x93, 0x02, 0x19, 0x3a, 0x01, 0x2a, 0x22, 0x14,
	0x2f, 0x61, 0x70, 0x69, 0x2f, 0x76, 0x31, 0x2f, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x65, 0x6e, 0x73,
	0x2f, 0x67, 0x65, 0x74, 0x12, 0x8c, 0x01, 0x0a, 0x15, 0x73, 0x65, 0x61, 0x72, 0x63, 0x68, 0x47,
	0x65, 0x6e, 0x65, 0x72, 0x61, 0x6c, 0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x73, 0x12, 0x2c,
	0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e,
	0x47, 0x65, 0x6e, 0x65, 0x72, 0x61, 0x6c, 0x54, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x73, 0x53,
	0x65, 0x61, 0x72, 0x63, 0x68, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x20, 0x2e, 0x6f,
	0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x53, 0x74,
	0x61, 0x6e, 0x64, 0x61, 0x72, 0x64, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22, 0x23,
	0x82, 0xd3, 0xe4, 0x93, 0x02, 0x1d, 0x3a, 0x01, 0x2a, 0x22, 0x18, 0x2f, 0x61, 0x70, 0x69, 0x2f,
	0x76, 0x31, 0x2f, 0x74, 0x72, 0x61, 0x66, 0x66, 0x69, 0x63, 0x73, 0x2f, 0x67, 0x65, 0x6e, 0x65,
	0x72, 0x61, 0x6c, 0x42, 0x18, 0x5a, 0x16, 0x67, 0x72, 0x70, 0x63, 0x2d, 0x67, 0x61, 0x74, 0x65,
	0x77, 0x61, 0x79, 0x2f, 0x61, 0x70, 0x69, 0x2f, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x06, 0x70,
	0x72, 0x6f, 0x74, 0x6f, 0x33,
}

var (
	file_url_shortener_service_proto_rawDescOnce sync.Once
	file_url_shortener_service_proto_rawDescData = file_url_shortener_service_proto_rawDesc
)

func file_url_shortener_service_proto_rawDescGZIP() []byte {
	file_url_shortener_service_proto_rawDescOnce.Do(func() {
		file_url_shortener_service_proto_rawDescData = protoimpl.X.CompressGZIP(file_url_shortener_service_proto_rawDescData)
	})
	return file_url_shortener_service_proto_rawDescData
}

var file_url_shortener_service_proto_msgTypes = make([]protoimpl.MessageInfo, 9)
var file_url_shortener_service_proto_goTypes = []any{
	(*GenerateShortCodeRequest)(nil),        // 0: online.gonlink.GenerateShortCodeRequest
	(*GenerateShortCodeAccountRequest)(nil), // 1: online.gonlink.GenerateShortCodeAccountRequest
	(*GenerateShortCodeResponse)(nil),       // 2: online.gonlink.GenerateShortCodeResponse
	(*GetOriginalUrlRequest)(nil),           // 3: online.gonlink.GetOriginalUrlRequest
	(*GetOriginalUrlResponse)(nil),          // 4: online.gonlink.GetOriginalUrlResponse
	(*GeneralTrafficsSearchRequest)(nil),    // 5: online.gonlink.GeneralTrafficsSearchRequest
	(*PageInfo)(nil),                        // 6: online.gonlink.PageInfo
	(*GeneralTraffic)(nil),                  // 7: online.gonlink.GeneralTraffic
	(*GeneralTrafficsSearchResponse)(nil),   // 8: online.gonlink.GeneralTrafficsSearchResponse
	(*StandardResponse)(nil),                // 9: online.gonlink.StandardResponse
}
var file_url_shortener_service_proto_depIdxs = []int32{
	7, // 0: online.gonlink.GeneralTrafficsSearchResponse.generalTraffic:type_name -> online.gonlink.GeneralTraffic
	6, // 1: online.gonlink.GeneralTrafficsSearchResponse.pageInfo:type_name -> online.gonlink.PageInfo
	0, // 2: online.gonlink.UrlShortenerService.generateShortCode:input_type -> online.gonlink.GenerateShortCodeRequest
	1, // 3: online.gonlink.UrlShortenerService.generateShortCodeAccount:input_type -> online.gonlink.GenerateShortCodeAccountRequest
	3, // 4: online.gonlink.UrlShortenerService.getOriginalUrl:input_type -> online.gonlink.GetOriginalUrlRequest
	5, // 5: online.gonlink.UrlShortenerService.searchGeneralTraffics:input_type -> online.gonlink.GeneralTrafficsSearchRequest
	9, // 6: online.gonlink.UrlShortenerService.generateShortCode:output_type -> online.gonlink.StandardResponse
	9, // 7: online.gonlink.UrlShortenerService.generateShortCodeAccount:output_type -> online.gonlink.StandardResponse
	9, // 8: online.gonlink.UrlShortenerService.getOriginalUrl:output_type -> online.gonlink.StandardResponse
	9, // 9: online.gonlink.UrlShortenerService.searchGeneralTraffics:output_type -> online.gonlink.StandardResponse
	6, // [6:10] is the sub-list for method output_type
	2, // [2:6] is the sub-list for method input_type
	2, // [2:2] is the sub-list for extension type_name
	2, // [2:2] is the sub-list for extension extendee
	0, // [0:2] is the sub-list for field type_name
}

func init() { file_url_shortener_service_proto_init() }
func file_url_shortener_service_proto_init() {
	if File_url_shortener_service_proto != nil {
		return
	}
	file_common_proto_init()
	if !protoimpl.UnsafeEnabled {
		file_url_shortener_service_proto_msgTypes[0].Exporter = func(v any, i int) any {
			switch v := v.(*GenerateShortCodeRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[1].Exporter = func(v any, i int) any {
			switch v := v.(*GenerateShortCodeAccountRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[2].Exporter = func(v any, i int) any {
			switch v := v.(*GenerateShortCodeResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[3].Exporter = func(v any, i int) any {
			switch v := v.(*GetOriginalUrlRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[4].Exporter = func(v any, i int) any {
			switch v := v.(*GetOriginalUrlResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[5].Exporter = func(v any, i int) any {
			switch v := v.(*GeneralTrafficsSearchRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[6].Exporter = func(v any, i int) any {
			switch v := v.(*PageInfo); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[7].Exporter = func(v any, i int) any {
			switch v := v.(*GeneralTraffic); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_url_shortener_service_proto_msgTypes[8].Exporter = func(v any, i int) any {
			switch v := v.(*GeneralTrafficsSearchResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	file_url_shortener_service_proto_msgTypes[5].OneofWrappers = []any{}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_url_shortener_service_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   9,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_url_shortener_service_proto_goTypes,
		DependencyIndexes: file_url_shortener_service_proto_depIdxs,
		MessageInfos:      file_url_shortener_service_proto_msgTypes,
	}.Build()
	File_url_shortener_service_proto = out.File
	file_url_shortener_service_proto_rawDesc = nil
	file_url_shortener_service_proto_goTypes = nil
	file_url_shortener_service_proto_depIdxs = nil
}
