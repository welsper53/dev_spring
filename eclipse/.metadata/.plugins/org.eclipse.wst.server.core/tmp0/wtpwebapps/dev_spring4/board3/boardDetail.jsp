<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map"%>    
<%
	List<Map<String,Object>> getBoardList = 
		(List<Map<String,Object>>)request.getAttribute("bList");
	String bm_title = null;
	String bm_writer = null;
	String bm_content = null;
	String bm_pw = null;
	String bm_no = null;
	String bm_group = null;
	String bm_pos = null;
	String bm_step = null;
	if(getBoardList!=null && getBoardList.size()>0){
		bm_title = getBoardList.get(0).get("BM_TITLE").toString();
		bm_writer = getBoardList.get(0).get("BM_WRITER").toString();
		bm_content = getBoardList.get(0).get("BM_CONTENT").toString();
		bm_pw = getBoardList.get(0).get("BM_PW").toString();
		bm_no = getBoardList.get(0).get("BM_NO").toString();
		bm_group = getBoardList.get(0).get("BM_GROUP").toString();
		bm_pos = getBoardList.get(0).get("BM_POS").toString();
		bm_step = getBoardList.get(0).get("BM_STEP").toString();
	}
	out.print(getBoardList);
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글상세보기</title>
<!-- 공통 코드 include처리 -->
<%@ include file="../common/easyUI_common.jsp" %>
<script type="text/javascript">
	function addAction(){
		$("#f_boardAdd").attr("action","/board3/boardInsert.st3");
		$("#f_boardAdd").submit();
		//부모창에 함수를 호출할때 opener.함수명();
		//opener.boardList();
		//self.close();
	}
	function updateForm(){
		//alert("updateForm 호출 성공");
		$('#d_boardUpd').dialog({
			title:'글수정'
		  ,width:720
		  ,height:450
		  ,closed:false
		  ,cache:false
		 ,modal:true	  
		});
		//$('#d_boardUpd').dialog('open');	
		//$('#d_boardUpd').dialog('refresh', '');
	}
	boardUpd = () => {
		$("#uf_board").attr("action","/board3/boardUpdate.st3");
		$("#uf_board").submit();		
	}
	//댓글쓰기
	function repleForm(){
		$("#dlg_boardAdd").dialog('open');
	}
	//글삭제하기 이벤트 처리
	function boardDelView(){
		console.log("boardDelView호출 성공");
		  $('#d_boardDel').dialog({
			    title: '글삭제',
			    buttons: btn_boardDel,
			    width: 420,
			    height: 250,
			    closed: true,
			    cache: false,
			    href: 'boardDelForm.jsp?bm_no=<%=bm_no%>&bm_pw=<%=bm_pw%>',
			    modal: true
	   }); 
	   $('#d_boardDel').dialog('open');		
	}
	//글삭제 화면에서 확인 버튼을 클릭했을 때
	const boardDel = () => {
		alert('삭제처리');
		const db_pw = '<%=bm_pw%>'
		const u_pw = $("#u_pw").textbox('getValue');
		//alert("db_pw:"+db_pw+", u_pw:"+u_pw);
		//alert("사용자가 입력한 비번:"+$("#db_pw").textbox('getValue'));
		//사용자가 입력한 비번과 DB에서 읽어온 비번을 비교하여
		//일치하면 삭제 처리 진행하고
		//불일치하면 비번을 다시 입력받도록 해주세요.
		if(u_pw==db_pw){
			//alert("같다");
			$.messager.confirm('Confirm','정말 삭제하시겠습니까?',function(r){
			 //r:true-ok, false-cancel
				if (r){//자바스크립트는 0이면 false 나머지 true
			    	location.href="./boardDelete.st3?bm_no=<%=bm_no%>";    
			    }
			});
		}else{
			console.log('비번이 달라요.');
			$("#db_pw").textbox('setValue','');
		}
	}
	function boardDelClose(){
		 $('#d_boardDel').dialog('close');
	}
	function boardList(){
		location.href="/board3/boardList.st3";
	}
</script>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
		
	});
</script>
    <table align="center" id="p" class="easyui-panel" title="글상세보기" data-options="footer:'#tb_read'"
        style="width:670px;height:380px;padding:10px;background:#fafafa;">
	    	<tr>
	    	<td>제목</td>
	    	<td><input id="bm_title" value="<%=bm_title %>" name="bm_title" data-options="width:'450px'" class="easyui-textbox"></td>
	    	</tr>
	    	<tr>
	    	<td>작성자</td>
	    	<td><input id="bm_writer" value="<%=bm_writer %>" name="bm_writer" class="easyui-textbox"></td>
	    	</tr>
	    	<tr>
	    	<td>내용</td>
	    	<td><input id="bm_content" value="<%="내용" %>" name="bm_content" data-options="multiline:'true', width:'570px', height:'90px'" class="easyui-textbox"></td>
	    	</tr>
	    	<tr>
	    	<td>비밀번호</td>
	    	<td><input id="bm_pw" value="<%=bm_pw %>" name="bm_pw" class="easyui-passwordbox"></td>
	    	</tr>	    	
	   </table>
	 <div id="tb_read" style="padding:2px 5px;" align="center">
	    <a href="javascript:repleForm()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">댓글쓰기</a>
	    <a href="javascript:updateForm()" class="easyui-linkbutton" iconCls="icon-add" plain="true">수정</a>
	    <a href="javascript:boardDelView()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">삭제</a>
	    <a href="javascript:boardList()" class="easyui-linkbutton" iconCls="icon-search" plain="true">목록</a>
	</div>
		<!-- 글삭제 시작 -->
		<div id="d_boardDel" closed="true" class="easyui-dialog" style="padding:20px 50px">
			<div id="btn_boardDel" align="right">
			<a href="javascript:boardDel()" class="easyui-linkbutton" iconCls="icon-ok" style="width:90px">확인</a>
			<a href="javascript:boardDelClose()" class="easyui-linkbutton" iconCls="icon-cancel" style="width:90px">닫기</a>
			</div>
		</div>
		<!-- 글삭제  끝   -->
		<!-- 글수정 시작 -->
		<div id="d_boardUpd" closed="true" class="easyui-dialog" style="padding:20px 50px">
