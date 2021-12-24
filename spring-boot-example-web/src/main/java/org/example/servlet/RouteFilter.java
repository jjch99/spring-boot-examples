package org.example.servlet;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 前后端混部，且前端使用 HistoryRouter(另一种常见的是HashRouter)时，可能需要把前端路由兜底到指定页面(eg:index.html)
 * 更好的做法应该是前后端分别约定好路径前缀，清晰明确
 */
@Slf4j
// @WebFilter(filterName = "RouteFilter", urlPatterns = "/*")
public class RouteFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init");
    }

    @Override
    public void destroy() {
        log.info("destroy");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestPath = request.getRequestURI();
        log.info("requestURI: {}", requestPath);

        if (StringUtils.contains(requestPath, "/api/")) {
            filterChain.doFilter(request, response);
        } else if (StringUtils.isNotEmpty(FilenameUtils.getExtension(requestPath))) {
            filterChain.doFilter(request, response);
        } else {
            responseIndexHtml(response);
        }
    }

    private void responseIndexHtml(HttpServletResponse response) {
        try {
            ClassPathResource resource = new ClassPathResource("static/index.html");
            if (resource.exists()) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    response.setContentType(MimeTypeUtils.TEXT_HTML_VALUE);
                    inputStream = resource.getInputStream();
                    outputStream = response.getOutputStream();
                    IOUtils.copy(inputStream, outputStream);
                    outputStream.flush();
                } finally {
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                }
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.warn("response index.html failed", e);
        }
    }

}
