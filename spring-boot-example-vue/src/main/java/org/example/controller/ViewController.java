package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping(value = "/**", produces = MediaType.TEXT_HTML_VALUE)
    public String resolveView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        String basePath = getBasePath(request);
        String requestPath = getRequestPath(request);
        if (log.isDebugEnabled()) {
            log.debug("basePath: {}", basePath);
            log.debug("requestPath: {}", requestPath);
        }
        modelMap.put("contextPath", basePath); // FTL文件中使用

        String viewFile = StringUtils.removeStart(requestPath, basePath + "/view");
        return viewFile;
    }

    private String getBasePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if (StringUtils.isEmpty(contextPath) || contextPath.equals("/")) {
            contextPath = "";
        }

        if (request.getPathInfo() == null) {
            // getPathInfo() 为空时，getServletPath()就是请求路径
            return contextPath;
        }

        String servletPath = request.getServletPath();
        if (StringUtils.isEmpty(servletPath) || servletPath.equals("/")) {
            servletPath = "";
        }
        return contextPath + servletPath;
    }

    private String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

}