<form id="uf_board" method="get">
<input type="hidden" id="bm_no" name="bm_no" value="<%=bm_no %>">
<input type="hidden" id="bs_seq" name="bs_seq" value="<%=1 %>">
<input type="hidden" id="old_file" name="old_file" value="이전파일명">
<table align="center" width="650px" height="280px">
	<tr>
		<td width="120px">글제목</td>
		<td width="580px">
			<input id="bm_title" value="<%=getBoardList.get(0).get("BM_TITLE").toString() %>" name="bm_title" class="easyui-textbox">
		</td>
	</tr>
	<tr>
		<td width="120px">작성자</td>
		<td width="580px">
			<input id="bm_writer" value="<%=bm_writer %>" name="bm_writer" class="easyui-textbox">
		</td>
	</tr>	
	<tr>
		<td width="120px">내용</td>
		<td width="580px">
			<input id="bm_content" multiline="true" value="<%=bm_content %>" name="bm_content" class="easyui-textbox" style="width:100%;height:100px">
		</td>
	</tr>	
	<tr>
		<td width="120px">첨부파일</td>
		<td width="580px">
			<input id="b_file" name="b_file" class="easyui-filebox" style="width:100%">
		</td>
	</tr>	
	<tr>
		<td width="120px">비번</td>
		<td width="580px">
			<input id="bm_pw" name="bm_pw" class="easyui-textbox" style="width:100px">
		</td>
	</tr>	
</table>
</form>		
			<div id="btn_boardUpd" align="right">
			<a href="javascript:boardUpd()" class="easyui-linkbutton" iconCls="icon-ok" style="width:90px">등록</a>
			<a href="javascript:boardUpdClose()" class="easyui-linkbutton" iconCls="icon-cancel" style="width:90px">닫기</a>
			</div>
		</div>
		<!-- 글수정  끝  -->
		<!-- 댓글쓰기 시작 -->
<!--================== [[댓글쓰기 화면]] ==================-->
<div id="dlg_boardAdd" title="댓글쓰기" class="easyui-dialog" style="width:600px;height:400px;padding:10px" data-options="closed:'true',modal:'true',footer:'#tbar_boardAdd'">	
<!-- 
form전송시 encType옵션이 추가되면 request객체로 사용자가 입력한 값을 꺼낼 수 없다.
MultipartRequest  => cos.jar
 -->	
<!-- 	<form id="f_boardAdd" method="post" enctype="multipart/form-data"> -->
	<form id="f_boardAdd" method="get">
	<input type="hidden" name="bm_no" value="<%=bm_no%>">
	<input type="hidden" name="bm_group" value="<%=bm_group%>">
	<input type="hidden" name="bm_pos" value="<%=bm_pos%>">
	<input type="hidden" name="bm_step" value="<%=bm_step%>">
	<!-- <form id="f_boardAdd"> -->
	<table>
		<tr>
			<td width="100px">제목</td>
			<td width="500px">
				<input class="easyui-textbox" data-options="width:'350px'" id="bm_title" name="bm_title" required>
			</td>
		</tr>
		<tr>	
			<td width="100px">작성자</td>
			<td width="500px">
				<input class="easyui-textbox" data-options="width:'150px'" id="bm_writer" name="bm_writer" required>
			</td>
		</tr>
		<tr>			
			<td width="100px">내용</td>
			<td width="500px">
				<input class="easyui-textbox" id="bm_content" name="bm_content" data-options="multiline:'true',width:'400px',height:'90px'" required>
			</td>
		</tr>
		<tr>			
			<td width="100px">비번</td>
			<td width="500px">
				<input class="easyui-textbox" data-options="width:'100px'" id="bm_pw" name="bm_pw" required>
			</td>
		</tr>
	</table>
	</form>
</div>
<!-- 입력 화면 버튼 추가 -->
<div id="tbar_boardAdd" align="right">
	<a href="javascript:addAction()" class="easyui-linkbutton" iconCls="icon-save">저장</a>
	<a href="javascript:$('#dlg_boardAdd').dialog('close')" 
	   class="easyui-linkbutton" iconCls="icon-cancel">닫기</a>
</div>
		<!-- 댓글쓰기  끝  -->	    
</body>
</html>