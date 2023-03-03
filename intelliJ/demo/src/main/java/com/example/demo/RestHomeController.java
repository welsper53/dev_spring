package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
// 마임타입이 plain -> 텍스트로 간다
@RestController
public class RestHomeController {
    @GetMapping("/home")
    public String home() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
        return "spring gradle test";
    }
}
