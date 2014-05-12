<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
</head>
<body style="overflow:hidden;overflow-x:auto;">
<script>
function setTitle(msg){
	$(document).ready(function(){
		$("#title").append(msg);
	});
}

function setContent(msg){
	$(document).ready(function(){
		$("#content").append(msg);
	});
}

setTitle(loadAlertTitle());
setContent(loadAlertContent());

</script>
	<div id ="header" data-role="page" id="page1">
		<div data-theme="a" data-role="header">
			<h1 id ="title"></h1>
				<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a onclick="windowCancel()" data-role="button" data-iconpos="notext"
					data-icon="delete"></a>
			</div>
		</div>
		<div data-role="content">
			<div data-role="fieldcontain">
				<h3 id="content"></h3>
			</div>
				<a data-role='button' data-theme='a' onclick='windowCancel()'
					data-icon="check" data-iconpos="left">확인</a>
		</div>
	</div>
</body>
</html>