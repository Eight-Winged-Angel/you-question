package cn.edu.cupk.learn.config;

import cn.edu.cupk.learn.api.aop.interceptor.SmsInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new SmsInterceptor())
                .addPathPatterns("/auth/phone/send");
    }
}
