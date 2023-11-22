package cn.edu.cupk.learn.common.exception;

import cn.edu.cupk.learn.common.enums.StatusCode;
import lombok.Getter;

/**
 * 数据库异常
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class DbException extends ApiException {

    private static final long serialVersionUID = 4294601765233334817L;

    public DbException(StatusCode statusCode, Object data) {
        super(statusCode, data);
    }
    public DbException(StatusCode statusCode) {
        super(statusCode);
    }
}
