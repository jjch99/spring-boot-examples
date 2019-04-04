package org.example.management;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriComponentsBuilder;

public class Shutdown {

    private static Properties load(String file) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = ClassLoader.getSystemResourceAsStream(file);
            if (in != null) {
                Charset cs = Charset.defaultCharset();
                InputStreamReader reader = new InputStreamReader(in, cs);
                prop.load(reader);
            }
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return prop;
    }

    public static void main(String[] args) {

        try {
            Properties prop = load("application.properties");
            if (args.length > 0) {
                prop.putAll(load(args[0]));
            }

            int port = Integer.parseInt(prop.getProperty("management.port"));
            String ctx = prop.getProperty("management.context-path");
            String path = prop.getProperty("endpoints.shutdown.path");
            String username = prop.getProperty("security.user.name");
            String password = prop.getProperty("security.user.password");
            String auth = Base64Utils.encodeToString((username + ":" + password).getBytes());

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("httpclient://127.0.0.1")
                    .port(port)
                    .path(ctx)
                    .path(path);
            String url = uriBuilder.toUriString();
            HttpURLConnection urlconn = (HttpURLConnection) new URL(url).openConnection();
            urlconn.setDoOutput(true);
            urlconn.setDoInput(true);
            urlconn.setRequestProperty("Authorization", "Basic " + auth);
            urlconn.setRequestMethod("POST");
            urlconn.connect();
            urlconn.getOutputStream().flush();
            int rspcode = urlconn.getResponseCode();
            urlconn.disconnect();

            if (rspcode == 200) {
                System.out.println("Shutdown Request OK.");
            } else {
                System.out.println("Shutdown Request Failed.");
            }

        } catch (ConnectException e) {
            System.out.println("Service Unavailable");
        } catch (Exception e) {
            System.out.println("Shutdown Request Failed.");
        }
    }

}
