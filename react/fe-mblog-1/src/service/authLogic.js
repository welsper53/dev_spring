import { GoogleAuthProvider, getAuth, signInWithPopup, createUserWithEmailAndPassword
  , EmailAuthProvider, sendEmailVerification, signInWithEmailAndPassword } from "firebase/auth";
//import firebase from 'firebase';

class AuthLogic {
   // 생성자 정의 - 자바와는 다르게 선언 없이 초기화 가능하다
   // firebaseAuth변수명, googleProvider변수명 -> 전역변수
  constructor() {
    this.auth = getAuth();
    this.googleProvider = new GoogleAuthProvider();
    //this.kakaoProvider = new KakaoAuthProvider();
    //this.githubProvider = new GihubAuthProvider();
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
      //파라미터 주입
      resolve(user); //내보내지는 정보 - View계층 - App.jsx
    });
  }); //end of Promise
}; //end of onAuthChange

// 로그아웃 버튼 클릭 시 호출하기
export const logout = (auth) => {
  return new Promise((resolve, reject) => {
    auth.signOut().catch( e => reject(e+'로그아웃 오류입니다'));
    
    //우리회사가 제공하는 서비스를 누리기 위해서는 구글에서 제공하는 기본 정보 외에
    //추가로 필요한 정보 있다 - 테이블 설계 - 세션
    //로그인 성공시 세션 스토리지에 담아둔 정보를 모두 지운다
    window.sessionStorage.clear();
    //서비스를 더 이상 사용하지 않는 경우이므로 돌려줄 값은 없다
    resolve(); //그래서 파라미터는 비웠다
  });
} // end of logout

//이메일과 비번으로 회원가입 신청을 한 경우 로그인 처리하는 함수임
// auth - AuthLogic클래스 생성자 getAuth() - auth
//두번째와세번째 - email, password
export const loginEmail = (auth, user) => {
  console.log(auth)
  console.log(user.email+", "+user.password)

  return new Promise((resolve, reject) => {
    signInWithEmailAndPassword(auth, user.email, user.password)
    .then((userCredential) => {
      // Signed in
      const user = userCredential.user;
      console.log(user)

      resolve(userCredential)
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
      console.log(errorCode+", "+errorMessage)

      reject(error)
    });
  })
} // end of loginEmail


// 로그인 시도 시 구글 인증인지 깃허브 인증인지 문자열로 넘겨받음
// 구글 인증인 경우 - Google
// 깃허브 인증인 경우 - Github
export const loginGoogle = (auth, googleProvider) => {
	return new Promise((resolve, reject) => {
    // 제공자의 정보이면 팝업을 띄워서 로그인 진행하도록 유도한다
		signInWithPopup(auth, googleProvider)    //팝업 열림
    .then((result) => {
				const user = result.user;//구글에 등록되어 있는 profile  정보가 담겨있음
				console.log(user)
				resolve(user)
			}
		).catch(e=> reject(e))
	})
};


export const signupEmail = (auth, user) => {
  return new Promise((resolve, reject) => {
    createUserWithEmailAndPassword(auth, user.email, user.password)
      .then((userCredential) => {
        sendEmail(userCredential.user).then(() => {
          resolve(userCredential.user.uid);
        });
      })
      .catch((e) => reject(e));
  });
};
export const linkEmail = (auth, user) => {
  console.log(auth);
  console.log(auth.currentUser);
  console.log(user);
  return new Promise((resolve, reject) => {
    console.log(user.email + "," + user.password);
    const credential = EmailAuthProvider.credential(user.email, user.password);
    console.log(credential);
    console.log(auth.currentUser.uid);
    resolve(auth.currentUser.uid);
    /* 인증정보가 다른 사용자 계정에 이미 연결되어 있다면 아래 코드 에러 발생함
    linkWithCredential(auth.currentUser, credential)
      .then((usercred) => {
        console.log(usercred);
        const user = usercred.user;
        console.log("Account linking success", user.uid);
        resolve(user.uid);
      })
      .catch((e) => reject(e));
    */
  });
};
export const sendEmail = (user) => {
  return new Promise((resolve, reject) => {
    sendEmailVerification(user)
      .then(() => {
        resolve("해당 이메일에서 인증메세지를 확인 후 다시 로그인 해주세요.");
      })
      .catch((e) => reject(e + ": 인증메일 오류입니다."));
  });
};