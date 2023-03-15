import { Route, Routes } from 'react-router-dom';
import './App.css';
import MemberPage from './component/page/MemberPage';

function App() {
  console.log("App 호출")

  return (
    <>
      <Routes>{/* 정확히 그 규칙을 따라달라는 exact={true} */}
        <Route path='/member' exact={true} element={<MemberPage />}/>
      </Routes>
    </>
  );
}

export default App;
