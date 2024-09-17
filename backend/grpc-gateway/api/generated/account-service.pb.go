// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.34.2
// 	protoc        v5.27.1
// source: account-service.proto

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

// === GetInfoAccount ===
type GetInfoAccountRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *GetInfoAccountRequest) Reset() {
	*x = GetInfoAccountRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_account_service_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetInfoAccountRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetInfoAccountRequest) ProtoMessage() {}

func (x *GetInfoAccountRequest) ProtoReflect() protoreflect.Message {
	mi := &file_account_service_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetInfoAccountRequest.ProtoReflect.Descriptor instead.
func (*GetInfoAccountRequest) Descriptor() ([]byte, []int) {
	return file_account_service_proto_rawDescGZIP(), []int{0}
}

type DataClient struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Name string `protobuf:"bytes,1,opt,name=name,proto3" json:"name,omitempty"`
	Data int64  `protobuf:"varint,2,opt,name=data,proto3" json:"data,omitempty"`
}

func (x *DataClient) Reset() {
	*x = DataClient{}
	if protoimpl.UnsafeEnabled {
		mi := &file_account_service_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *DataClient) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*DataClient) ProtoMessage() {}

func (x *DataClient) ProtoReflect() protoreflect.Message {
	mi := &file_account_service_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use DataClient.ProtoReflect.Descriptor instead.
func (*DataClient) Descriptor() ([]byte, []int) {
	return file_account_service_proto_rawDescGZIP(), []int{1}
}

func (x *DataClient) GetName() string {
	if x != nil {
		return x.Name
	}
	return ""
}

func (x *DataClient) GetData() int64 {
	if x != nil {
		return x.Data
	}
	return 0
}

type ShortUrl struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	ShortCode   string `protobuf:"bytes,1,opt,name=shortCode,proto3" json:"shortCode,omitempty"`
	OriginalUrl string `protobuf:"bytes,2,opt,name=originalUrl,proto3" json:"originalUrl,omitempty"`
}

func (x *ShortUrl) Reset() {
	*x = ShortUrl{}
	if protoimpl.UnsafeEnabled {
		mi := &file_account_service_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *ShortUrl) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*ShortUrl) ProtoMessage() {}

func (x *ShortUrl) ProtoReflect() protoreflect.Message {
	mi := &file_account_service_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use ShortUrl.ProtoReflect.Descriptor instead.
func (*ShortUrl) Descriptor() ([]byte, []int) {
	return file_account_service_proto_rawDescGZIP(), []int{2}
}

func (x *ShortUrl) GetShortCode() string {
	if x != nil {
		return x.ShortCode
	}
	return ""
}

func (x *ShortUrl) GetOriginalUrl() string {
	if x != nil {
		return x.OriginalUrl
	}
	return ""
}

type GetInfoAccountResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Email               string        `protobuf:"bytes,1,opt,name=email,proto3" json:"email,omitempty"`
	Name                string        `protobuf:"bytes,2,opt,name=name,proto3" json:"name,omitempty"`
	Avatar              string        `protobuf:"bytes,3,opt,name=avatar,proto3" json:"avatar,omitempty"`
	Role                string        `protobuf:"bytes,4,opt,name=role,proto3" json:"role,omitempty"`
	Create              string        `protobuf:"bytes,5,opt,name=create,proto3" json:"create,omitempty"`
	TotalShortURL       int32         `protobuf:"varint,6,opt,name=totalShortURL,proto3" json:"totalShortURL,omitempty"`
	TotalClick          int64         `protobuf:"varint,7,opt,name=totalClick,proto3" json:"totalClick,omitempty"`
	Cities              []*DataClient `protobuf:"bytes,8,rep,name=cities,proto3" json:"cities,omitempty"`
	Countries           []*DataClient `protobuf:"bytes,9,rep,name=countries,proto3" json:"countries,omitempty"`
	Timezones           []*DataClient `protobuf:"bytes,10,rep,name=timezones,proto3" json:"timezones,omitempty"`
	Browsers            []*DataClient `protobuf:"bytes,11,rep,name=browsers,proto3" json:"browsers,omitempty"`
	BrowserVersions     []*DataClient `protobuf:"bytes,12,rep,name=browserVersions,proto3" json:"browserVersions,omitempty"`
	OperatingSystems    []*DataClient `protobuf:"bytes,13,rep,name=operatingSystems,proto3" json:"operatingSystems,omitempty"`
	OsVersions          []*DataClient `protobuf:"bytes,14,rep,name=osVersions,proto3" json:"osVersions,omitempty"`
	DeviceTypes         []*DataClient `protobuf:"bytes,15,rep,name=deviceTypes,proto3" json:"deviceTypes,omitempty"`
	DeviceManufacturers []*DataClient `protobuf:"bytes,16,rep,name=deviceManufacturers,proto3" json:"deviceManufacturers,omitempty"`
	DeviceNames         []*DataClient `protobuf:"bytes,17,rep,name=deviceNames,proto3" json:"deviceNames,omitempty"`
}

