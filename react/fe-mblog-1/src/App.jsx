import { Route, Routes, useNavigate } from 'react-router-dom';
import LoginPage from './component/auth/LoginPage';
import DeptDetail from './component/dept/DeptDetail';
import KakaoRedirectHandler from './component/kakao/KakaoRedirectHandler';
import Profile from './component/kakao/Profile';
import DeptPage from './component/page/DeptPage';
import HomePage from './component/page/HomePage';
import MemberPage from './component/page/MemberPage';
import RepleBoardPage from './component/page/RepleBoardPage';
import { toastStatus } from './redux/toastStatus/state';
import Toast from './component/Toast'
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { setToastMsg } from './redux/toastStatus/action';
import SignupPage from './component/auth/SignupPage';
import KhSignup from './component/auth/KhSignup';
import KhLoginPage from './component/auth/KhLoginPage';
import { onAuthChange } from './service/authLogic';
import { memberListDB } from './service/dbLogic';

function App({authLogic, imageUploader}) {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const ssg = sessionStorage
  const toastStatus = useSelector(state => state.toastStatus)

  useEffect (() => {
    //dispatch(setToastMsg('회원가입 하세요'))
    const asyncDB = async() => {
      const auth = authLogic.getUserAuth()
      // 현재 인증된 사용자 정보를 가져온다
      const user = await onAuthChange(auth)

      // 사용자가 있으면 - userID가 있다
      // 구글 로그인으로 사용자 정보를 가지고 있을 때
      // user정보가 있으면 sessionStorage에 담는다 - email
      if (user) {
        console.log('user정보가 있을 때')
        ssg.setItem('email', user.email)

        const res = await memberListDB({MEM_ID: user.uid, type: 'auth'})
        // 오라클서버의 회원집합에 uid가 존재하면 - 세션스토리지에 값을 담자
        if (res.data) {
          const temp = JSON.stringify(res.data)
          const jsonDoc = JSON.parse(temp)

          ssg.setItem('nickname', jsonDoc[0].MEM_NICKNAME)
          ssg.setItem('status', jsonDoc[0].MEM_STATUS)
          ssg.setItem('auth', jsonDoc[0].MEM_AUTH)
          ssg.setItem('no', jsonDoc[0].MEM_NO)

          navigate('/')
          return    // 렌더링 종료
        }
        // 구글 로그인 했지만 false일 때
        // if () {
          
        // }
        // 오라클서버의 회원집합에 uid가 존재하지 않으면
        else {

        }
      } // end of 사용자 정보가 있을 때
      // 사용자 정보가 없을 때
      else {
        console.log('user정보가 없을 때')
        if (ssg.getItem('email')) {
          // 세션스토리지에 있는 값 모두 삭제하기
          ssg.clear()
          window.location.reload()    // 새로고침
        }
      } // end of 사용자 정보가 없을 때
    }

  }, [dispatch])

  return (
    <>
      <div style={{height: '100vh'}}>
        {toastStatus.status && <Toast />}
        <Routes>{/* 정확히 그 규칙을 따라달라는 exact={true} */}
          <Route path='/' exact={true} element={<HomePage />} />
          {/* <Route path='/login' exact={true} element={<LoginPage />} /> */}
          <Route path='/login' exact={true} element={<KhLoginPage authLogic={authLogic}/>} />
          {/* <Route path='/auth/signup' exact={true} element={<SignupPage authLogic={authLogic}/>} /> */}
          {<Route path='/login/signup' exact={true} element={<KhSignup authLogic={authLogic}/>} />}
          <Route path='/repleboard' element={<RepleBoardPage />} />
          <Route path='/dept/:gubun' element={<DeptPage imageUploader={imageUploader}/>} />
          {/* 컴포넌트 함수를 호출하는 것이다 -> 마운트(렌더링) - return호출되었다 */}
          <Route path='/deptdetail/:deptno' element={<DeptDetail imageUploader={imageUploader}/>} />
          <Route path='/auth/kakao/callback' exact={true} element={<KakaoRedirectHandler />} />
          <Route path='/profile' exact={true} element={<Profile/>}/>
          <Route path='/member' exact={true} element={<MemberPage imageUploader={imageUploader}/>}/>
        </Routes>
      </div>
    </>
  );
}

export default App;
