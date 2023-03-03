package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
    // URL "/hello"가 들어올 때 아래 메소드를 호출한다
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");

        // 'resources/templates/{ViewName}.html' 파일을 실행
        // ViewName : 리턴값 명
        return "hello";
    }
}
