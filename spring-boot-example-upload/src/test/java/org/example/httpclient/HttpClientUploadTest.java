package org.example.httpclient;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientUploadTest extends TestCase {

    public void testUpload() throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String uri = "http://localhost:8080/batch-upload";
            HttpPost httppost = new HttpPost(uri);

            Resource resource1 = new ClassPathResource("hello.json");
            File file1 = resource1.getFile();
            FileBody fileBody1 = new FileBody(file1, ContentType.APPLICATION_JSON);

            Resource resource2 = new ClassPathResource("hello.txt");
            File file2 = resource2.getFile();
            FileBody fileBody2 = new FileBody(file2, ContentType.TEXT_PLAIN);

            StringBody hash = new StringBody("123456789", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", fileBody1)
                    .addPart("file", fileBody2)
                    .addPart("hash", hash)
                    .build();

            httppost.setEntity(reqEntity);

            log.info("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                log.info("----------------------------------------");
                log.info(response.getStatusLine().toString());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    log.info("Response content length: " + resEntity.getContentLength());
                    log.info(EntityUtils.toString(resEntity));
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

    }

}
