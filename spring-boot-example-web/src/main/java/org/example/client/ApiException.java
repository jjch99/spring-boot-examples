package org.example.client;

public class ApiException extends RuntimeException {

    private String code;

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

}
