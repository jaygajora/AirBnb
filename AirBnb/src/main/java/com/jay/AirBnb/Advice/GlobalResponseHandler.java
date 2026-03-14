package com.jay.AirBnb.Advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType){
        return true;
    } // spring ask should i apply the beforeBodyWrite() to this controllers/controller's response

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response){

        // DON'T wrap reponses from these end points in ApiResponse
        List<String> routesWithoutWrapping = List.of("/v3/api-docs", "/actuator");

        //to check if the request received at an endpoint matches with any endpoint present in "routesWithoutWrapping" list
        boolean isAllowedWithoutWrapping = routesWithoutWrapping
                .stream()
                .anyMatch(route -> request.getURI().getPath().contains(route));


        //if the response is already wrapped in ApiResponse<> or if the we are hitting an api endpoint / getting reponse from an endpoint whose response does NOT need to be wrapped!!
        if(body instanceof ApiResponse<?> || isAllowedWithoutWrapping){
            return body;
        }

        return new ApiResponse<>(body);
    }
}
