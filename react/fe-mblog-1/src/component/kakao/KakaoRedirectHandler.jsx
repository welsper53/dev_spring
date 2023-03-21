import axios from 'axios';
import qs from 'qs';
import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

const KakaoRedirectHandler = () => {
  // 카카오API 가져올 때 객체명을 Kakao로 해야한다
  // 카카오 객체를 global variable에 등록해주는 코드
  const {Kakao} = window;
  // 리액트에서는 location.href나 sendRedirect 대신 사용한다
  const navigate = useNavigate()
  // 카카오 서버에서 돌려주는 URL뒤에 쿼리스트링 가져오기 - mdn searchParams
  // http://localhost:3000/auth/kakao/callback
  //   ?code=9IxBfTN2rcOW9cKT1KLqRAGX667Pf4D2IQGh8xBbDGU-LIpKqjXRuFQoTBzJ4rALs7C8tgo9c-wAAAGHAaAF9A
  // 서블릿 - request.getParameter("code"); ==> 9IxBfTN2rcOW9cKT1KLqRAGX667Pf4D2IQGh8xBbDGU-LIpKqjXRuFQoTBzJ4rALs7C8tgo9c-wAAAGHAaAF9A
  let params = new URL(document.location).searchParams;
  let code = params.get("code");
  console.log("코드: "+code)
  const grant_type="authorization_code"
  const redirect_uri = "http://localhost:3000/auth/kakao/callback"

  const getToken = async () => {
    const payload = qs.stringify({
      grant_type: grant_type,
      client_id: process.env.REACT_APP_KAKAO_API_KEY,
      redirect_uri: redirect_uri,
      code: code
    })

    try {
      const res = await axios.post(
        "https://kauth.kakao.com/oauth/token",
        payload
      )
      window.Kakao.init(process.env.REACT_APP_KAKAO_API_KEY)
      console.log("토큰값: "+res.data.access_token)
      window.Kakao.Auth.setAccessToken(res.data.access_token);
      navigate("/profile")
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(()=> {
    getToken()
  })

  return (
    <>
      {/* 아무런 의미 없는 화면이다 - 거쳐서 다른 화면으로 이동하니까 <br/>
      - 루트컨텍스트 - 인증이 되면 /home으로 가자 <br/> */}
      {code}
    </>
  )
}

export default KakaoRedirectHandler
