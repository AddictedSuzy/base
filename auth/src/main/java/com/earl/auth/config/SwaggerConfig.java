package com.earl.auth.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                // 添加全局参数
//                .globalOperationParameters(setHeaderToken())
                .apiInfo(new ApiInfoBuilder()
                        .title("Auth Swagger").contact(
                                new Contact("Earl",  "https://github.com/AddictedSuzy",  "liupanyifo@163.com"))
                        .description("Auth Swagger Description")
                        .version("9.0")
                        .license("Earl Fake License")
                        .licenseUrl("http://www.bilibili.com")
                        .build());
    }

//    private List<Parameter> setHeaderToken(){
//        ParameterBuilder ticketPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        //header中的ticket参数非必填，传空也可以
//        ticketPar.name("Authorization").description("认证token")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build();
//        //根据每个方法名也知道当前方法在设置什么参数
//        pars.add(ticketPar.build());
//        return pars;
//    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    // 对部分接口不要求使用全局参数
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}