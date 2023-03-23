import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import BlogHeader from '../include/BlogHeader'

const Profile = () => {
  console.log("Profile 호출")
  // 사용자 정보 담기
  const[user_id, setUserId] = useState()
  const[nickName, setNickName] = useState()
  const[profileImage, setProfileImage] = useState()
  const navigate = useNavigate()

  const getProfile = async () => {
    try {
      let data = await window.Kakao.API.request({
        url: "/v2/user/me",
      })
      console.log("아이디: "+data.id)
      console.log("닉네임: "+data.properties.nickname)
      console.log("프로필사진: "+data.properties.profile_image)
      setUserId(data.id)
      window.localStorage.setItem("user_id", user_id)
      setNickName(data.properties.nickname)
      window.localStorage.setItem("nickName", nickName)
      setProfileImage(data.properties.profile_image)
      window.localStorage.setItem("profileImage", profileImage)
      //navigate("/")
    } catch (error) {
      console.log(error)
    }
  }

  useEffect (() => {
    getProfile()
  })

  const kakaoLogout = async () => {
    // 로그아웃 처리
    console.log("kakaoLogout 호출")
    await axios({
      method: "GET",
      url: `https://kauth.kakao.com/oauth/logout?client_id=${process.env.REACT_APP_KAKAO_API_KEY}&logout_redirect_uri=http://localhost:3000`
    }).then(res => {    // 성공 시
      console.log(res)
      window.localStorage.removeItem("user_id")
      window.localStorage.removeItem("nickName")
      window.localStorage.removeItem("profileImage")
      navigate("/")
    }).catch(error => { // 실패 시
      console.log(error)
    })
  }

  return (
    <>
      <BlogHeader />
      <h3>{user_id}</h3>
      <h3>{nickName}</h3>
      <img src={profileImage} alt="프로필이미지"></img>
      <br />
      <button onClick={kakaoLogout}>카카오로그아웃</button>
    </>
  )
}

export default Profile
