package org.example.dubbo;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class GreetingTest {

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @Test
    public void greeting() throws Exception {

        final Request request = new Request.Builder()
                .url("httpclient://localhost:18080/hello?name=world")
                .get()
                .build();
        Response response = okHttpClient().newCall(request).execute();
        log.info(response.body().string());

    }

}
