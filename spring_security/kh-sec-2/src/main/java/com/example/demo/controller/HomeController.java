package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// 스프링 시큐리티에서는 별도의 세션 scope 관리한다
// Authentication
// @AuthenticationPrincipal
@Controller
public class HomeController {
    Logger logger = LogManager.getLogger(HomeController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index(HttpServletRequest req) {
        logger.info("Index 호출");
        return  "forward:index.jsp";
//        logger.info("admin 호출 : " + req.isUserInRole("ROLE_ADMIN"));
//        logger.info("user 호출 : " + req.isUserInRole("ROLE_USER"));
//        logger.info("manager 호출 : " + req.isUserInRole("ROLE_MANAGER"));
//
//        if (req.isUserInRole("ROLE_ADMIN")) {
//            return "forward:admin-index.jsp";
//        }
//        else if (req.isUserInRole("ROLE_USER")) {
//            return "forward:user-index.jsp";
//        }
//        else {
//            return "forward:index.jsp";
//        }
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "redirect:/loginForm.jsp";
    }

//    // "/login"이 요청되면 스프링 시큐리티가 인터셉트 해서 대신 로그인 진행한다
//    @GetMapping("/login")
//    public @ResponseBody String login(){
//        return "로그인 한 후 페이지";
//    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication
                                                , @AuthenticationPrincipal OAuth2User oauth) {
        logger.info("authentication: " + authentication.getPrincipal());

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        logger.info("oAuth2User: " + oAuth2User.getAttributes());
        logger.info("oauth: " + oauth.getAttributes());

        return "구글세션정보 확인";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "redirect:/joinForm.jsp";
    }
    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");
        // 패스워드 암호화 처리
        String rawPwd = user.getPassword();

        // NullPointer 또는 Autowired(required=true) 메시지 출력 시
        // -> SecurityConfig 빈등록 안했을때 뜨는 에러
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        user.setPassword(encPwd);

        userRepository.save(user);

        return "redirect:/loginForm.jsp";
    }

    @GetMapping("/login-error")
    public String loginError(){
        return "redirect:/loginError.jsp";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "redirect:/accessDenied.jsp";
    }

    // @PreAuthorize 사용하려면 반드시
    // @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)    // 권한을 체크하겠다는 설정 추가
    // 첫번째 파라미터는 롤에 따른 접근 제약 활성화 해준다
    // 두번째 파라미터는 해당 메소드에 롤을 부여할 때 추가한다
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/user")
    public String user(){
        logger.info("user");
        return "forward:user-index.jsp";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        logger.info("manager");
        return "manager";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin(){
        logger.info("admin");
        return "forward:admin-index.jsp";
    }


    @GetMapping("/auth")
    public @ResponseBody Authentication auth() {

        return SecurityContextHolder.getContext().getAuthentication();
    }
}


/*
	1. 스프링 시큐리티가 관리하는 세션이 따로 존재한다.
	2. 테스트 시나리오
		localhost:5000/test/login 엔터
		파라미터인 Authentication은 스프링 시큐리티로부터 의존성 주입받음
		스프링 시큐리티는 스프링 시큐리티 세션을 들고있다 -> 서버 세션 영역에 시큐리티가 관리하는 세션이 따로 존재
		시큐리티 세션은 반드시 Authentication 객체만 들어갈 수 있다
		Authentication이 시큐리티 세션 안에 있다는 건 로그인된 상태라는 의미
*/