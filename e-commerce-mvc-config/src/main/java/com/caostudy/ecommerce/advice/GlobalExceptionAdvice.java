package com.caostudy.ecommerce.advice;

import com.caostudy.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cao Study
 * @description GlobalExceptionAdvice 全局异常捕获处理
 * @date 2021/7/22 19:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerCommerceException(HttpServletRequest request,
                                                           Exception e) {
        CommonResponse<String> response=new CommonResponse<>(
          -1,"business error"
        );
        response.setData(e.getMessage());
        log.error("commerce service has error:[{}]",e.getMessage(),e);
        return response;
    }
}
