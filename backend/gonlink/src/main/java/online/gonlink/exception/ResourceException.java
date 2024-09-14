package online.gonlink.exception;

import online.gonlink.exception.enumdef.ExceptionEnum;

public class ResourceException extends RuntimeException {

    public ResourceException(String message, Exception e) {
        super(message, e);
    }

    public ResourceException(ExceptionEnum constant, Exception e) {
        super(constant.name(), e);
    }
}
