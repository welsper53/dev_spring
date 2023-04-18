package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)          // 요청이 지나가는 필터정보 확인 가능하다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)    // 권한을 체크하겠다는 설정 추가
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.inMemoryAuthentication()
                // 아래 메소드가 데프리케이트 대상인 이유는 테스트용도로만 사용하라는 경고의 의미임
                // 보안상 안전하지 않으니 쓰지 말것을 당부함. 그러나 지원은 끊지 않음
                .withUser(User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("123")
                        .roles("USER")
                ).withUser(User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("1234")
                        .roles("ADMIN")
                );

        http.csrf().disable();
        http.authorizeRequests()
                // localhost:5000/user로 요청하면 403발동 - 403접근권한이 없을 때
                .antMatchers("/user/**").authenticated()     // 인증만 되면 들어갈 수 있는 주소
                // manager나 admin 권한이 있는 사람만 들어온다
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // admin권한이 있는 사람만 들어온다
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()             // 위 세가지가 아닌 경우는 모두 허용함
                .and()
                .formLogin()
                .loginPage("/loginForm")   // 2차 단위테스트하기 - 권한에 따라서 페이지 제어
                .loginProcessingUrl("/login")   // 로그인 URL 설정 - 스프링 시큐리티가 인터셉트한다
                .defaultSuccessUrl("/");
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
