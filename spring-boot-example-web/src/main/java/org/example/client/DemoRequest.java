package org.example.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DemoRequest implements ApiRequest<DemoResponse> {

    private DemoRequestBody body;

    @Override
    public String getRequestPath() {
        return "/demo";
    }

    @Override
    public Object getData() {
        return body;
    }

    @Override
    public Class<DemoResponse> getResponseClass() {
        return DemoResponse.class;
    }

    @Getter
    @Setter
    @ToString
    public static class DemoRequestBody {

        private String content;

    }

}
