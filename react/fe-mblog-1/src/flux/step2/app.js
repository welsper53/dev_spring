import { decrease, increase, setToastFalse, setToastMsg } from "./actions.js";
import { reducer } from "./reducer.js"  // worker함수
import { createStore } from "./redux.js";

// 사용 - 함수 호출 - store생성하기 - index.js - 리액트
// 왜? index.js인가?
// - 생성한 스토어에 모든 전역 상태값을 저장하고 싶기 때문
// - app.js에 있는 코드가 리액트 컴포넌트에 써야하는 코드이다
// 문제 제기 - app.js 하나에 모두 있을 때는 파라미터에 reducer(구:worker) 파라미터로 넘겨야 한다
const store = createStore(reducer); // index.js에서 생성할 것임 - props대신 중앙에서 즉시 한번에 가져다 준다

// subscribe함수 호출 시 파라미터로 콜백함수를 넘김
store.subscribe(() => {           // 구독발행 모델 - 함수 호출
  // getState리액트에서 useSelector(state=>state.userAuth) 상태값을 
  // store에서 읽어들일 때 사용한다
  console.log(store.getState());  // 변경된 상태값 출력 - 리액트 컴포넌트가 마운트 될 때 찍기
  const state = store.getState();
  document.querySelector("#count").append(state.count)
  document.querySelector("#msg").append(state.msg)

})

// action의 내용은 dispatch 만듦
// 사용자가 버튼을 클릭했을 때 시그널 발생한다
// - type정해서 value를 store에 전달한다
// store가 받아서(useSelectoer훅) 전역변수로 관리된다
// - G컴포넌트에서 즉시 바로 사용가능하다
store.dispatch(increase());  // 시그널 추가 - action - 리액트: const dispatch=useDipatch() -> dispatch(type)
store.dispatch(increase());  // 시그널 추가 - action
store.dispatch(decrease());  // 시그널 추가 - action
store.dispatch(setToastMsg('관리자에게 문의하세요')); //
store.dispatch(setToastFalse());

/* 
  함수는 객체이다
  ->  소문자로 선언하면 함수이고
      대문자로 선언하면 화면을 렌더링하는 컴포넌트이다

  return에서는 상태값을 직접 넘겨주지 않는다
  상태는 createStore함수에 있지만
  변경하거나 읽거나 하는 코드들은 UI의 Component들이다
  이 컴포넌트들은 createStore함수의 바깥쪽에 위치한다

  1. UI한테는 직접적인 상태를 주지 않는다
  - state변수를 return으로 보내지 않는다

  문제제기
  : 컴포넌트(HomePage.jsx, LoginPage.jsx)가 여러 개 있는 상황에서 
    어떤 컴포넌트의 데이터가 변경되었는지 어떻게 알고서 getState함수를 호출할까?
    -> 구독발행 모델(Pub and Subscribe)
*/