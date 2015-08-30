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
<div data-tap-toggle="false" data-role="panel" id="mainPanel"
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

				<a rel="external" data-icon="home" data-role="button"
					data-theme="b" href="/">
					마이페이지 </a> 
				<a rel="external" data-icon="envelope" data-role="button"
					data-theme="b" href="/community/tags:$${currentUser.username}">
					메시지함 </a> 
				<a rel="external" data-icon="signout" data-role="button"
					data-theme="b" href="/m/j_spring_security_logout"> 로그아웃 </a>
			</div>
		</div>

	</sec:authorize>


		<div data-role="controlgroup">
			<a rel="external" data-icon="comments" data-role="button"
				data-theme="b" href="/community/"> 커뮤니티 </a> <a
				rel="external" data-icon="bookmark" data-role="button"
				data-theme="b" href="/project/"> 프로젝트 </a> <a rel="external"
				data-icon="rocket" data-role="button" data-theme="b"
				href="/code/"> 코드 </a> <a rel="external" data-icon="twitter"
				data-role="button" data-theme="b" href="/weaver/"> 위버 </a>

		</div>
		
		

	<c:if test="${tagNames !=null}">
	<script>
	$(document).ready(function() {
		$('#searchInput').keypress(function (e) {
		  if (e.which == 13) {
			  movePage(getTagList(document.location.href),$('#searchInput').val());
		  }
		});
	});
	</script>
	<input id="searchInput" class="etc" placeholder="검색어를 입력해주세요!"
		type="search">
	</c:if>
</div>