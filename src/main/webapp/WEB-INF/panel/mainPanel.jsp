<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<sec:authentication property="principal" var="currentUser"/>
<script src="/resources/forweaver/js/forweaver-util.js"></script>


<div data-tap-toggle="false" data-role="panel" id="mainPanel" data-position="right" data-theme="a" data-display="overlay">

			<sec:authorize ifNotGranted="ROLE_USER">
				<a rel="external" data-icon="user" data-role="button" data-theme="f" href="/m/login"> 로그인</a>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_USER">
        <div data-collapsed-icon="custom-icon" data-expanded-icon="custom-icon"  
        data-role="collapsible-set" data-theme="f" data-content-theme="c">
            <div data-role="collapsible">
            <h3>&nbsp;&nbsp;${currentUser.username}</h3>
                                
                <a rel="external" data-icon="cog" data-role="button" data-theme="b" href="/manage">
                    관리화면
                </a>
                <a rel="external" data-icon="envelope" data-role="button" data-theme="b" href="/community/tags:$${currentUser.username}">
                    메시지함
                </a>
                <a rel="external" data-icon="signout" data-role="button" data-theme="b" href="/j_spring_security_logout">
                    로그아웃
                </a>
            </div>
        </div>

					</sec:authorize>

	<div data-role="collapsible-set" data-theme="b" data-content-theme="c">

					
		<div data-role="collapsible">
			<h3>소개</h3>
			<a rel="external" data-icon="question-sign" data-role="button" data-theme="b" href="/forweaver">forWeaver</a>
			<a rel="external" data-icon="signal" data-role="button" data-theme="b" href="/weavernest">WeaverNest </a> 
		</div>

		<div data-role="collapsible" data-collapsed="false">
			<h3>바로가기</h3>
			<a rel="external" data-icon="comments" data-role="button" data-theme="b"
				href="/community/sort:age-desc/page:1"> 커뮤니티 </a> 
			<a rel="external" data-icon="coffee" data-role="button" data-theme="b"
				href="/chat/page:1"> 채팅 </a> 
			<a rel="external" data-icon="bookmark" data-role="button"
				data-theme="b" href="/project/page:1"> 프로젝트 </a> 
			<a rel="external" data-icon="book"
				data-role="button" data-theme="b" href="/lecture/page:1"> 강의 </a>
		</div>

	</div>

	<input id="tagsinput" class = "etc" placeholder="태그를 입력해주세요!">

	<!--  <input id="tagsdisplay" class="etc tagsinput"/>-->
	
	<input id="searchInput" class = "etc" placeholder="검색어를 입력해주세요!" type="search">

</div>