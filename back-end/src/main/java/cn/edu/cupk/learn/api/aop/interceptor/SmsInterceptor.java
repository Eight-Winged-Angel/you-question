package cn.edu.cupk.learn.api.aop.interceptor;

import cn.edu.cupk.learn.common.enums.ApiCode;
import cn.edu.cupk.learn.common.exception.ApiException;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 当前Session短信验证码发送频率控制
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class SmsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Object handler) {
        HttpSession session = request.getSession();
        long now = System.currentTimeMillis();
        Long latest = (Long)session.getAttribute("phoneSend");
        // 大于1分钟即可再次请求
        if (latest == null || now - latest >= 60000) {
            return true;
        }
        int diff = 60 - (int)((now - latest) / 1000);
        throw new ApiException(ApiCode.VERIFY_TOO_FAST_SESSION, diff);
    }

    public void afterCompletion(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Object handler, @Nullable Exception ex) {
        HttpSession session = request.getSession();
        session.setAttribute("phoneSend", System.currentTimeMillis());
    }

}
