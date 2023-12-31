package cn.edu.cupk.learn.common.vo;

import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.enums.StatusCode;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * 返回对象JSON
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = -3977464215965519547L;
    // 状态码
    @NotNull
    private String code = ApiCode.SUCCESS.getCode();

    // 状态信息
    @NotNull
    private String message = ApiCode.SUCCESS.getMessage();

    // 返回对象
    @Nullable
    private Object data = null;

    // 默认返回成功状态码，数据对象
    public ResponseVO(@Nullable Object data) {
        this.data = data;
    }

    // 只返回状态码
    public ResponseVO(@NotNull StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    // 返回指定状态码，数据对象
    public ResponseVO(@NotNull StatusCode statusCode, @Nullable Object data) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }
}
