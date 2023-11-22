package cn.edu.cupk.learn.core.provider;

import cn.edu.cupk.learn.common.domain.SmsVerifyCodeAuthenticationToken;
import cn.edu.cupk.learn.common.enums.DbCode;
import cn.edu.cupk.learn.common.enums.ServiceCode;
import cn.edu.cupk.learn.common.exception.ApiException;
import cn.edu.cupk.learn.common.exception.DbException;
import cn.edu.cupk.learn.common.exception.ServiceException;
import cn.edu.cupk.learn.core.service.AuthService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 短信验证码登录认证逻辑
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private GrantedAuthoritiesMapper authoritiesMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return new NullAuthoritiesMapper();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof SmsVerifyCodeAuthenticationToken)) {
            throw new ApiException();
        }
        SmsVerifyCodeAuthenticationToken smsVerifyCodeAuthenticationToken =
                (SmsVerifyCodeAuthenticationToken) authentication;
        String phone = smsVerifyCodeAuthenticationToken.getName();
        String code = smsVerifyCodeAuthenticationToken.getVerifyCode();
        UserDetails user = userDetailsService.loadUserByUsername(phone);
        if (user == null) {
            throw new DbException(DbCode.USER_NOT_FOUND);
        }

        // 验证码校验，失败抛异常返回，成功创建新token（认证后）
        authService.codeVerify(phone, code);
        return createSuccessAuthentication(authentication, user);
    }

    // 当前Provider仅支持校验短信验证码token
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsVerifyCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 认证成功，将非认证token转为认证token
     * @param authentication 身份认证
     * @param user 用户信息
     * @return 认证token
     */
    protected Authentication createSuccessAuthentication(
            @NotNull Authentication authentication,
            @NotNull UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
        SmsVerifyCodeAuthenticationToken authenticationToken =
                new SmsVerifyCodeAuthenticationToken(user, null, authorities);
        authenticationToken.setDetails(authentication.getDetails());
        return authenticationToken;
    }
}
