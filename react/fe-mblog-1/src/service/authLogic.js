import { GoogleAuthProvider, getAuth, signInWithPopup } from "firebase/auth";
//import firebase from 'firebase';

class AuthLogic {
   // 생성자 정의 - 자바와는 다르게 선언 없이 초기화 가능하다
   // firebaseAuth변수명, googleProvider변수명 -> 전역변수
  constructor() {
    this.auth = getAuth();
    this.googleProvider = new GoogleAuthProvider();
  }
  getUserAuth = () => {
    return this.auth;
  };
  getGoogleAuthProvider = () => {
    return this.googleProvider;
  };
}

/* 외부 js에서 사용하려면 반드시 추가할 것 
  왜 리덕스를 공부해야 하나? -> 대답
*/
export default AuthLogic;
/* 
객체 자체가 클래스
export걸어놔서 외부에서 인스화 가능
*/

// 사용자가 변경되는지 지속적으로 체크하여 변경될 때마다 호출된다
export const onAuthChange = (auth) => {
  return new Promise((resolve) => {
    // 사용자가 바뀌었을 때 콜백함수를 받아서
    auth.onAuthStateChanged((user) => {
      resolve(user)
    });
  }); //end of Promise
}; //end of onAuthChange

// 로그아웃 버튼 클릭 시 호출하기
export const logout = (auth) => {
  return new Promise((resolve, reject) => {
    auth.signOut().catch( e => reject(e+'로그아웃 오류입니다'));
    
    // 로그인 성공 시 세션 스토리지에 담아둔 정보를 모두 지운다
    sessionStorage.clear();
    // 서비스를 더 이상 사용하지 않는 경우이므로 돌려줄 값은 없닥
    resolve(); // 그래서 파라미터는 비웠다
  });
} // end of logout

// 로그인 시도 시 구글 인증인지 깃허브 인증인지 문자열로 넘겨받음
// 구글 인증인 경우 - Google
// 깃허브 인증인 경우 - Github
export const loginGoogle = (auth, googleProvider) => {
	return new Promise((resolve, reject) => {
    // 제공자의 정보이면 팝업을 띄워서 로그인 진행하도록 유도한다
		signInWithPopup(auth, googleProvider).then(
			(result) => {
				const user = result.user;//구글에 등록되어 있는 profile  정보가 담겨있음
				console.log(user)
				resolve(user)
			}
		).catch(e=> reject(e))
	})
};