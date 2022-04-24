package indi.hadeslock.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Author: Hadeslock
 * Created on 2022/3/25 20:02
 * Email: hadeslock@126.com
 * Desc: swagger配置类
 */

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    //api接口包扫描路径
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "indi.hadeslock.server.controller";

    public static final String VERSION = "1.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //配置包扫描根路径，扫描controller包下的控制器
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                //.apis(RequestHandlerSelectors.any())
                //任何路径都生成文档
                .paths(PathSelectors.any())
                .build();
    }


    /*
     * @author Hadeslock
     * @time 2021/12/22 19:09
     * 文档信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("情绪检测接口文档")
                .description("情绪检测接口文档")
                .contact(new Contact("hadeslock",null,"hadeslock@126.com"))
                .version(VERSION)
                .build();
    }

}
