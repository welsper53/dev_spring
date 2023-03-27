// type을 정의하는 규칙 - 커링함수
// 매개변수 분할 처리
// 첫번째 파라미터: type, 두번째 파라미터: data 받아오는 인자
// payload - 수하물
// 개발자가 정의한 data나 에러처리에 필요한 메시지값
// 요청에 대한 응답 메시지로 사용이 가능하다
// -> (리덕스) Toast 객체
// 실제 서비스에서는 필요없다 -> react-redux사용하기 때문
// 순서대로 처리할 필요가 있다 - 커링함수 패턴 적용한다
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
