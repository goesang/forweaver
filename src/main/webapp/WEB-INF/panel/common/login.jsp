<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="alert">	
<p style="text-align: center; font-size: 12px;">
	<b><i class="fa fa-user"></i> 로그인을 먼저 해주세요!<b>
</p>
<p style="font-size: 11px;">서비스를 이용하시려면 로그인을 하셔야 합니다. 만일 아이디가 없으시다면
	회원가입을 하시거나 페이스북 및 트위터로 인증해주세요!</p>
</div>

<form data-ajax="false" action="j_spring_security_check" method="post"
	id="loginForm">
	<fieldset>
		<input placeholder="아이디를 입력해주세요!" class="text" type="text"
			name="j_username" id="j_username" /> <input
			placeholder="비밀번호를 입력해주세요!" class="text" type="password"
			name="j_password" id="j_password" />
		<button rel="external" data-icon="user" data-theme="f" type="submit">접속하기</button>
		<a rel="external" href="/m/join" data-theme="f" data-role="button"
			data-icon="edit-sign">가입하기</a> <a rel="external"
			href="/m/facebook/call" data-theme="b" data-role="button"
			data-icon="facebook">페이스북으로 로그인</a> <a rel="external"
			href="/m/twitter/call" data-theme="b" data-role="button"
			data-icon="twitter">트위터로 로그인</a>
	</fieldset>
</form>