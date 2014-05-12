<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css" />
<link rel="stylesheet" type="text/css"	href="/resources/weavernest/css/weavernest.css" />
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
</head>
<body style='overflow:hidden;overflow-x:auto;'>
<script>
function setCommitMassage(msg){
		$("#commitMassage").append(msg);
}
$(document).ready(function(){	// 깃 메뉴를 숨김
	setCommitMassage(loadHeadCommitMassage());

	$( "#okButton" ).click(function() {
		commit($('#commitMassage').val(),'true'); 
		parentExcute('showCommitList(eval(loadCommitLogSimpleList()))');  
		windowCancel()
	});
});
</script>
	<div id ='header' data-role='page' id='page1'>
		<div data-theme='a' data-role='header'>
			<h4 class ='title'>커밋 수정하기</h4>
			<div class='ui-btn-right' data-role='controlgroup'
				data-type='horizontal' data-mini='true'>
				<a onclick='windowCancel()' data-role='button' data-iconpos='notext'
					data-icon='delete'></a>
			</div>
		</div>
		<div data-role='content'>
			<div data-role='fieldcontain'>
				<textarea id='commitMassage'
					placeholder='예시) hello.c파일의 printf("hello word!!!")를 추가로 입력했습니다.'></textarea>
			</div>
			<div class='ui-grid-a'>
				<div class='ui-block-a'>
					<a id ='okButton' data-role='button' data-theme='a'
						data-icon='check' data-iconpos='left'> 확인 </a>
				</div>
				<div class='ui-block-b'>
					<a data-role='button' data-theme='a'  onclick='windowCancel()'
						data-icon='delete' data-iconpos='left'> 취소 </a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>