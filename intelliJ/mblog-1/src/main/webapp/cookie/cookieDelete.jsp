<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠키 삭제하기</title>
</head>
<body>
<%
	// 쿠키 정보는 사용자 컴퓨터에 저장되어 있다
	// 그래서 서버는 요청을 해야한다 -> 사용자 컴퓨터에게
	// 서버는 사용자를 어떻게 구분하나요?
	// - JSessionID발급
	// -> 쿠키에 저장됨 -> 쿠키값 기준으로 알아본다
	Cookie cookie = new Cookie("notebook", "");
	cookie.setMaxAge(0);
	response.addCookie(cookie);
%>
</body>
</html>