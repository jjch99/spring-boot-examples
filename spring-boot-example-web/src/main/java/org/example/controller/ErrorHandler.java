package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.common.ServiceException;
import org.example.dto.BaseResponse;
import org.example.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@Slf4j
@RestController
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    // @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    @ResponseBody
    public BaseResponse error(HttpServletRequest req, HttpServletResponse resp) {

        WebRequest webRequest = new ServletWebRequest(req);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        log.warn("error: {}", JsonUtils.toJson(attr));

        BaseResponse<String> response = new BaseResponse<>();
        response.setCode(String.valueOf(resp.getStatus()));
        if (attr.containsKey("error")) {
            response.setMessage(String.valueOf(attr.get("error")));
        }
        if (StringUtils.isEmpty(response.getMessage())) {
            response.setMessage("unknown error");
        }

        String method = getClass().getName() + ".error";
        log.info("method: {}, return: {}", method, JsonUtils.toJson(response));

        return response;
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public BaseResponse exceptionHandler(HttpServletRequest req, Exception e) {
        BaseResponse<String> response = new BaseResponse();
        if (e instanceof ServiceException) {
            ServiceException ex = (ServiceException) e;
            response.setCode(ex.getCode());
            response.setMessage(e.getMessage());
        } else {
            response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.warn(req.getRequestURI(), e);
        }

        String method = getClass().getName() + ".exceptionHandler";
        log.info("method: {}, return: {}", method, JsonUtils.toJson(response));

        return response;
    }

    // @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        super.handleExceptionInternal(e, body, headers, status, request);

        BaseResponse<String> response = new BaseResponse();
        response.setCode(String.valueOf(status.value()));
        response.setMessage(StringUtils.defaultString(status.getReasonPhrase()));

        String method = getClass().getName() + ".handleExceptionInternal";
        log.info("method: {}, return: {}", method, JsonUtils.toJson(response));

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }

}
