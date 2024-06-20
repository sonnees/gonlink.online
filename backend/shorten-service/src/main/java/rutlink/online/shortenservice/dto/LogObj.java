package rutlink.online.shortenservice.dto;

public record LogObj<S,T>(Class<S> serviceClass, String methodName, T content) {
}
