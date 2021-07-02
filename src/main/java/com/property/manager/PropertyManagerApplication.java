package com.property.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
@PropertySource("classpath:/application.properties")
public class PropertyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyManagerApplication.class, args);
    }
}