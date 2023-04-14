package com.example.demo.oauth;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/*
*   구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> 코드를 리턴(OAuth2-Client라이브러리 받음)
*   AccessToken 요청 (userRequest정보) -> loadUser호출 -> 구글로부터 회원프로필을 받는다
*   super.loadUser(userRequest).getAttributes() : Map<String,Object> 형태로 담아준다
*/
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    Logger logger = LogManager.getLogger(PrincipalOauth2UserService.class);
    @Autowired
    private UserRepository userRepository;

    // 구글로부터 받은 userRequest데이터에 대한 후처리 메소드 구현 - Profile정보 수집
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("loadUser 호출");
        logger.info("userRequest : " + userRequest);        // org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@6aad0775

        // 액세스 토큰 출력해보기
        logger.info("벤더정보: " + userRequest.getClientRegistration().getRegistrationId());                // google
        logger.info("구글에서 발급하는 토큰값: " + userRequest.getAccessToken().getTokenValue());             // ya29.a0Ael9sCOm3LqK...
        logger.info("구글에서 발급하는 id값: " + userRequest.getClientRegistration().getRegistrationId());   // google
        logger.info("구글에서 발급하는 clientID값 : " + userRequest.getClientRegistration().getClientId());  // 1810007...apps.googleusercontent.com

        // 구글 인증 후 프로필 정보를 가져와서 OAuth2User에 담기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        logger.info(oAuth2User.getAttribute("sub").toString());         // 104894737226430706199
        logger.info(oAuth2User.getAttribute("email").toString());       // welsper53@gmail.com

        String vender = userRequest.getClientRegistration().getRegistrationId();
        String sub = oAuth2User.getAttribute("sub").toString();
        String username = vender + "_" + sub;
        String password = "123";
        String email = oAuth2User.getAttribute("email").toString();
        String role = "ROLE_USER";

        // 구글 프로필을 가지고 회원가입 전에 이미 되어 있는지 체크하기
        User userEntity = userRepository.findByUsername(username);
        /*  Hibernate:
                select
                    user0_.id as id1_0_,
                    user0_.createDate as createda2_0_,
                    user0_.email as email3_0_,
                    user0_.password as password4_0_,
                    user0_.role as role5_0_,
                    user0_.username as username6_0_
                from
                    User user0_
                where
                    user0_.username=?                   */

        // username이 없는 지 확인
        if (userEntity == null) {
            logger.info("구글계정으로 회원가입 이력이 없습니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .build();

            userRepository.save(userEntity);
            /*  Hibernate:
                    insert
                    into
                        User
                        (createDate, email, password, role, username)
                    values
                        (?, ?, ?, ?, ?)                                 */
        } else {
            logger.info("구글계정으로 회원가입이 되어있습니다.");
        }

        //return super.loadUser(userRequest);
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
