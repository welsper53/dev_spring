<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%
// jsp에서 자바코드(스크립틀릿)와 html코드의 작성 위치는 문제가 되지 않는다.
// 왜냐하면 어차피 jsp는 서버에서 실행되고 그 결과가 text로 출력되는 것이므로 
// html과 처리 시점이 완전 다르니까...
	List<Map<String,Object>> boardList = 
			(List<Map<String,Object>>)request.getAttribute("bList");
	int size = 0;
	if(boardList!=null){
		size = boardList.size();
	}		
	out.print(size);
%>    
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="UTF-8"> 이것때문에 한글깨짐.-->
<title>MVC기반의 계층형 게시판 구현하기[WEB-INF]</title>
<%@ include file="../common/easyUI_common.jsp" %>
<!-- Include the Quill library -->
<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
<!-- Initialize Quill editor -->
<script>

</script>
<script type="text/javascript">
	let g_no=0;//그리드에서 선택이 바뀔때 마다 변경된 값이 저장됨.
	let tb_value;//사용자가 입력한 문자열 담기
	let isOk = false;
	function dlgIns_save(){
		//폼 전송 처리함.
		$("#f_boardIns").submit();
	}
	function dlgIns_close(){
		$("#dlg_boardIns").dialog('close');
	}
	function getBoardList(){
		//alert("getBoardList호출");
		//사용자가 선택한 콤보박스에 value가 담김 - b_title, or b_content or b_writer
		cb_value = user_combo;
		tb_value = $("#tb_search").val();//사용자가 입력한 조건 검색 문자열
		console.log("콤보박스 값: "+ cb_value+", 사용자가 입력한 키워드: "+tb_value);
		location.href = "./boardList.st3?cb_search="+cb_value+"&tb_search="+tb_value+"&bm_reg="+v_date;
	}	
	function boardDetail(bm_no){
		console.log(bm_no);
		location.href="./boardDetail.st3?bm_no="+bm_no;
	}
    function fileDown(fname){
		location.href="downLoad.jsp?bs_file="+fname;
    }	
</script>
</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){//DOM구성이 완료된 시점-자바스크립트로 태그접근,설정변경,이미지
	    /*===================== CRUD버튼 시작 ====================*/	    
		//조회버튼 클릭했을 때
	    $('#crudBtnSel').bind('click', function(){
	    	getBoardList();
	    });
		$('#crudBtnIns').bind('click', function(){
	        //alert('입력 버튼');
	        $("#dlg_boardIns").dialog('open');
	    });			
	    /*===================== CRUD버튼 끝 ====================*/	    
		const container = document.querySelector("#editor");
	    //const editor = new Quill(container);
		const quill = new Quill(container, {
		    modules: {
		        toolbar: '#toolbar'
		    },
		    theme: 'snow',
		    placeholder: ':)',
		})
	});///////////////// end of ready
</script>
<center>
 
<!-- 글입력 화면 추가 시작 -->
    <div id="dlg_boardIns" footer="#tb_boardIns"  title="글쓰기" style="width:600px;height:400px;padding:10px">
        <!-- <form id="f_boardIns" method="post" enctype="multipart/form-data" action="./boardInsert.pj"> -->
        <form id="f_boardIns" method="get" action="./boardInsert.st3">
        <!-- hidden속성은 화면에 보이지 않음. 개발자가 필요로 하는 값
        등록 부분과 수정 부분이 동시에 발생할 수 도 있다  - 트랜잭션 처리가 필요함
        트랜잭션 처리가 필요한 경우의 메소드 설계
         -->
	    <input type="hidden" id="bm_no" name="bm_no" value="0">
	    <input type="hidden" id="bm_group" name="bm_group" value="0">
	    <input type="hidden" id="bm_pos" name="bm_pos" value="0">
	    <input type="hidden" id="bm_step" name="bm_step" value="0">
        	<table>
        		<tr>
        			<td width="100px">제&nbsp;&nbsp;&nbsp;목</td>
        			<td width="500px"><input id="bm_title" name="bm_title" class="easyui-textbox" data-options="width:'250px'" required></td>
        		</tr>
        		<tr>
        			<td width="100px">작&nbsp;성&nbsp;자</td>
        			<td width="500px"><input id="bm_writer" name="bm_writer" class="easyui-textbox" data-options="width:'150px'" required></td>
        		</tr>
        		<tr>
        			<td width="100px">내&nbsp;&nbsp;&nbsp;용</td>
        			<td width="500px"><input id="bm_content" name="bm_content" class="easyui-textbox" data-options="multiline:'true',width:'350px', height:'90px'" required></td>
        		</tr>
        		<tr>
        			<td width="100px">내&nbsp;&nbsp;&nbsp;용2</td>
        			<td width="500px"><div id="editor"></div></td>
        		</tr>
        		<tr>
        			<td width="100px">비&nbsp;&nbsp;&nbsp;번</td>
        			<td width="500px"><input id="bm_pw" name="bm_pw" class="easyui-textbox" data-options="width:'100px'" required></td>
        		</tr>
        		<tr>
        			<td width="100px">첨부파일</td>
        			<td width="500px"><input id="bs_file" name="bs_file" class="easyui-filebox" data-options="width:'350px'"></td>
        		</tr>
        	</table>
        </form>
    </div>
    <!-- 다이얼로그 화면 버튼 추가 시작 -->
	<div id="tb_boardIns">
	<a href="javascript:dlgIns_save()" class="easyui-linkbutton">저장</a>
	<a href="javascript:dlgIns_close()" class="easyui-linkbutton">닫기</a>
	</div>    
    <!-- 다이얼로그 화면 버튼 추가  끝   -->
<!-- 글입력 화면 추가  끝   -->
</center>
</body>
</html>