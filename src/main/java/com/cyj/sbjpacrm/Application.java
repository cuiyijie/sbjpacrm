package com.cyj.sbjpacrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.cyj.sbjpacrm.repository")// Spring Jpa 启用注解
@EntityScan(basePackages = "com.cyj.sbjpacrm.entity")     //扫描Jpa实体对象
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
