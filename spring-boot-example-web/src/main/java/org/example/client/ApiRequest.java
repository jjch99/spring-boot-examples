package org.example.client;

public interface ApiRequest<T extends ApiResponse> {

    String getRequestPath();

    Object getData();

    Class<T> getResponseClass();

}
