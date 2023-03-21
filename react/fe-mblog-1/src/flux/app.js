// 상태는 createStore() 안에 있다
const createStore = () => {
  let state;  // 상태를 담아두는 저장소
  // 함수를 담아두는 배열 선언
  let handlers = [];

  // 상태를 바꾸는 일을 한다
  const send = (action) => {
    console.log("send 호출");
    // 새로운 객체가 만들어진다
    state = worker(state, action);

    handlers.forEach(handler => handler())
  }

  const subscribe = ((handler) => {
    handlers.push(handler);
  })

  const getState = () => {
    return state
  }

  // 함수 안에서 함수를 리턴하도록 처리를 해야 바깥쪽에서 해당 함수를 요청할 수 있다
  return {
    send,     // 함수 == 객체; 파라미터로 들어온 상태를 받아서 가공해서 새로운 객체로 내보낸다
    getState, // 함수 - 상태정보를 담은 state 반환해준다
    subscribe,
  }
}

// react-redux에서는 worker가 dispatcher가 된다
const worker = (state = { count:0 }, action) => { // state가 undefined 되는것을 방지하기 위해 객체 선언
  // 무엇을 해야 하나요?
  // 상태를 바꾸면 createStore 안에 state의 참조 무결성이 깨진다
  // 리덕스에서는 상태를 바꾸는 함수는 반드시 새로운 상태를 반환해라
  // 새로운 상태라는 입력(Action)으로 상태의 객체를 줄테니 이 객체를 깊은 복사해서
  // 기존의 참조(링크)를 끊어라 - 그래야 side effect방지 가능하다
  switch(action.type) {
    case 'increase':
      return {...state, count: state.count+1} // 깊은 복사
      case 'decrease':
      return {...state, count: state.count-1} // 깊은 복사
    default:
      return {...state}
  }

}

// 자바스크립트에서는 함수도 파라미터로 넘길 수 있다
const store = createStore(worker) // index.js에서 생성할 것임 - props대신 중앙에서 즉시 한번에 가져다 준다

// subscribe함수 호출 시 파라미터로 콜백함수를 넘김
store.subscribe(() => {
  console.log(store.getState());
})

// action의 내용은 send에서 만듦
store.send({type:'increase'});  // 시그널 추가 - action
store.send({type:'increase'});
store.send({type: 'decrease'});

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