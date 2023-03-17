<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL(표현언어) 실습</title>
</head>
<body>
쿠키이름 : ${cookie.notebook.name} | ${cookie.hp.name} | ${cookie.coffee.name}
<br/>
쿠키값 : ${cookie.notebook.value} | ${cookie.hp.value} | ${cookie.coffee.value}
<br/>
쿠키경로 : ${cookie.notebook.path} | ${cookie.hp.path} | ${cookie.coffee.path}
</body>
</html>
