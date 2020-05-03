package com.ustudent.resquod;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(getSwaggerPaths())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    private Predicate<String> getSwaggerPaths() {
        return or(
                regex("/user.*"),
                regex("/test.*"));
    }


    private ApiKey apiKey() {
        return new ApiKey("authkey", "Authorization", "header");
    }

}
