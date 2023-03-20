import { Route, Routes } from 'react-router-dom';
import './App.css';
import LoginPage from './component/auth/LoginPage';
import KakaoRedirectHandler from './component/kakao/KakaoRedirectHandler';
import MemberPage from './component/page/MemberPage';

function App({imageUploader}) {
  console.log("App 호출")

  return (
    <>
      <Routes>{/* 정확히 그 규칙을 따라달라는 exact={true} */}
        <Route path='/' exact={true} element={<LoginPage />} />
        <Route path='/auth/kakao/callback' exact={true} element={<KakaoRedirectHandler />} />
        <Route path='/member' exact={true} element={<MemberPage imageUploader={imageUploader}/>}/>
      </Routes>
    </>
  );
}

export default App;
