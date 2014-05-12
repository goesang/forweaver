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
	<div data-role="page">
	
		<div data-theme="a" data-role="header">
			<h4>위버네스트</h4>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a onclick="windowMinimize()" data-role="button" data-iconpos="notext"
					data-icon="minus"></a> 
				<a onclick="windowExit()"
					data-role="button" data-iconpos="notext" data-icon="delete"></a>
			</div>
		</div>

		<div data-role="content" align="center">
			<embed src="/resources/svg/noun_project_8428.svg" width="170"
				height="170" type="image/svg+xml" />
			<form data-ajax="false" action="j_spring_security_check"
				method="post" id="loginForm">
				<fieldset>
					<input placeholder="아이디를 입력해주세요!" class="text" type="text" name="j_username" id="j_username" /> 
					<input placeholder="비밀번호를 입력해주세요!" class="text" type="password" name="j_password" id="j_password" />
					<button data-icon="check" data-theme="a"
						onclick="rememberWeaver($('#j_username').val(),$('#j_password').val())"
						type="submit">접속하기</button>
					<button data-icon="delete" data-theme="a" data-transition="slide"
						onclick="windowExit()"
						type="submit">종료하기</button>	
				</fieldset>
			</form>
		</div>
		</div>
</body>
</html>