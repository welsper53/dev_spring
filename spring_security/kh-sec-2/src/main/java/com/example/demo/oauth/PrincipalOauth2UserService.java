package com.example.demo.oauth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    Logger logger = LogManager.getLogger(PrincipalOauth2UserService.class);

    // 구글로부터 받은 userRequest데이터에 대한 후처리 메소드 구현 - Profile정보 수집
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("loadUser 호출");
        logger.info("userRequest : " + userRequest);

        // 액세스 토큰 출력해보기
        logger.info("구글에서 발급하는 토큰값: " + userRequest.getAccessToken().getTokenValue());
        logger.info("구글에서 발급하는 id값: " + userRequest.getClientRegistration().getRegistrationId());

        return super.loadUser(userRequest);
    }
}
