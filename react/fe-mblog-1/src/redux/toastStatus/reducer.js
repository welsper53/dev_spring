import { toastStatus } from "./state";
import { SET_FALSE, SET_MSG } from './action'

// 아래 toastInfo함수 이름을 직접 사용하지 않아요
export default function toastInfo(state =toastStatus, action) {
  switch (action.type) {
    case SET_MSG:
      return {
        ...state, 
        status: action.bool, 
        msg:action.msg,
      }
    case SET_FALSE:
      return {
        ...state, 
        status: action.bool, 
        msg:action.msg,
      }
    default:
      return {
        ...state, 
      }
  }
} // end of toastInfo