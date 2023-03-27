import React from 'react';
import ReactDOM from 'react-dom/client';
import "bootstrap/dist/css/bootstrap.min.css";
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import ImageUploader from './service/imageUploader';
import "@fortawesome/fontawesome-free/js/all.js";

import { Provider } from 'react-redux';
import rootReducer from './redux/rootReducer';
import { legacy_createStore } from 'redux';
import AuthLogic from './service/authLogic';
import firebaseApp from './service/firebase';
import { setAuth } from './redux/userAuth/action';

// 리덕스 적용하기
const store = legacy_createStore(rootReducer);
// AuthLogic객체 생성하기
const authLogic = new AuthLogic(firebaseApp);
// store에 있는 초기 상태 정보 출력하기
store.dispatch(setAuth(authLogic.getUserAuth(), authLogic.getGoogleAuthProvider()))
console.log(store.getState());

// 이미지 업로더 객체 생성
const imageUploader = new ImageUploader();
const root = ReactDOM.createRoot(document.getElementById('root'));

// 리덕스 추가 - store 생성
// createStore호출
root.render(
  <>
    <Provider store={store}>
      <BrowserRouter>
        <App imageUploader={imageUploader}/>
      </BrowserRouter>
    </Provider>
  </>
);
