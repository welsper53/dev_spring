import { Route, Routes } from 'react-router-dom';
import LoginPage from './component/auth/LoginPage';
import DeptDetail from './component/dept/DeptDetail';
import KakaoRedirectHandler from './component/kakao/KakaoRedirectHandler';
import Profile from './component/kakao/Profile';
import Signup from './component/member/Signup';
import DeptPage from './component/page/DeptPage';
import HomePage from './component/page/HomePage';
import MemberPage from './component/page/MemberPage';
import RepleBoardPage from './component/page/RepleBoardPage';

function App({imageUploader}) {
  return (
    <>
      <Routes>{/* 정확히 그 규칙을 따라달라는 exact={true} */}
        <Route path='/' exact={true} element={<LoginPage />} />
        <Route path='/home' exact={true} element={<HomePage />} />
        <Route path='/member/signup' exact={true} element={<Signup />} />
        <Route path='/repleboard' element={<RepleBoardPage />} />
        <Route path='/dept/:gubun' element={<DeptPage imageUploader={imageUploader}/>} />
        {/* 컴포넌트 함수를 호출하는 것이다 -> 마운트(렌더링) - return호출되었다 */}
        <Route path='/deptdetail/:deptno' element={<DeptDetail imageUploader={imageUploader}/>} />
        <Route path='/auth/kakao/callback' exact={true} element={<KakaoRedirectHandler />} />
        <Route path='/profile' exact={true} element={<Profile/>}/>
        <Route path='/member' exact={true} element={<MemberPage imageUploader={imageUploader}/>}/>
      </Routes>
    </>
  );
}

export default App;
