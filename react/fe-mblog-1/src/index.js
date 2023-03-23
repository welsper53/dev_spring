import React from 'react';
import ReactDOM from 'react-dom/client';
import "bootstrap/dist/css/bootstrap.min.css";
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import ImageUploader from './service/imageUploader';
import "@fortawesome/fontawesome-free/js/all.js";

// 이미지 업로더 객체 생성
const imageUploader = new ImageUploader();
const root = ReactDOM.createRoot(document.getElementById('root'));

// 리덕스 추가 - store 생성
// createStore호출
root.render(
  <>
    <BrowserRouter>
      <App imageUploader={imageUploader}/>
    </BrowserRouter>
  </>
);
