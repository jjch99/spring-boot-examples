package org.example.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> implements Serializable {

    private String code = "0";

    private String message;

    private T data;

    public BaseResponse() {
    }

    public BaseResponse(T object) {
        this.data = object;
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean success() {
        return "0".equals(getCode());
    }

}
