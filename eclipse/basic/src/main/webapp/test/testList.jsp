<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testList목록페이지</title>
</head>
<body>
버튼 이벤트 처리<br>
<button id="btn_test">확인</button>
<script>
	const btn_test = document.querySelector('#btn_test');
	console.log(btn_test);
	btn_test.addEventListener('click', function(){
		alert("test");
	})
</script>
</body>
</html>