func (x *GetInfoAccountResponse) Reset() {
	*x = GetInfoAccountResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_account_service_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetInfoAccountResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetInfoAccountResponse) ProtoMessage() {}

func (x *GetInfoAccountResponse) ProtoReflect() protoreflect.Message {
	mi := &file_account_service_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetInfoAccountResponse.ProtoReflect.Descriptor instead.
func (*GetInfoAccountResponse) Descriptor() ([]byte, []int) {
	return file_account_service_proto_rawDescGZIP(), []int{3}
}

func (x *GetInfoAccountResponse) GetEmail() string {
	if x != nil {
		return x.Email
	}
	return ""
}

func (x *GetInfoAccountResponse) GetName() string {
	if x != nil {
		return x.Name
	}
	return ""
}

func (x *GetInfoAccountResponse) GetAvatar() string {
	if x != nil {
		return x.Avatar
	}
	return ""
}

func (x *GetInfoAccountResponse) GetRole() string {
	if x != nil {
		return x.Role
	}
	return ""
}

func (x *GetInfoAccountResponse) GetCreate() string {
	if x != nil {
		return x.Create
	}
	return ""
}

func (x *GetInfoAccountResponse) GetTotalShortURL() int32 {
	if x != nil {
		return x.TotalShortURL
	}
	return 0
}

func (x *GetInfoAccountResponse) GetTotalClick() int64 {
	if x != nil {
		return x.TotalClick
	}
	return 0
}

func (x *GetInfoAccountResponse) GetCities() []*DataClient {
	if x != nil {
		return x.Cities
	}
	return nil
}

func (x *GetInfoAccountResponse) GetCountries() []*DataClient {
	if x != nil {
		return x.Countries
	}
	return nil
}

func (x *GetInfoAccountResponse) GetTimezones() []*DataClient {
	if x != nil {
		return x.Timezones
	}
	return nil
}

func (x *GetInfoAccountResponse) GetBrowsers() []*DataClient {
	if x != nil {
		return x.Browsers
	}
	return nil
}

func (x *GetInfoAccountResponse) GetBrowserVersions() []*DataClient {
	if x != nil {
		return x.BrowserVersions
	}
	return nil
}

func (x *GetInfoAccountResponse) GetOperatingSystems() []*DataClient {
	if x != nil {
		return x.OperatingSystems
	}
	return nil
}

func (x *GetInfoAccountResponse) GetOsVersions() []*DataClient {
	if x != nil {
		return x.OsVersions
	}
	return nil
}

func (x *GetInfoAccountResponse) GetDeviceTypes() []*DataClient {
	if x != nil {
		return x.DeviceTypes
	}
	return nil
}

func (x *GetInfoAccountResponse) GetDeviceManufacturers() []*DataClient {
	if x != nil {
		return x.DeviceManufacturers
	}
	return nil
}

func (x *GetInfoAccountResponse) GetDeviceNames() []*DataClient {
	if x != nil {
		return x.DeviceNames
	}
	return nil
}

var File_account_service_proto protoreflect.FileDescriptor

var file_account_service_proto_rawDesc = []byte{
	0x0a, 0x15, 0x61, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x2d, 0x73, 0x65, 0x72, 0x76, 0x69, 0x63,
	0x65, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x0e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e,
	0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x1a, 0x1c, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65, 0x2f,
	0x61, 0x70, 0x69, 0x2f, 0x61, 0x6e, 0x6e, 0x6f, 0x74, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x73, 0x2e,
	0x70, 0x72, 0x6f, 0x74, 0x6f, 0x1a, 0x0c, 0x63, 0x6f, 0x6d, 0x6d, 0x6f, 0x6e, 0x2e, 0x70, 0x72,
	0x6f, 0x74, 0x6f, 0x22, 0x17, 0x0a, 0x15, 0x47, 0x65, 0x74, 0x49, 0x6e, 0x66, 0x6f, 0x41, 0x63,
	0x63, 0x6f, 0x75, 0x6e, 0x74, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x22, 0x34, 0x0a, 0x0a,
	0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x12, 0x12, 0x0a, 0x04, 0x6e, 0x61,
	0x6d, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x04, 0x6e, 0x61, 0x6d, 0x65, 0x12, 0x12,
	0x0a, 0x04, 0x64, 0x61, 0x74, 0x61, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03, 0x52, 0x04, 0x64, 0x61,
	0x74, 0x61, 0x22, 0x4a, 0x0a, 0x08, 0x53, 0x68, 0x6f, 0x72, 0x74, 0x55, 0x72, 0x6c, 0x12, 0x1c,
	0x0a, 0x09, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28,
	0x09, 0x52, 0x09, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x64, 0x65, 0x12, 0x20, 0x0a, 0x0b,
	0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x18, 0x02, 0x20, 0x01, 0x28,
	0x09, 0x52, 0x0b, 0x6f, 0x72, 0x69, 0x67, 0x69, 0x6e, 0x61, 0x6c, 0x55, 0x72, 0x6c, 0x22, 0xc0,
	0x06, 0x0a, 0x16, 0x47, 0x65, 0x74, 0x49, 0x6e, 0x66, 0x6f, 0x41, 0x63, 0x63, 0x6f, 0x75, 0x6e,
	0x74, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x14, 0x0a, 0x05, 0x65, 0x6d, 0x61,
	0x69, 0x6c, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x05, 0x65, 0x6d, 0x61, 0x69, 0x6c, 0x12,
	0x12, 0x0a, 0x04, 0x6e, 0x61, 0x6d, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x04, 0x6e,
	0x61, 0x6d, 0x65, 0x12, 0x16, 0x0a, 0x06, 0x61, 0x76, 0x61, 0x74, 0x61, 0x72, 0x18, 0x03, 0x20,
	0x01, 0x28, 0x09, 0x52, 0x06, 0x61, 0x76, 0x61, 0x74, 0x61, 0x72, 0x12, 0x12, 0x0a, 0x04, 0x72,
	0x6f, 0x6c, 0x65, 0x18, 0x04, 0x20, 0x01, 0x28, 0x09, 0x52, 0x04, 0x72, 0x6f, 0x6c, 0x65, 0x12,
	0x16, 0x0a, 0x06, 0x63, 0x72, 0x65, 0x61, 0x74, 0x65, 0x18, 0x05, 0x20, 0x01, 0x28, 0x09, 0x52,
	0x06, 0x63, 0x72, 0x65, 0x61, 0x74, 0x65, 0x12, 0x24, 0x0a, 0x0d, 0x74, 0x6f, 0x74, 0x61, 0x6c,
	0x53, 0x68, 0x6f, 0x72, 0x74, 0x55, 0x52, 0x4c, 0x18, 0x06, 0x20, 0x01, 0x28, 0x05, 0x52, 0x0d,
	0x74, 0x6f, 0x74, 0x61, 0x6c, 0x53, 0x68, 0x6f, 0x72, 0x74, 0x55, 0x52, 0x4c, 0x12, 0x1e, 0x0a,
	0x0a, 0x74, 0x6f, 0x74, 0x61, 0x6c, 0x43, 0x6c, 0x69, 0x63, 0x6b, 0x18, 0x07, 0x20, 0x01, 0x28,
	0x03, 0x52, 0x0a, 0x74, 0x6f, 0x74, 0x61, 0x6c, 0x43, 0x6c, 0x69, 0x63, 0x6b, 0x12, 0x32, 0x0a,
	0x06, 0x63, 0x69, 0x74, 0x69, 0x65, 0x73, 0x18, 0x08, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e,
	0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44,
	0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x52, 0x06, 0x63, 0x69, 0x74, 0x69, 0x65,
	0x73, 0x12, 0x38, 0x0a, 0x09, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x72, 0x69, 0x65, 0x73, 0x18, 0x09,
	0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f,
	0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74,
	0x52, 0x09, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x72, 0x69, 0x65, 0x73, 0x12, 0x38, 0x0a, 0x09, 0x74,
	0x69, 0x6d, 0x65, 0x7a, 0x6f, 0x6e, 0x65, 0x73, 0x18, 0x0a, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a,
	0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e,
	0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x52, 0x09, 0x74, 0x69, 0x6d, 0x65,
	0x7a, 0x6f, 0x6e, 0x65, 0x73, 0x12, 0x36, 0x0a, 0x08, 0x62, 0x72, 0x6f, 0x77, 0x73, 0x65, 0x72,
	0x73, 0x18, 0x0b, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65,
	0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69,
	0x65, 0x6e, 0x74, 0x52, 0x08, 0x62, 0x72, 0x6f, 0x77, 0x73, 0x65, 0x72, 0x73, 0x12, 0x44, 0x0a,
	0x0f, 0x62, 0x72, 0x6f, 0x77, 0x73, 0x65, 0x72, 0x56, 0x65, 0x72, 0x73, 0x69, 0x6f, 0x6e, 0x73,
	0x18, 0x0c, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e,
	0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65,
	0x6e, 0x74, 0x52, 0x0f, 0x62, 0x72, 0x6f, 0x77, 0x73, 0x65, 0x72, 0x56, 0x65, 0x72, 0x73, 0x69,
	0x6f, 0x6e, 0x73, 0x12, 0x46, 0x0a, 0x10, 0x6f, 0x70, 0x65, 0x72, 0x61, 0x74, 0x69, 0x6e, 0x67,
	0x53, 0x79, 0x73, 0x74, 0x65, 0x6d, 0x73, 0x18, 0x0d, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e,
	0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44,
	0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x52, 0x10, 0x6f, 0x70, 0x65, 0x72, 0x61,
	0x74, 0x69, 0x6e, 0x67, 0x53, 0x79, 0x73, 0x74, 0x65, 0x6d, 0x73, 0x12, 0x3a, 0x0a, 0x0a, 0x6f,
	0x73, 0x56, 0x65, 0x72, 0x73, 0x69, 0x6f, 0x6e, 0x73, 0x18, 0x0e, 0x20, 0x03, 0x28, 0x0b, 0x32,
	0x1a, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b,
	0x2e, 0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x52, 0x0a, 0x6f, 0x73, 0x56,
	0x65, 0x72, 0x73, 0x69, 0x6f, 0x6e, 0x73, 0x12, 0x3c, 0x0a, 0x0b, 0x64, 0x65, 0x76, 0x69, 0x63,
	0x65, 0x54, 0x79, 0x70, 0x65, 0x73, 0x18, 0x0f, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x6f,
	0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44, 0x61,
	0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x52, 0x0b, 0x64, 0x65, 0x76, 0x69, 0x63, 0x65,
	0x54, 0x79, 0x70, 0x65, 0x73, 0x12, 0x4c, 0x0a, 0x13, 0x64, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4d,
	0x61, 0x6e, 0x75, 0x66, 0x61, 0x63, 0x74, 0x75, 0x72, 0x65, 0x72, 0x73, 0x18, 0x10, 0x20, 0x03,
	0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c,
	0x69, 0x6e, 0x6b, 0x2e, 0x44, 0x61, 0x74, 0x61, 0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x52, 0x13,
	0x64, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4d, 0x61, 0x6e, 0x75, 0x66, 0x61, 0x63, 0x74, 0x75, 0x72,
	0x65, 0x72, 0x73, 0x12, 0x3c, 0x0a, 0x0b, 0x64, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61, 0x6d,
	0x65, 0x73, 0x18, 0x11, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x1a, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e,
	0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x44, 0x61, 0x74, 0x61, 0x43, 0x6c,
	0x69, 0x65, 0x6e, 0x74, 0x52, 0x0b, 0x64, 0x65, 0x76, 0x69, 0x63, 0x65, 0x4e, 0x61, 0x6d, 0x65,
	0x73, 0x32, 0x7c, 0x0a, 0x07, 0x41, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x12, 0x71, 0x0a, 0x0e,
	0x67, 0x65, 0x74, 0x49, 0x6e, 0x66, 0x6f, 0x41, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x12, 0x25,
	0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e,
	0x47, 0x65, 0x74, 0x49, 0x6e, 0x66, 0x6f, 0x41, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x52, 0x65,
	0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x18, 0x2e, 0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x65, 0x2e, 0x67,
	0x6f, 0x6e, 0x6c, 0x69, 0x6e, 0x6b, 0x2e, 0x42, 0x61, 0x73, 0x65, 0x47, 0x72, 0x70, 0x63, 0x22,
	0x1e, 0x82, 0xd3, 0xe4, 0x93, 0x02, 0x18, 0x3a, 0x01, 0x2a, 0x22, 0x13, 0x2f, 0x61, 0x70, 0x69,
	0x2f, 0x76, 0x31, 0x2f, 0x61, 0x63, 0x63, 0x6f, 0x75, 0x6e, 0x74, 0x73, 0x2f, 0x6d, 0x65, 0x42,
	0x18, 0x5a, 0x16, 0x67, 0x72, 0x70, 0x63, 0x2d, 0x67, 0x61, 0x74, 0x65, 0x77, 0x61, 0x79, 0x2f,
	0x61, 0x70, 0x69, 0x2f, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x06, 0x70, 0x72, 0x6f, 0x74, 0x6f,
	0x33,
}

var (
	file_account_service_proto_rawDescOnce sync.Once
	file_account_service_proto_rawDescData = file_account_service_proto_rawDesc
)

func file_account_service_proto_rawDescGZIP() []byte {
	file_account_service_proto_rawDescOnce.Do(func() {
		file_account_service_proto_rawDescData = protoimpl.X.CompressGZIP(file_account_service_proto_rawDescData)
	})
	return file_account_service_proto_rawDescData
}

var file_account_service_proto_msgTypes = make([]protoimpl.MessageInfo, 4)
var file_account_service_proto_goTypes = []any{
	(*GetInfoAccountRequest)(nil),  // 0: online.gonlink.GetInfoAccountRequest
	(*DataClient)(nil),             // 1: online.gonlink.DataClient
	(*ShortUrl)(nil),               // 2: online.gonlink.ShortUrl
	(*GetInfoAccountResponse)(nil), // 3: online.gonlink.GetInfoAccountResponse
	(*BaseGrpc)(nil),               // 4: online.gonlink.BaseGrpc
}
var file_account_service_proto_depIdxs = []int32{
	1,  // 0: online.gonlink.GetInfoAccountResponse.cities:type_name -> online.gonlink.DataClient
	1,  // 1: online.gonlink.GetInfoAccountResponse.countries:type_name -> online.gonlink.DataClient
	1,  // 2: online.gonlink.GetInfoAccountResponse.timezones:type_name -> online.gonlink.DataClient
	1,  // 3: online.gonlink.GetInfoAccountResponse.browsers:type_name -> online.gonlink.DataClient
	1,  // 4: online.gonlink.GetInfoAccountResponse.browserVersions:type_name -> online.gonlink.DataClient
	1,  // 5: online.gonlink.GetInfoAccountResponse.operatingSystems:type_name -> online.gonlink.DataClient
	1,  // 6: online.gonlink.GetInfoAccountResponse.osVersions:type_name -> online.gonlink.DataClient
	1,  // 7: online.gonlink.GetInfoAccountResponse.deviceTypes:type_name -> online.gonlink.DataClient
	1,  // 8: online.gonlink.GetInfoAccountResponse.deviceManufacturers:type_name -> online.gonlink.DataClient
	1,  // 9: online.gonlink.GetInfoAccountResponse.deviceNames:type_name -> online.gonlink.DataClient
	0,  // 10: online.gonlink.Account.getInfoAccount:input_type -> online.gonlink.GetInfoAccountRequest
	4,  // 11: online.gonlink.Account.getInfoAccount:output_type -> online.gonlink.BaseGrpc
	11, // [11:12] is the sub-list for method output_type
	10, // [10:11] is the sub-list for method input_type
	10, // [10:10] is the sub-list for extension type_name
	10, // [10:10] is the sub-list for extension extendee
	0,  // [0:10] is the sub-list for field type_name
}

func init() { file_account_service_proto_init() }
func file_account_service_proto_init() {
	if File_account_service_proto != nil {
		return
	}
	file_common_proto_init()
	if !protoimpl.UnsafeEnabled {
		file_account_service_proto_msgTypes[0].Exporter = func(v any, i int) any {
			switch v := v.(*GetInfoAccountRequest); i {
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
		file_account_service_proto_msgTypes[1].Exporter = func(v any, i int) any {
			switch v := v.(*DataClient); i {
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
		file_account_service_proto_msgTypes[2].Exporter = func(v any, i int) any {
			switch v := v.(*ShortUrl); i {
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
		file_account_service_proto_msgTypes[3].Exporter = func(v any, i int) any {
			switch v := v.(*GetInfoAccountResponse); i {
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
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_account_service_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   4,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_account_service_proto_goTypes,
		DependencyIndexes: file_account_service_proto_depIdxs,
		MessageInfos:      file_account_service_proto_msgTypes,
	}.Build()
	File_account_service_proto = out.File
	file_account_service_proto_rawDesc = nil
	file_account_service_proto_goTypes = nil
	file_account_service_proto_depIdxs = nil
}
