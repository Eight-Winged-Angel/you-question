package cn.edu.cupk.learn.api.controller;

import cn.edu.cupk.learn.common.dto.auth.LoginDTO;
import cn.edu.cupk.learn.common.dto.auth.RegisterDTO;
import cn.edu.cupk.learn.common.dto.auth.ResetDTO;
import cn.edu.cupk.learn.common.dto.auth.VerifyDTO;
import cn.edu.cupk.learn.core.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

/**
 * 登录模块api
 * @since 1.0.0
 * @author Icy
 * @version 1.0.0
 */
@Api(tags = "身份认证模块")
@Validated
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 前端请求发送验证码
     * @param phone 手机号
     */
    @ApiOperation("给定手机发送验证码")
    @PreAuthorize("permitAll()")
    @GetMapping("/code/send")
    public void codeSend(
            @ApiParam(value = "手机号", required = true)
            @Pattern(regexp = "^1[3-9]\\d{9}$")
            @RequestParam("phone") String phone) {
        System.out.println(phone);
        authService.codeSend(phone);
    }

    /**
     * 前端请求验证手机号
     * @param form 手机号，验证码
     * @return 进行下次操作所需的token标记
     */
    @ApiOperation("验证手机号")
    @PreAuthorize("permitAll()")
    @PostMapping("/code/verify")
    public String codeVerify(
            @Validated @NotNull @RequestBody VerifyDTO form) {
        return authService.codeVerify(form.getPhone(), form.getCode());
    }

    /**
     * 新用户注册
     * @param form 注册信息表单
     * @return token和权限
     */
    @ApiOperation("用户注册")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public Object register(@NotNull @RequestBody RegisterDTO form) {
        System.out.println("register");
        return authService.register(form.getToken(), form.getName(), form.getPassword());
    }

    /**
     * 手机号密码登录
     * @param form 密码登录表单
     * @return token和权限
     */
    @ApiOperation("手机号密码登录")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login/password")
    public Object loginByPassword(@NotNull @RequestBody LoginDTO form) {
        return authService.loginByPassword(form.getPhone(), form.getPassword());
    }

    /**
     * 手机验证码登录
     * @param form 验证码表单
     * @return token和权限
     */
    @ApiOperation("手机号验证码登录")
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login/verifyCode")
    public Object loginByVerifyCode(@NotNull @RequestBody VerifyDTO form) {
        return authService.loginByVerifyCode(form.getPhone(), form.getCode());
    }

    /**
     * 用户登出
     */
    @ApiOperation("用户登出")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public void logout() {
        authService.logout();
    }

    /**
     * 重置密码
     * @param form 重置密码表单
     */
    @ApiOperation("重置密码")
    @PreAuthorize("permitAll()")
    @PostMapping("/reset")
    public void reset(@NotNull @RequestBody ResetDTO form) {
        authService.reset(form.getToken(), form.getPassword());
    }

}
