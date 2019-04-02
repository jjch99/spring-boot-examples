package org.example.dto;

public class Response<T> {

    private final String code;

    private final String message;

    private final T data;

    private Response(String code, String message) {
        this(code, message, null);
    }

    private Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public <T> T getData() {
        return (T) data;
    }

    public final Response customMessage(String message) {
        return new Response(this.code, message, this.data);
    }

    public final <T> Response<T> customData(T data) {
        return new Response(this.code, this.message, data);
    }

    public static final Response create(String code, String message) {
        return new Response(code, message);
    }

    public static final <T> Response<T> create(String code, String message, T data) {
        return new Response(code, message, data);
    }

}
