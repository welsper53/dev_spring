import React from 'react'
import BlogHeader from '../include/BlogHeader'
import KaKaoMap from '../kakao/KaKaoMap'
import { FormDiv, HeaderDiv } from '../styles/FormStyle.js'

const HomePage = () => {
  return (
    <>
      {/* <ContainerDiv> */}
        <BlogHeader />
        <div className='container'>
          <div className="page-header">
            <div className="page-header">
              <h1 style={{marginLeft:"10px"}}>터짐블로그</h1>
            </div>
            <FormDiv style={{textAlign: "center"}}>
              <div>이벤트존</div>
              <hr style={{height:"2px"}} />
              <div>추천수업존</div>
              <hr style={{height:"2px"}} />
              <div><KaKaoMap /></div>
              <div>카카오맵존</div>
              <hr style={{height:"2px"}} />
            </FormDiv>
          </div>
        </div>
      {/* </ContainerDiv> */}
    </>
  )
}

export default HomePage
