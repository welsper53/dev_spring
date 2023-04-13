package com.example.demo.auth;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class PrincipalDetailsService implements UserDetailsService {
    Logger logger = LogManager.getLogger(PrincipalDetailsService.class);
    @Autowired
    private UserRepository userRepository;


    // 아래 파라미터 username은 화면에서 사용하는, 즉 input type의 name과 반드시 일치해야함
    // -> /login 요청되면 시큐리티가 인터셉트해서 자동으로 진행
    // 만약 다르게 하려면 SecurityConfig에서 '.usernameParameter("mem_name")' 추가할 것!
    // loadUserByUsername 메소드의 리턴은 어디로 가는가?
    // 시큐리티 session(Authentication(내부 UserDetails))
    // 메소드 종료 시 @AuthentidationPrincipal 어노테이션 생성됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername 호출");
        logger.info(username); // 사용자가 입력한 이름

        // jpa query method로 검색하기 - mysql
        // mysql서버에 요청하는 코드
        User userEntity = userRepository.findByUsername(username); // 검색하기
        logger.info(userEntity);                // 사용자가 입력한 이름
        logger.info(userEntity.getRole());      // 사용자의 규칙 (ROLE_ADMIN, ROLE_USER ...)

        if(userEntity != null) {                //DB에서 찾아온 정보를 들고 있으면
            return new PrincipalDetails(userEntity);        //Authentication에 담을 수 있는 타입으로 변환
        }

        return null;
    }
}
