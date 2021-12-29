package org.example.httpclient;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.example.common.ServiceException;
import org.example.dto.BaseRequest;
import org.example.dto.BaseResponse;
import org.example.dto.HelloRequest;
import org.example.dto.HelloResponse;
import org.example.utils.JsonUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * 封装HTTP接口简单示例
 * <p>
 * 一般HTTP接口设计时，<br>
 * 1.每个接口的URL不同，eg 微信支付API <br>
 * 2.使用统一的URL，然后通过某个参数来定位到某个接口，eg 支付宝支付API、阿里云API、腾讯云API <br>
 * <p>
 * 一般HTTP接口还涉及签名、验签等问题
 * <p>
 * 接口设计方式可多参考大型成熟的开放平台：云厂商、微信、支付宝
 */
@Slf4j
public class HttpClientServiceImpl implements HttpClientService {

    @Setter
    private String serviceUrl;

    @Setter
    private CloseableHttpClient httpClient;

    @Setter
    private RequestConfig requestConfig;

    @Override
    public BaseResponse<HelloResponse> hello(HelloRequest request) {
        URI uri = URI.create(serviceUrl + "/hello");
        BaseRequest baseRequest = createRequest("hello", request);
        BaseResponse baseResponse = execute(uri, baseRequest, HelloResponse.class);
        return baseResponse;
    }

    private <T> BaseRequest createRequest(String method, T request) {
        // 部分基础参数可以直接注入值到当前实例，或提供一个Provider根据条件获取
        BaseRequest<T> baseRequest = new BaseRequest<>();
        baseRequest.setAppId("XXX");
        baseRequest.setMethod(method);
        baseRequest.setData(request);
        baseRequest.setVersion("1.0.0");
        return baseRequest;
    }

    private <T> BaseResponse<T> execute(URI uri, BaseRequest request, Class<T> responseDataClass) {
        try {
            return doPost(uri, request, responseDataClass);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("doPost failed", e);
        }
    }

    private <T> BaseResponse<T> doPost(URI uri, BaseRequest request, Class<T> responseDataClass) throws Exception {
        String requestJson = JsonUtils.toJson(request);
        String responseBody = doPost(uri, requestJson);
        if (responseBody == null) {
            return null;
        }
        BaseResponse response = JsonUtils.toObject(responseBody, type(BaseResponse.class, responseDataClass));
        return response;
    }

    @SuppressWarnings("deprecation")
    private String doPost(URI uri, String request) throws Exception {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String body = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    return body;
                }
            }
        } finally {
            IOUtils.closeQuietly(response);
        }
        return null;
    }

    /**
     * 获取 Type 另一个选择: com.google.gson.reflect.TypeToken
     * 
     * <pre>
     * Type type = new TypeToken<ClassA<ClassB>>(){}.getType()
     * </pre>
     */
    private Type type(final Class<?> raw, final Type...args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
