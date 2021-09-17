package org.example.controller;

import org.apache.commons.lang3.StringUtils;
import org.example.common.ServiceException;
import org.example.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

//@RestController
//@ControllerAdvice
public class CombinedErrorHandler extends ResponseEntityExceptionHandler implements ErrorController {

    private static final String NO_MESSAGE_AVAILABLE = "No message available";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private HttpStatus resolve(int statusCode) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.value() == statusCode) {
                return status;
            }
        }
        return null;
    }

    @RequestMapping(value = "/error")
    @ResponseBody
    public BaseResponse error(HttpServletRequest req, HttpServletResponse resp) {
        BaseResponse<String> response = new BaseResponse<>();
        response.setCode("" + resp.getStatus());

        WebRequest webRequest = new ServletWebRequest(req);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(webRequest, false);

        String message = "";
        if (attr.containsKey("message")) {
            message = Objects.toString(attr.get("message"));
        }
        if (StringUtils.isEmpty(message) || NO_MESSAGE_AVAILABLE.equalsIgnoreCase(message)) {
            HttpStatus status = resolve(resp.getStatus());
            if (status != null) {
                message = status.getReasonPhrase();
            } else {
                message = "";
            }
        }
        response.setMessage(message);
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
        }
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        super.handleExceptionInternal(e, body, headers, status, request);

        BaseResponse<String> response = new BaseResponse();
        response.setCode(String.valueOf(status.value()));
        response.setMessage(StringUtils.defaultString(status.getReasonPhrase()));
        return new ResponseEntity(response, headers, HttpStatus.OK);
    }

}
