package com.example.mblog1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@SpringBootApplication
public class Mblog1Application {

    public static void main(String[] args) {
        SpringApplication.run(Mblog1Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 두 포트 모두 서비스를 제공하지만 3000은 동기화 된 서비스 제공이고
                // 7000번은 build 된 코드까지만 제공한다
                // 결과적으로 3000번은 개발서버로 7000번 서비스 서버로 사용하면 될 것이다
                registry.addMapping("/**").allowedOrigins("http://localhost:3000", "http://localhost:7000");
            }
        };
    }
}
