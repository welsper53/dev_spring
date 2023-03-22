// 부서명 정규식
export const validateDName = (e) => {
  // 사용자가 입력한 부서명
  const name = e.target.value;    // input onChange
  const han = /^[가-힣]+$/;
  const eng = /^[a-zA-Z]+$/;

  if (name.length === 0) {
    return " ";
  } else if (han.test(name) || eng.test(name)) {  // test의 리턴타입은 boolean
    return "";
  } else {
    return "부서명은 영어 또는 한글로만 가능합니다.";
  }
}