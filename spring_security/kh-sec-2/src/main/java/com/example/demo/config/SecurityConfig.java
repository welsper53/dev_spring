package com.example.demo.config;

import com.example.demo.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 1-1에서는 SecurityConfig파일을 생성하지 않았다
// 스프링 시큐리티에서 제공하는 인증화면으로 처리 진행했다
// 이 클래스를 정의하지 않으면 스프링시큐리티가 제공하는 DefaultLoginPageGeneratingFilterrk
// 제어권을 가져가서 개발자가 만든 로그인 화면을 만날 수 없다
// -> 결론 : 내가 주도하는 인증처리를 위해서 반드시 선언한다
@EnableWebSecurity(debug = true)          // 요청이 지나가는 필터정보 확인 가능하다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)    // 권한을 체크하겠다는 설정 추가
public class SecurityConfig {
    // 암호화가 안된 비밀번호(평문)로는 로그인이 안된다
    // 패스워드를 암호화 위한 코드 추가
    // Bean어노테이션을 적으면 해당 메소드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Autowired
    PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
    /*
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

    // 만약 관리자 계정이면 하위인 유저 계정에 접근할 수 있도록 처리하려면 아래 코드를 추가함
    // 유저는 관리자에 접근이 안되지만 관리자는 유저페이지도, 관리자페이지 접근 가늘하도록 설정
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);

//        auth.inMemoryAuthentication()
//                // 아래 메소드가 데프리케이트 대상인 이유는 테스트용도로만 사용하라는 경고의 의미임
//                // 보안상 안전하지 않으니 쓰지 말것을 당부함. 그러나 지원은 끊지 않음
//                .withUser(User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("123")
//                        .roles("USER")
//                ).withUser(User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("1234")
//                        .roles("ADMIN")
//                );

        http.csrf().disable();          // csrf filter 비활성화 하기
        http.authorizeRequests()    // http 요청으로 들어오는 것에 대해 매칭하기
                // localhost:5000/user로 요청하면 403발동 - 403접근권한이 없을 때
                .antMatchers("/user/**").authenticated()     // 인증만 되면 들어갈 수 있는 주소
                // manager나 admin 권한이 있는 사람만 들어온다
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // admin권한이 있는 사람만 들어온다
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")       // 어노테이션 @EnableGlobalMethodSecurity(securedEnabled = true) 설정되어 있어야 한다
                .anyRequest().permitAll()             // 위 세가지가 아닌 경우는 모두 허용함
                .and()                                  // 새로운 요청 건(connectio)이 있을 때마다 ".and()"를 추가한다
                .formLogin()
                .loginPage("/loginForm")   // 2차 단위테스트하기 - 권한에 따라서 페이지 제어
                .loginProcessingUrl("/login")   // 로그인 URL 설정 - 스프링 시큐리티가 인터셉트해서 대신 로그인 진행한다
                .failureUrl("/login-error")
                .defaultSuccessUrl("/", false)
                .and()
                // 구글 로그인 기능 구현 추가분
                .oauth2Login()
                .loginPage("/loginForm")
                // 1. 인증받기 -> 2. 엑세스 토큰(권한) -> 3. 사용자 프로필정보 가져오기 -> 4. 그 정보로 회원가입 처리가능(여기선 미적용)
                // 구글 로그인이 성공되면, 코드를 받는게 아니라 엑세스 토큰과 사용자에 대한 프로필정보를 한번에 받아옴
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
//                // 현재 페이지에서 로그아웃을 눌렀을 때 로그인 페이지가 아니라 메인 페이지에 있도록 해준다
//                .logout(logout -> logout.logoutSuccessUrl("/"))
//                // 403 접근제한 예외 발생 시 이동한 페이지 요청URL 작성하기
//                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));
        return http.build();
    }//end of filterChain
}

/*
 * 테스트 시나리오
 * localhost:5000 <-요청은 권한과 상관없이 열림
 *
 * localhost:5000/user
 * localhost:5000/admin
 * localhost:5000/manager
 * -> 로그인 페이지로 이동함
 *
 * 첫번째 - user롤일 때
 * localhost:5000/user 출력되고
 * localhost:5000/admin 403발동
 * localhost:5000/manager 403발동
 *
 * 두번째 - admin로그인 했을 때
 * localhost:5000/user 403발동
 * localhost:5000/admin 출력
 * localhost:5000/manager 출력
 *
 * 세번째 - manager로그인 했을 때
 * localhost:5000/user 403발동
 * localhost:5000/admin 403발동
 * localhost:5000/manager 출력
 */
