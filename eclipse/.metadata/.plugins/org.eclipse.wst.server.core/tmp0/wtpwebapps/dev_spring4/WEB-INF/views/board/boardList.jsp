<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%
	List<Map<String,Object>> bList = (List)request.getAttribute("bList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 구현</title>
</head>
<body>
	<h3>게시글 목록</h3>
	<table border="1" width="300px">
		<tr>
			<th>글번호</th>
			<th>글제목</th>
			<th>작성자</th>
		</tr>
		<%
			for (int i=0; i<bList.size(); i++) {
				Map<String,Object> rmap = bList.get(i);
		%>
		<tr>
			<th><%=rmap.get("BM_NO") %></th>
			<th><%=rmap.get("BM_TITLE") %></th>
			<th><%=rmap.get("BM_WRITER") %></th>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>