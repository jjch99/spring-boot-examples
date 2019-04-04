package org.example.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class ApiClient {

    private OkHttpClient httpClient;

    @Setter
    private String url;

    @Setter
    private String accessKey;

    @Setter
    private String secretKey;

    public ApiClient() {
        this(null);
    }

    public ApiClient(OkHttpClient httpClient) {
        if (this.httpClient == null) {
            this.httpClient = new OkHttpClient.Builder().build();
        } else {
            this.httpClient = httpClient;
        }
    }

    public <T extends ApiResponse> T execute(ApiRequest<T> request) {
        String url = this.url + request.getRequestPath();
        return execute(url, accessKey, secretKey, request.getData(), request.getResponseClass());
    }

    protected <T extends ApiResponse> T execute(String url, String accessKey, String secretKey, Object data,
            Class<T> clazz) {
        Map params = new LinkedHashMap();
        params.put("accessKey", accessKey);
        params.put("reqid", UUID.randomUUID().toString());
        params.put("signType", "SHA256");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        if (data instanceof String) {
            params.put("data", data);
        } else {
            params.put("data", JSON.toJSONString(data));
        }
        params.put("sign", sign(params, secretKey));
        String response = null;
        try {
            response = doPost(url, params);
        } catch (IOException e) {
            log.error("API reqeust error: {}", params, e);
            throw new ApiException("500", "API请求失败", e);
        }
        T respObj = null;
        try {
            respObj = JSON.parseObject(response, clazz);
            respObj.setRawData(response);
        } catch (JSONException e) {
            log.error("response content json parse error: {}", response, e);
            throw new ApiException("500", "response content json parse error", e);
        }
        return respObj;
    }

    protected String sign(Map<String, String> params, String secretKey) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            if ("sign".equalsIgnoreCase(key)) {
                continue;
            }
            String value = params.get(key);
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secretKey);
        String sign = DigestUtils.sha256Hex(query.toString().getBytes(StandardCharsets.UTF_8));
        return sign.toUpperCase();
    }

    protected String doPost(String fullUrl, Map<String, String> params) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            formBody.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(formBody.build())
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        if (!response.isSuccessful()) {
            log.debug("retrun rawData: {}", responseBody);
        }
        return responseBody;
    }

}
