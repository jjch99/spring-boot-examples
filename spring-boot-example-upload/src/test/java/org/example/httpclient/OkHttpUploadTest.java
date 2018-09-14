package org.example.httpclient;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSONObject;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class OkHttpUploadTest extends TestCase {

    private OkHttpClient creteHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.followRedirects(true);
        return builder.build();
    }

    public void testUploadFileWithJson() throws Exception {

        String url = "http://localhost:8080/batch-upload-with-json";

        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("id", 12345);
            requestJson.put("name", "tom");
            String requestJsonStr = requestJson.toString();
            log.info(requestJsonStr);
            MediaType json = MediaType.parse("application/json;charset=UTF-8");
            RequestBody requestBody = RequestBody.create(json, requestJsonStr);

            Resource fileResource = new ClassPathResource("hello.txt");
            File file = fileResource.getFile();
            MediaType octetStream = MediaType.parse("application/octet-stream");
            RequestBody fileRequestBody = RequestBody.create(octetStream, file);

            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("hello", "hello.json", requestBody)
                    .addFormDataPart("file", file.getName(), fileRequestBody)
                    .addFormDataPart("hash", "123456")
                    .build();

            Request httpRequest = new Request.Builder()
                    .url(url)
                    .post(multipartBody)
                    .build();

            // OkHttpClient httpClient = new OkHttpClient();
            OkHttpClient httpClient = creteHttpClient();
            Response httpResponse = httpClient.newCall(httpRequest).execute();
            String httpResponseBody = httpResponse.body().string();
            log.info(httpResponseBody);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
