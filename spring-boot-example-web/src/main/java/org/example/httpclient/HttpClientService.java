package org.example.httpclient;

import org.example.dto.BaseResponse;
import org.example.dto.HelloRequest;
import org.example.dto.HelloResponse;

public interface HttpClientService {

    BaseResponse<HelloResponse> hello(HelloRequest request);

}
