<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
  /*  url: jstl/memberAction.jsp 요청하며 Redirect를 만나고
  *   MemberController(GetMapping("jsonMemberList"))
  *   - MemeberLogic
  *   - MemberDao
  *   --> @Configuration -> DatabaseConfiguration
  *   --> @Bean (글로벌)
  *   --> SqlSessionFactory -> SqlSessionTemplate
  *   --> member.xml -> id로 찾음
  * */

  // model.addObject("mList", mList);
  /*List<Map<String,Object>> mList =
          (List<Map<String,Object>>)request.getAttribute("mList");*/
  //response.sendRedirect("/member/jsonMemberList");
  // localhost:8000/member/memberList
%>
<%--
<c:forEach var="map" items="${mList}" varStatus="x" >
  ${mLIst[x.index]}
</c:forEach>
--%>
