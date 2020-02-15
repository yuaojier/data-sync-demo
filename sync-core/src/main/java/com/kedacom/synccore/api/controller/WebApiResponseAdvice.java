package com.kedacom.synccore.api.controller;

import cn.hutool.core.util.StrUtil;
import com.kedacom.synccore.api.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.kedacom.synccore"})
public class WebApiResponseAdvice implements ResponseBodyAdvice {
    private static final String OK_CODE = "10000";

    private static final String TXTFILE = ".txt";

    private static final String STR_JSON_TEMPLATE = "{\"code\":\"" + OK_CODE + "\", \"data\":\"{}\"}";


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String rawPath = request.getURI().getRawPath();
        if (rawPath.endsWith(TXTFILE)) {
            return body;
        }
        if (AbstractJackson2HttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            return new HttpResponse<>(OK_CODE, null, body);
        } else if (StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            String str = StrUtil.toString(body);
            if (StrUtil.startWith(str, "{")) {
                str = StrUtil.replace(body.toString(), "\"", "\\\"");
            }
            return StrUtil.format(STR_JSON_TEMPLATE, str);
        } else {
            return body;
        }
    }
}
