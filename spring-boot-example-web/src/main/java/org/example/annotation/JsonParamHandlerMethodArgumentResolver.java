package org.example.annotation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.core.MethodParameter;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 从[application/x-www-form-urlencoded]请求里 解析Json格式的参数
 * <p>
 * 有些系统 参数通过表单方式提交，但里面有的参数是JSON格式的，可以通过这个来处理
 */
public class JsonParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper mapper;

    public JsonParamHandlerMethodArgumentResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // TODO: 待完善
        JsonParam a = parameter.getParameterAnnotation(JsonParam.class);
        String val = webRequest.getParameter(a.value());
        if (val == null) {
            // 参数缺失
            if (parameter.hasParameterAnnotation(Valid.class)
                    || parameter.hasParameterAnnotation(Validated.class)
                    || parameter.hasParameterAnnotation(NotNull.class)) {
                BindingResult bindingResult = new NullBeanBindingResult(parameter.getParameterName());
                throw new MethodArgumentNotValidException(parameter, bindingResult);
            }
            return null;
        }
        if (String.class.equals(parameter.getParameterType())) {
            return val;
        }

        Object obj = mapper.readValue(val, parameter.getParameterType());
        if (parameter.hasParameterAnnotation(Valid.class) || parameter.hasParameterAnnotation(Validated.class)) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
            binder.validate();
            BindingResult bindingResult = binder.getBindingResult();
            if (bindingResult.hasErrors()) {
                throw new MethodArgumentNotValidException(parameter, bindingResult);
            }
        }
        return obj;
    }

    private static class NullBeanBindingResult extends AbstractBindingResult {

        public NullBeanBindingResult(String objectName) {
            super(objectName);
        }

        @Override
        public Object getTarget() {
            return null;
        }

        @Override
        protected Object getActualFieldValue(String field) {
            return null;
        }

    }

}
