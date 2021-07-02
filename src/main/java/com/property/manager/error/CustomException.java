package com.property.manager.error;

public class CustomException extends Exception {

    private CustomError error = null;

    public CustomException(CustomError error) {
        super(error.message());
        this.error = error;
    }

    public CustomException(CustomError error, String extra) {
        super(error.message() + " [" + extra + "]");
        this.error = error;
    }

    public CustomException(CustomError error, Throwable cause) {
        super(error.message(), cause);
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return this.error == null ? -1 : this.error.code();
    }
}
