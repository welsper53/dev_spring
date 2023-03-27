// store에서 관리해야 하는 상태값의 종류가 점점 늘어나겠지
// -> 객체리터럴 - 열거형연산자 - n개 관리 (<- +초기화)
// 상태 관리하는 변수 선언
// 선언된 변수들이 payload에 담긴다
export const initializeState = { 
  count: 0,
  status: false,        // Toast메시지 컴포넌트를 보여줄지
  msg: '',              // 사용자 요청에 대한 처리 결과 메시지
}; // 구: let state 