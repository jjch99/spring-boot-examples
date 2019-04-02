package org.example.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {

    private String code;

    private String message;

    private String data;

    public boolean success() {
        return "0".equals(this.code);
    }

}
