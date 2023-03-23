export const actionCreator = (type) => (payload) => ({
  type,
  payload,
});

export const createStore = (reducer) => {
  // 배치 위치는 index.js 배치 - store 생성
  let state;  // 상태를 담아두는 저장소
  // 함수를 담아두는 배열 선언
  let handlers = [];

  // 상태를 바꾸는 일을 한다 - useSelect 훅
  const dispatch = (action) => {
    console.log("send 호출");
    // 새로운 객체가 만들어진다
    state = reducer(state, action);

    // 나에게 구독신청한 사람들에게 모두 알림
    handlers.forEach(handler => handler())  // 전달 받은 함수를 호출해줘
  }

  const subscribe = (handler) => {   // useDispatch 훅
    // 콜백함수
    handlers.push(handler);
  }

  const getState = () => {
    return state
  }

  // 함수 안에서 함수를 리턴하도록 처리를 해야 바깥쪽에서 해당 함수를 요청할 수 있다
  return {
    dispatch,     // 함수 == 객체; 파라미터로 들어온 상태를 받아서 가공해서 새로운 객체로 내보낸다
    getState, // 함수 - 상태정보를 담은 state 반환해준다
    subscribe,
  }
} // end of Store
