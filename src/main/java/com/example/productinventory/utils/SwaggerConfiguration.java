package com.example.productinventory.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.Collections;


@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {



    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiDetails() {
        return new ApiInfo(
                "ProductInventory API",
                "API a small product store",
                "1.0",
                "Free to use",
                new Contact("test", "info.dev.com", "test@emaildomain.com"),
                "API License",
                "API license URL",
                Collections.emptyList()
        );
    }


}
