package com.cq.hwh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2//开启swagger2
public class SwaggerConfig {
    //配置了Swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("程崎")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cq.hwh.controller"))
                .build();
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("程崎", "http://127.0.0.1:8880", "2972084238@qq.com");

        return new ApiInfo(
                "文件分类管理系统",
                          "将一系列文件分类成视频，图片等",
                "cq&&hwh",
                "https://gitee.com/file-classification-management/back-end-code.git",
                contact,
                "Apache 2.0",
                "https://gitee.com/file-classification-management/back-end-code.git",
                new ArrayList());
    }
}
