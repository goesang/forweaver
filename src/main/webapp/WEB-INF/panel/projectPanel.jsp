<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<sec:authentication property="principal" var="currentUser" />
<script src="/resources/forweaver/js/forweaver-util.js"></script>

<sec:authorize ifAnyGranted="ROLE_USER, ROLE_ADMIN">
	<style>
.ui-icon-custom-icon {
	background-image: url("/${currentUser.username}/img");
	background-size: 28px;
}
</style>
</sec:authorize>
<div data-tap-toggle="false" data-role="panel" id="projectPanel"
	data-position="right" data-theme="a" data-display="overlay">

	<sec:authorize ifNotGranted="ROLE_USER, ROLE_ADMIN">
		<a rel="external" data-icon="user" data-role="button" data-theme="f"
			href="/login?state=null"> 로그인</a>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER, ROLE_ADMIN">
		<div data-collapsed-icon="custom-icon"
			data-expanded-icon="custom-icon" data-role="collapsible-set"
			data-theme="b" data-content-theme="b">
			<div data-collapsed="false" data-role="collapsible">

				<h3>&nbsp;${currentUser.username}</h3>

				<a rel="external" data-icon="envelope" data-role="button"
					data-theme="b" href="/m/community/tags:$${currentUser.username}">
					메시지함 </a> <a rel="external" data-icon="signout" data-role="button"
					data-theme="b" href="/m/j_spring_security_logout"> 로그아웃 </a>
			</div>
		</div>

	</sec:authorize>


<div data-role="controlgroup">
<a rel="external" data-icon="folder-close" data-role="button" data-theme="b" href="/project/${project.name}">파일 브라우져</a>
<a rel="external" data-icon="info-sign" data-role="button" data-theme="b" href="/project/${project.name}/commitlog">커밋 내역</a> 
<a rel="external" data-icon="comments" data-role="button" data-theme="b" href="/project/${project.name}/community">커뮤니티</a>
<a rel="external" data-icon="group" data-role="button" data-theme="b" href="/project/${project.name}/weaver">참가자 목록</a>
<a rel="external" data-icon="info" data-role="button" data-theme="b" href="/project/${project.name}/info">프로젝트 정보</a>

</div>
<a rel="external" data-icon="chevron-sign-up" data-role="button" data-theme="f" href="/project/">프로젝트 나가기</a>

</div>