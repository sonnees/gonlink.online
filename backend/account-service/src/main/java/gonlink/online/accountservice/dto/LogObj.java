package gonlink.online.accountservice.dto;

public record LogObj<S,T>(Class<S> serviceClass, String methodName, T content) {}
