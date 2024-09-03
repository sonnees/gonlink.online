package online.gonlink.exception;

public class ResourceException extends RuntimeException {

    public ResourceException(String message, Exception e) {
        super(message, e);
    }
}
