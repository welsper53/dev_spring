/* global daum */

import React, { useCallback, useState } from 'react'
import { Button } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'
import { memberInsertDB } from '../../service/dbLogic'
import { BButton, ContainerDiv, FormDiv, HeaderDiv } from '../styles/FormStyle'

// 회원가입 페이지
const Signup = () => {  // 컴포넌트 함수
  // use~~~ <- 훅이라고 한다 ()
  // 리액트 16.8 버전부터 지원
  // 함수형 프로그래밍에 대한 이점으로 훅을 지원하게 되었다
  const navigate = useNavigate()
  
  const [mem_uid, setMemUid] = useState("")
  const [mem_pw, setMemPw] = useState("")
  const [mem_pw2, setMemPw2] = useState("")
  const [mem_name, setMemName] = useState("")
  const [mem_nickname, setMemNickname] = useState("")
  const [mem_email, setMemEmail] = useState("")
  const [mem_tel, setMemTel] = useState("")
  const [mem_gender, setMemGender] = useState("")
  const [mem_birthday, setMemBirthday] = useState("")
  const [mem_zipcode, setMemZipcode] = useState("")
  const [mem_addr, setMemAddr] = useState("")
  const [mem_addr_dtl, setMemAddrDtl] = useState("")
  const [post, setPost] = useState({
    zipcode: '',
    addr: '',
    addrDetail: '',
  })
  
  
  // 사용자가 입력한 값을 useState에 초기화하기
  const handleUid = useCallback((e) => {
    setMemUid(e)
  }, [])
  
  const handlePW = useCallback((e) => {
    setMemPw(e)
  }, [])
  const handlePW2 = useCallback((e) => {
    setMemPw2(e)
  }, [])
  const handleName = useCallback((e) => {
    setMemName(e)
  }, [])
  const handleNickname = useCallback((e) => {
    setMemNickname(e)
  }, [])
  const handleEmail = useCallback((e) => {
    setMemEmail(e)
  }, [])
  const handleTel = useCallback((e) => {
    setMemTel(e)
  }, [])
  const handleGender = useCallback((e) => {
    setMemGender(e)
  }, [])
  const handleBirthday = useCallback((e) => {
    setMemBirthday(e)
  }, [])
  const handleZipcode = useCallback((e) => {
    setMemZipcode(e)
  }, [])
  const handleAddr = useCallback((e) => {
    setMemAddr(e)
  }, [])
  const clickAddr = (event) => {
    event.preventDefault()
    
    new daum.Postcode({
      oncomplete: function(data) {
        let addr = ''
        
        if (data.userSelectedType === 'R') {
          addr = data.roadAddress;  // 도로명
        }
        else {
          addr = data.jibunAddress; // 지번명
        }
        console.log(data)   // 전체 주소 정보 - 한글+영어
        console.log(addr)   // 주소 정보만
        // 기존의 참조관계를 끊는다 - 
        setPost({...post, zipcode: data.zonecode, addr: addr})  // 깊은 복사

        document.querySelector("#mem_zipcode").value = data.zonecode   // 화면에 자동으로 입력처리
        document.querySelector("#mem_addr").value = addr  // 선택한 주소정보를 input 컴포넌트에 자동입력 처리
        document.querySelector("#mem_addr_dtl").focus()   // addr이 입력되었을 때 커서 자동 이동 처리
      }
    }).open();
  }
  const handleAddrDtl = useCallback((e) => {
    setMemAddrDtl(e)
  }, [])
  
  

  // POST, @RequestBody, {} -> Map or VO 
  // -> 비동기 처리 -> Promise(resolve, reject)
  // async - await
  const memberInsert = async () => {
    const member = {
      mem_uid: mem_uid,
      mem_pw: mem_pw,
      mem_pw2: mem_pw2,
      mem_name: mem_name,
      mem_nickname: mem_nickname,
      mem_email: mem_email,
      mem_tel: mem_tel,
      mem_gender: mem_gender,
      mem_birthday: mem_birthday,
      mem_zipcode: mem_zipcode,
      mem_addr: mem_addr,
      mem_addr_dtl: mem_addr_dtl,
    }

    const res = await memberInsertDB(member)
    
    if (!res.data) {
      console.log("회원가입 실패")
    }
    else {
      console.log("회원가입 성공")
      // 회원가입 성공 시 로그인 화면으로 이동
      navigate("/")
    }
  } // end of memberInsert

  return (
    <>
      <ContainerDiv>
        <HeaderDiv>
          <h3 style={{marginLeft:"10px"}}>회원가입</h3>
        </HeaderDiv>
        <FormDiv>
          <div style={{width:"100%", maxWidth:"2000px"}}>
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>아이디</span>
            </div>
            <input id="mem_id" type="text" maxLength="50" placeholder="아이디를 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleUid(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>비밀번호</span>
            </div>
            <input id="mem_pw" type="text" maxLength="50" placeholder="비밀번호를 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handlePW(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>비밀번호 확인</span>
            </div>
            <input id="mem_pw2" type="text" maxLength="50" placeholder="비밀번호를 한번 더 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handlePW2(e.target.value)}}/>

            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>이름</span>
            </div>
            <input id="mem_name" type="text" maxLength="50" placeholder="이름을 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleName(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>닉네임</span>
            </div>
            <input id="mem_nickname" type="text" maxLength="50" placeholder="닉네임을 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleNickname(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>이메일</span>
            </div>
            <input id="mem_email" type="text" maxLength="50" placeholder="이메일을 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleEmail(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>연락처</span>
            </div>
            <input id="mem_tel" type="text" maxLength="50" placeholder="연락처를 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleTel(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>성별</span>
            </div>
            <input id="mem_gender" type="text" maxLength="50" placeholder="성별을 선택하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleGender(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>생일</span>
            </div>
            <input id="mem_birthday" type="text" maxLength="50" placeholder="생일을 입력하세요."
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleBirthday(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>우편번호</span>
            </div>
            <input id="mem_zipcode" type="text" maxLength="50" placeholder="우편번호"
                  style={{width:"200px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleZipcode(e.target.value)}}/>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>주소</span>
            </div>
            <input id="mem_addr" type="text" maxLength="50" placeholder="주소를 입력하세요"
                  style={{width:"350px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleAddr(e.target.value)}}/>
            <Button onClick={clickAddr}>주소검색</Button>
            
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <span>상세주소</span>
            </div>
            <input id="mem_addr_dtl" type="text" maxLength="50" placeholder="상세주소를 입력하세요" readOnly={post.addr ? false : true}
                  style={{width:"350px",height:'40px' , border:'1px solid lightGray', marginBottom:'5px'}} onChange={(e)=>{handleAddrDtl(e.target.value)}}/>

            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'5px'}}>
              <BButton onClick={memberInsert}>가입</BButton>
              <hr style={{margin:'10px 0px 10px 0px'}}/>
            </div>
          </div>
        </FormDiv>
      </ContainerDiv>
    </>
  )
}

export default Signup
