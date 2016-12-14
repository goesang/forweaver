<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<title>ForWeaver : 모두를 위한 소셜 코딩!</title>
</head>

<body>
<script>
${script}
</script>

	<div data-role="page">
<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
			</div>
			<h1><b>Forweaver.com</b></h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-iconpos="notext" data-role="button" data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content" align="center">
		<h5>학생들을 위한 소셜코딩!</h5>
		
			<embed src="/resources/svg/noun_project_8428.svg" width="150"
								height="150" type="image/svg+xml" />
			
			<form data-ajax="false" action="j_spring_security_check"
				method="post" id="loginForm">
				<fieldset>
					<input placeholder="아이디를 입력해주세요!" class="text" type="text"
						name="j_username" id="j_username" /> <input
						placeholder="비밀번호를 입력해주세요!" class="text" type="password"
						name="j_password" id="j_password" />
					<button rel="external"  data-icon="user" data-theme="a" type="submit">접속하기</button>
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>