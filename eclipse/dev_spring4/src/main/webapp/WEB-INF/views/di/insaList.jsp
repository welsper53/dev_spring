<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
	List<String> insaList = (List<String>)request.getAttribute("insaBean");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insaBean객체출력[WEB-INF]</title>
</head>
<body>
	<table width="300px" border="1">
	<%
		for (int i=0; i<insaList.size(); i++) {
			String msg = insaList.get(i);
	%>
		<tr>
			<td><%=msg %></td>
		</tr>
	<%
		} // end of for
	%>
	</table>
</body>
</html>