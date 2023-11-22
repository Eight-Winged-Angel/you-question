package cn.edu.cupk.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Api文档生成配置
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "dockerBean")
    public Docket docketBean() {
        Contact contact = new Contact("Icy", "www.cupk.club", "icy@st.cupk.edu.cn");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .description("# Knife4j RESTful APIs")
                                .termsOfServiceUrl("localhost")
                                .contact(contact)
                                .version("1.0")
                                .build()
                )
                .groupName("用户服务")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.edu.cupk.learn.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
