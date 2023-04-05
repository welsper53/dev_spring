import React, { useEffect, useState } from 'react'
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import BlogHeader from '../include/BlogHeader';
import { ContainerDiv, FormDiv, HeaderDiv } from '../styles/FormStyle';
import KakaoMap from '../kakao/KaKaoMap'

const HomePage = ({ authLogic }) => {
	const member = window.localStorage.getItem('member')
	const [email, setEmail] = useState()
	useEffect(()=> {
		setEmail(sessionStorage.getItem("email"))
	},[])
	console.log(JSON.parse(member))
	const jsonDoc = JSON.parse(member)
	//console.log(jsonDoc.mem_id + ' , '+ jsonDoc.mem_pw)
	const navigate = useNavigate()
	const handleLogin = () => {
		console.log('로그인 요청')
		navigate('/login')
	}
  return (
	<>
		<ContainerDiv>
			<BlogHeader authLogic={authLogic} />
			<HeaderDiv>
				<h1 style={{marginLeft:"10px"}}>터짐블로그</h1>
				{ !email && <Button onClick={handleLogin}>로그인</Button> }
			</HeaderDiv>
			<FormDiv style={{textAlign: 'center'}}>
				<div>이벤트존</div>
				<hr style={{height:"2px"}} />
				<div>추천 수업존</div>
				<hr style={{height:"2px"}} />
				<div><KakaoMap /></div>
				<div>카카오맵존</div>
				<hr style={{height:"2px"}} />
			</FormDiv>
		</ContainerDiv>
	</>
  )
}

export default HomePage;