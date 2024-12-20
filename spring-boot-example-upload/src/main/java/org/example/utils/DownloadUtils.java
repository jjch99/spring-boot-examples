package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class DownloadUtils {

    private static String getEncoding(HttpServletRequest request) {
        String encoding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(encoding) || !Charset.isSupported(encoding)) {
            encoding = StandardCharsets.UTF_8.name();
        }
        return encoding;
    }

    private static String transformFilename(HttpServletRequest request, String filename) {
        try {
            String encoding = getEncoding(request);
            String userAgent = StringUtils.defaultString(request.getHeader("User-Agent")).toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.contains("edge")) {
                filename = URLEncoder.encode(filename, encoding);
            } else {
                filename = new String(filename.getBytes(encoding), StandardCharsets.ISO_8859_1);
            }
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return filename;
    }

    private static void setHeader(HttpServletResponse response, String filename) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(getEncoding(request));

        String contentDisposition = "attachment;filename=" + transformFilename(request, filename);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
    }

    public static void write(InputStream input, HttpServletResponse response, String filename)
            throws IOException {
        setHeader(response, filename);
        OutputStream output = response.getOutputStream();
        IOUtils.copy(input, output);
        output.flush();
    }

}
