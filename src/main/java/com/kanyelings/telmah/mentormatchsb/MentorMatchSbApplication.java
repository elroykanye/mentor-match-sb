package com.kanyelings.telmah.mentormatchsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class MentorMatchSbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentorMatchSbApplication.class, args);
    }

    @Bean
    public Docket mmApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Mentor Match API")
                .version("1.0.0")
                .description("API reactive docs for the mentor match services")
                .contact(
                        new Contact(
                                "Elroy Kanye",
                                "elroykanye.github.io/elroykanye",
                                "elroykanye@gmail.com"
                        )

                )
                .license("Apache License Version 2.0")
                .build();
    }
}
