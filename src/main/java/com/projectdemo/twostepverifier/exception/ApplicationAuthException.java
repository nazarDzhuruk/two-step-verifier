package com.projectdemo.twostepverifier.exception;

public class ApplicationAuthException extends RuntimeException{
    public ApplicationAuthException() {
    }

    public ApplicationAuthException(String message) {
        super(message);
    }

    public ApplicationAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationAuthException(Throwable cause) {
        super(cause);
    }

    public ApplicationAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
