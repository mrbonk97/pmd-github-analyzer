package io.github.mrbonk97.analyzer.exception;

public class CustomException extends RuntimeException {
    public CustomException(ExceptionCode message) {
        super(message.getMessage());
    }

    public CustomException(ExceptionCode message, String detail) {
        super(message.getMessage() + ": " + detail);
    }
}
