package cn.edu.cupk.learn.api.aop.aspect;

import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.enums.ServiceCode;
import cn.edu.cupk.learn.common.exception.ApiException;
import cn.edu.cupk.learn.common.vo.ResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@RestControllerAdvice(basePackages = "cn.edu.cupk.learn.api.controller")
public class ControllerExceptionHandler implements ResponseBodyAdvice<Object> {

    // response是ResultVo类型，或者注释了NotControllerResponseAdvice都不进行包装
    @Override
    public boolean supports(@NotNull MethodParameter methodParameter,
                            @NotNull Class<? extends HttpMessageConverter<?>> clazz) {
        return !methodParameter.getParameterType().isAssignableFrom(ResponseVO.class);
    }

    // String类型不能直接包装
    @Override
    public Object beforeBodyWrite(Object data, @NotNull MethodParameter returnType,
                                  @NotNull MediaType mediaType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> clazz,
                                  @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            // 将数据包装在ResultVo里后转换为json串进行返回
            try {
                return objectMapper.writeValueAsString(new ResponseVO(data));
            } catch (JsonProcessingException e) {
                throw new ApiException(ApiCode.API_ERROR, e.getStackTrace());
            }
        }

        // 否则直接包装成ResultVo返回
        return new ResponseVO(data);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(@NotNull AccessDeniedException e) {
        return new ResponseVO(ApiCode.AUTH_MISMATCH);
    }

    @ExceptionHandler(ApiException.class)
    public Object handleServiceException(@NotNull ApiException e) {
        return new ResponseVO(e.getStatusCode(), e.getData());
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public Object handlerMethodArgumentNotValidException() {
        return new ResponseVO(ApiCode.PARAM_FORMAT_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException() {
        return new ResponseVO(ServiceCode.SERVICE_ERROR);
    }

}
