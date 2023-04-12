package com.example.demo.controller;

import com.example.demo.model.SecurityMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 스프링 시큐리티에서는 별도의 세션 scope 관리한다
// Authentication
// @AuthenticationPrincipal
@RestController
public class HomeController {
    Logger logger = LogManager.getLogger(HomeController.class);

    @GetMapping("/")
    public String index() {
        logger.info("Index 호출");

        return "홈페이지";
    }

    @GetMapping("/auth")
    public Authentication auth() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/user")
    public SecurityMessage user() {

        return SecurityMessage.builder()
                .auth(SecurityContextHolder.getContext().getAuthentication())
                .message("User 정보")
                .build();
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public SecurityMessage admin() {

        return SecurityMessage.builder()
                .auth(SecurityContextHolder.getContext().getAuthentication())
                .message("Admin 정보")
                .build();
    }
}
