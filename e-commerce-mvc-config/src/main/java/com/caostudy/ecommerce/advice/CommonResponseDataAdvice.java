package com.caostudy.ecommerce.advice;

import com.caostudy.ecommerce.annotation.IgnoreResponseAdvice;
import com.caostudy.ecommerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Cao Study
 * @description CommonResponseDataAdvice 实现统一响应
 * @date 2021/7/22 19:39
 * 对所有Controller返回的ResponseBody对象进行拦截
 */
//@RestControllerAdvice的value值指定生效的范围
@RestControllerAdvice(value = "com.caostudy.ecommerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 判断是否需要对响应进行处理
     * @param returnType
     * @param converterType
     * @return true表示需要处理， false表示不需要处理
     * 这里要判断@IgnoreResponseAdvice注解
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getDeclaringClass()
                .isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if(returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }

    /**
     * 包装ResponseBody使其变为我们编写的统一接口响应CommonResponse
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        //定义最终的返回对象
        CommonResponse<Object> response=new CommonResponse<>(0,"");
        if(null==body){
            return response;
        }else if (body instanceof CommonResponse){
            response=(CommonResponse<Object>) body;
        }else{
            response.setData(body);
        }
        return response;
    }
}
