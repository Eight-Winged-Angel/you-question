package cn.edu.cupk.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * SpringBoot后台启动入口
 * @author Icy
 * @version 1.0.0
 */
@EnableRedisHttpSession
@SpringBootApplication
public class LearningApplication {


    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class, args);
    }

}
