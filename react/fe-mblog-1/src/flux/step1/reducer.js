import * as ActionType from './action-type.js'

// store에서 관리해야 하는 상태값의 종류가 점점 늘어나겠지
// -> 객체리터럴 - 열거형연산자 - n개 관리 (<- +초기화)
const initializeState = { count: 0 } // 구: let state

// react-redux에서는 worker가 dispatcher가 된다
// reducer, dispatch함수
export const reducer = (state = initializeState, action) => { // state가 undefined 되는것을 방지하기 위해 객체 선언
  // 무엇을 해야 하나요?
  // 상태를 바꾸면 createStore 안에 state의 참조 무결성이 깨진다
  // 리덕스에서는 상태를 바꾸는 함수는 반드시 새로운 상태를 반환해라
  // 새로운 상태라는 입력(Action)으로 상태의 객체를 줄테니 이 객체를 깊은 복사해서
  // 기존의 참조(링크)를 끊어라 - 그래야 side effect방지 가능하다
  switch(action.type) {
    case ActionType.INCREASE:
      return {...state, count: state.count+2} // 깊은 복사
    case ActionType.DECREASE:
      return {...state, count: state.count-1} // 깊은 복사
    case ActionType.RESET:
      return {...state, count: 0} // 깊은 복사
    default:
      return {...state}
  }
};