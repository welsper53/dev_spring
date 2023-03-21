import { Route, Routes } from 'react-router-dom';
import LoginPage from './component/auth/LoginPage';
import KakaoRedirectHandler from './component/kakao/KakaoRedirectHandler';
import Profile from './component/kakao/Profile';
import DeptPage from './component/page/DeptPage';
import HomePage from './component/page/HomePage';
import MemberPage from './component/page/MemberPage';

function App({imageUploader}) {
  console.log("App 호출")

  return (
    <>
      <Routes>{/* 정확히 그 규칙을 따라달라는 exact={true} */}
        <Route path='/' exact={true} element={<LoginPage />} />
        <Route path='/home' exact={true} element={<HomePage />} />
        <Route path='/dept' exact={true} element={<DeptPage />} />
        <Route path='/auth/kakao/callback' exact={true} element={<KakaoRedirectHandler />} />
        <Route path='/profile' exact={true} element={<Profile/>}/>
        <Route path='/member' exact={true} element={<MemberPage imageUploader={imageUploader}/>}/>
      </Routes>
    </>
  );
}

export default App;
