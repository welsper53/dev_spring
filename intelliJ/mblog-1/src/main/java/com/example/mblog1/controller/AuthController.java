package com.example.mblog1.controller;

import com.example.mblog1.model.KakaoProfile;
import com.example.mblog1.model.OAuthToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth/*")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    // 카카오 Redirect_url등록된 값으로 맞춤
    @GetMapping("/kakao/callback")
    public String kakaoCallback(HttpSession session, String code) {
        //public @ResponseBody kakaoCallback(HttpSession session, String code) {
        logger.info("kakaoCallback 호출");

        // POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // 이것을 위해 RestTemplate라이브러리가 있음
        RestTemplate rt = new RestTemplate();
        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "511d03f75f56df5ea1cd7253f92ba730");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
        // Http요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
                tokenRequest, String.class);

        logger.info(code);
        logger.info(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }

        logger.info(oAuthToken.toString());
        logger.info("access_token ==> " + oAuthToken.getAccess_token());

        // 사용자 정보 가져오기
        RestTemplate rtUser = new RestTemplate();
        // HttpHeaders 객체 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(headers2);
        // Http요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rtUser.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
                profileRequest, String.class);
        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }

        // User : username, password, email
        logger.info("카카오 아이디(번호) : " + kakaoProfile.getId());
        logger.info("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        logger.info("카카오 유저네임 : " + kakaoProfile.getProperties().nickname);

        String nickname = kakaoProfile.getProperties().nickname;
        session.setAttribute("nickname", nickname);

        return "redirect:/";
    }
}
