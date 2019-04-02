package org.example.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class DemoResponse extends ApiResponse {

    private DemoResult data;

    @Getter
    @Setter
    @ToString
    public static class DemoResult {

        private String content;

    }

}
