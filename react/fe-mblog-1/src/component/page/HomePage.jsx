import React from 'react'
import BlogHeader from '../include/BlogHeader'
import KaKaoMap from '../kakao/KaKaoMap'
import { ContainerDiv, FormDiv, HeaderDiv } from '../styles/FormStyle.js'

const HomePage = () => {
  return (
    <>
      <ContainerDiv>
        <BlogHeader />
        <HeaderDiv>
          <h1 style={{marginLeft:"10px"}}>터짐블로그</h1>
        </HeaderDiv>
        <FormDiv style={{textAlign: "center"}}>
          <div>이벤트존</div>
          <hr style={{height:"2px"}} />
          <div>추천수업존</div>
          <hr style={{height:"2px"}} />
          <div><KaKaoMap /></div>
          <div>카카오맵존</div>
          <hr style={{height:"2px"}} />
        </FormDiv>
      </ContainerDiv>
    </>
  )
}

export default HomePage
