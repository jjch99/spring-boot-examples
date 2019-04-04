package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseRequest<T> {

    private String appId;

    private String method;

    private String timestamp;

    private String version;

    private T data;

}
