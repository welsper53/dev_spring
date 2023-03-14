<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Map<String,Object>> bList = null;
    bList = (List<Map<String,Object>>) request.getAttribute("bList");

    //if (bList != null) {
        for (int i=0; i<bList.size(); i++) {
            Map<String,Object> rMap = bList.get(i);
            out.print(rMap.get("BM_TITLE") + "<br>");
            out.print(rMap.get("BM_WRITER") + "<br>");
        }
    //}
%>
<html>
<head>
    <title>계층형 게시판(WEB-INF)</title>
</head>
<body>
<h3>계층형 게시판</h3>
</body>
</html>
