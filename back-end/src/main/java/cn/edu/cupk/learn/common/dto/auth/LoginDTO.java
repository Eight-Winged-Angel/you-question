package cn.edu.cupk.learn.common.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * 登录表单
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class LoginDTO {

    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phone;

    // 密码暂定为任意6~16位大小写字母、数字、小数点组合s
    @ApiModelProperty(value = "密码", required = true)
    @Pattern(regexp = "[A-Za-z\\d.]{6,16}")
    private String password;

}
