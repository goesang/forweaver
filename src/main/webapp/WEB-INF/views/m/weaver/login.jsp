<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>

<body>
<script>
$(document).ready(function() {
	$("#tagsdisplay").tagsInput();
});
</script>
	<div data-role="page">
<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
			</div>
			<h1><b>forWeaver</b></h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-iconpos="notext" data-role="button" data-icon="coffee"></a>
				<a href="#mainPanel" data-iconpos="notext" data-role="button" data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content" align="center">
			
			<h5>학생들을 위한 소셜코딩!</h5>
			<form data-ajax="false" action="j_spring_security_check"
				method="post" id="loginForm">
				<fieldset>
					<input placeholder="아이디를 입력해주세요!" class="text" type="text"
						name="j_username" id="j_username" /> <input
						placeholder="비밀번호를 입력해주세요!" class="text" type="password"
						name="j_password" id="j_password" />
					<button rel="external"  data-icon="user" data-theme="a" type="submit">접속하기</button>
					<a rel="external" href="/m/join" data-theme="a" data-role="button" data-icon="edit-sign">가입하기</a>
					<a rel="external" href="/m/facebook/call" data-theme="b" data-role="button" data-icon="facebook">페이스북으로 로그인</a>
					<a rel="external" href="/m/twitter/call" data-theme="b" data-role="button" data-icon="twitter">트위터로 로그인</a>
				</fieldset>
			</form>
		</div>
		
		<div data-theme="b" data-role="footer" data-position="fixed">
           <div class="ui-grid-a">
            <div class="ui-block-a" style=" padding-left:20px; width:85%;">
                    <input style="height:20px;" id="tagsdisplay" class="etc tagsinput"/>
            </div>
            <div class="ui-block-b" style="padding-top:5px; padding-right:5px; padding-left:5px; width:10%;">
                <a id ="chat-ok" data-role="button" data-icon="coffee" data-iconpos="notext">
                </a>
            </div>
        </div>
    </div>
	</div>
</body>
</html>