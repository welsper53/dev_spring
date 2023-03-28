function first(param) {
  console.log(param)    // [Function 이름없는]
  param()
}

function second(param) {
  console.log(2)
}

first(second)

// 순서대로 꼭 처리가 되어야 할때 아래와 같이 작성한다
function func1() {  // outter 함수 - 클로저
  let num = 0       // 선언된 함수

  return function func2() { // 반환형이 함수인 경우이다
    return ++num    // 여기에 사용가능하다
  }
}

let account = func1()
console.log(account())    // 함수에 괄호가 없으면 단순 '[Funciton]' 출력

function one() {
  console.log(1)
}
function two() {
  console.log(2)
}