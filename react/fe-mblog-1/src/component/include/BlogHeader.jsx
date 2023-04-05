import React, { useEffect, useState } from 'react'
import { Button, Container, Nav, Navbar } from 'react-bootstrap'
import { Link, useNavigate } from 'react-router-dom'
import { logout } from '../../service/authLogic'

const BlogHeader = ({authLogic}) => {
  const navigate = useNavigate()
  const auth = authLogic.getUserAuth()  // "firebase/auth" 에서 주입해준다
  // 로그아웃버튼 추가하기

  // 왜 일반변수가 아닌 상태훅에 남는 것인가?
  // 상태훅에 관리하면 화면에 즉시 반영됨
  // 인증 (front-end -> sessionStorage, back-end -> session: cpu cache메모리)과 인가 구분할 수 있다
  // 동기화 처리 필요함 - 어려움 - 실전연습
  const [email, setEmail] = useState()

  // 의존성 배열이란?... 실행문(변수선언, 제어문, 로직-기능)이 재요청되는 기준임
  // 빈 배열 일 때는 한번만 호출된다
  // 빈 배열을 삭제하면 글자 하나만 입력 받아도 재요청이 일어난다 -> 비효율적
  // 리렌더링 발생하는 경우
  // : 1) 상태훅, 2) props, 3) 부모 컴포넌트가 변경되면
  // :-> return 코드 블록이 호출된다
  useEffect( () => {
    setEmail(sessionStorage.getItem("email"))

    // return
    // return () => {} // 인터셉트해서 무언가를 전처리가 필요할 때 사용한다
  },[]) // 의존성배열 - useMemo, useCallback(메모이제이션 - 효율성 - 실전)

  return (
	<>
      <Navbar bg="light">
        <Container fluid>
          <Link to="/" className='nav-link'>TerrGYM</Link>
          <Nav className="me-auto">
            <Link to="/home" className='nav-link'>Home</Link>
            <Link to="/dept/0" className='nav-link'>부서관리</Link>
            <Link to="/reple/board" className='nav-link'>게시판</Link>
            <Link to="/qna/list" className='nav-link'>QnA게시판</Link>
          </Nav>
          {/* js와 jsx 섞어 쓰기 */}
          {/* 참이 아닌경우 : null, undefined -> 주의할 것 */}
          { email && 
            <Button variant='primary' onClick={ () => {
              logout(auth); navigate('/login'); 
              window.location.reload();
              }} >Logout</Button>
          }
        </Container>
      </Navbar>
	</>
  )
}

export default BlogHeader
