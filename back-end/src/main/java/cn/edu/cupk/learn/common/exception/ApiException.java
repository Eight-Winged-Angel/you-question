package cn.edu.cupk.learn.common.exception;

import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 后端Api异常
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -7680905182505160327L;

    private StatusCode statusCode = ApiCode.API_ERROR;

    private Object data;

    public ApiException(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.data = null;
    }

    public ApiException(StatusCode statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

}
