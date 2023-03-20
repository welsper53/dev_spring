import React from 'react'
import { Image } from 'react-bootstrap'

const LoginPage = () => {
  const REDIRECT_URI="http://localhost:3000/auth/kakao/callback"
  const KAKAO_AUTH_URL=`https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_API_KEY}&redirect_uri=${REDIRECT_URI}`
  return (
    <div>
      <a href={KAKAO_AUTH_URL} >
        <Image src='/images/kakao/kakao_login_medium_wide.png' />
      </a>
    </div>
  )
}

export default LoginPage
