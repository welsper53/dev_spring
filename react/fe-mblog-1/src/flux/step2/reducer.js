import * as ActionType from './action-type.js'
import {initializeState} from './state.js';

// react-redux에서는 worker가 dispatcher가 된다
// reducer, dispatch함수
// reducer: 상탤를 변형하는 것
/** 
 * @param {*} state 상태값
 * @param {*} action 파라미터 액션 - dispatch store에 전달
 * @returns */
// action에 담긴 정보를 dispatch가 store에 전달하기 -> flux architech 
// OneWay 방식 (<- 한 방향으로만 흐름)
// action-dispatch-store-view
// action-type.js에 별도 정의함
export const reducer = (state = initializeState, action) => { // state가 undefined 되는것을 방지하기 위해 객체 선언
  // 무엇을 해야 하나요?
  // 상태를 바꾸면 createStore 안에 state의 참조 무결성이 깨진다
  // 리덕스에서는 상태를 바꾸는 함수는 반드시 새로운 상태를 반환해라
  // 새로운 상태라는 입력(Action)으로 상태의 객체를 줄테니 이 객체를 깊은 복사해서
  // 기존의 참조(링크)를 끊어라 - 그래야 side effect방지 가능하다
  switch(action.type) {
    case ActionType.INCREASE:
      return {...state, count: state.count +2} // 깊은 복사
    case ActionType.DECREASE:
      return {...state, count: state.count -1} // 깊은 복사
    case ActionType.RESET:
      return {...state, count: 0} // 깊은 복사
    case ActionType.SET_MSG:
      // 깊은 복사에서 두 번째 인자가 payload에 해당된다
      return {...state, status: action.bool, msg: action.msg}
    case ActionType.SET_FALSE:
      // 깊은 복사에서 두 번째 인자가 payload에 해당된다
      return {...state, status: action.bool, msg: action.msg}
    default:
      return {...state};
  }
};