package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
// 마임타입을 위해 (<-> @RestController)
@Controller
@RequestMapping("/home/*")  // "WEB-INF/views/home/" 접근
public class HomeController {
    @GetMapping("index")
    // => URL: "http://localhost:7000/home/index"
    public String index() {
        log.info("index호출");
        // Servlet으로 요청 -> JSP(index.jsp)
        // 확장자를 자동으로 "*.jsp" 붙어있다 (application.jsp)
        return "home/index";    // 경로 앞에 '/'가 붙어있다 (application.jsp)
    }

    @GetMapping("index2")
    // => URL: "http://localhost:7000/home/index2"
    public String index2() {
        log.info("index2호출");
        // Servlet으로 요청 -> JSP(index.jsp)
        // 확장자를 자동으로 "*.jsp" 붙어있다 (application.jsp)
        return "redirect:index";    // 경로 앞에 '/'가 붙어있다 (application.jsp)
    }
}
