package cn.edu.cupk.learn.common.exception;

import cn.edu.cupk.learn.common.enums.StatusCode;
import lombok.Getter;

/**
 * Service层异常
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class ServiceException extends ApiException {

    private static final long serialVersionUID = 4000618294874083090L;

    public ServiceException(StatusCode statusCode, Object data) {
        super(statusCode, data);
    }
    public ServiceException(StatusCode statusCode) {
        super(statusCode);
    }
}
