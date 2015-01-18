<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 포위버란 무엇인가?</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<div class="container">
		<%@ include file="/WEB-INF/views/common/nav.jsp"%>
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="/forweaver">포위버란 무엇인가?</a></li>
				</ul>
		<!--
			<div class='page-header page-header-none'>
			
			</div>
			-->
			<div class="row">
				<div class="col-md-4 col-md-offset-1">
					<center><h3>ForWeaver!</h3></center>
					<strong style="font-size:22px;">ForWeaver</strong>는 <a href="https://about.gitlab.com/">GitLab</a>과 같이 소프트웨어 개발 및 저장소와 회원 관리 기능을 제공하는 협업 개발 플랫폼입니다.<br><br>
					'숙제 저장소'나 '예제 저장소'와 같은 특별한 저장소 관리 서비스, 각종 문서와 정보를 간편하게 공유할 수 있는 게시판, 소스코드의 변경내역을 편리하게 관리할 수 있는 형상관리 툴을 비롯하여, 팀 개발을 위한 다양한 기능을 포함하고 있습니다.<br><br>
				</div>
				<div class="col-md-5 col-md-offset-1"><br>
					<embed src="/resources/img/mainpage.png" style="max-width: 100%;"/>
				</div>
			</div>
			<div class="row" style = "background-color:#F3F3F3">
			<!--  <hr/>-->
			<center><h3>ForWeaver란?</h3></center>
				<div class="col-md-4 col-md-offset-1">
					<center>
					<br>
					아이디어 구상 중 TV에서 다큐멘터리를 보다가<br>
					<a href="http://en.wikipedia.org/wiki/Sociable_Weaver">Sociable Weaver</a>라는 새를 알게 됐습니다.<br>
					소셔블 위버라는 새는 조그만 새지만 집단생활을 하며, <br>
					다같이 잔가지를 주워와 몇 톤에 이르는 둥지를 짓습니다.<br>
					이 조그맣고 경이로운 새들을 본따<br>
					프로젝트 컨셉을 Weaver로 정하였습니다!<br>
					<br><br><br>
					</center>
				</div>
					<div class="col-xs-6 col-md-3">
						<img src="http://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Philetairus_socius_%28Etosha%2C_2013%29.jpg/800px-Philetairus_socius_%28Etosha%2C_2013%29.jpg" style="max-width: 100%;" />
					</div>
					<div class="col-xs-6 col-md-3">
						<img src="http://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Webervogelnst_Auoblodge.JPG/800px-Webervogelnst_Auoblodge.JPG" style="max-width: 89%;"/>
					</div>
			</div>
			<center><h3>기본 기능은?</h3></center>
			<div class="row">
				<div class="col-md-4 col-md-offset-1">
					<center><h4>태그 페이지!</h4></center>
					<strong style="font-size:22px;">Tag</strong>를 통해 <br>
					프로젝트, 게시글 등을 검색 및 관리할 수 있으며,<br>
					2개 이상의 태그를 이용한 분리 검색이 가능합니다.<br><br>
				</div>
				<div class="col-md-4 col-md-offset-1"><br>
					<embed src="/resources/img/tagpage.png" style="max-width: 100%;" />
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-4 col-md-offset-1"><br>
					<embed src="/resources/img/projectpage.png" style="max-width: 100%;" />
				</div>
				<div class="col-md-4 col-md-offset-1">
					<center><h4>프로젝트 페이지!</h4></center>
					<strong style="font-size:22px;">Project</strong>는 <br>
					안전하게 서버에 보관되며,<br>
					해당 프로젝트 사용자들 간의 채팅,<br>
					프로젝트의 진행 내역,<br>
					코드 주고받기 등 다양한 기능을 제공합니다.<br><br>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-4 col-md-offset-1">
					<center><h4>위버 관리 페이지!</h4></center>
					<strong style="font-size:22px;">Weaver</strong>&nbsp; 페이지를 통해 다른 사용자를 찾아보세요.<br>
					관심 있는 주제를 확인할 수 있고,<br>
					사용자의 최근 정보를 보는 것이 가능합니다.<br><br>
				</div>
				<div class="col-md-4 col-md-offset-1"><br>
					<embed src="/resources/img/weaverpage.png" style="max-width: 100%;" />
				</div>
			</div>
			<!-- 
			<div class="row">
			<center><h3>사용 설명서</h3></center>			
				<div class="col-md-6 col-md-offset-3">
				<strong style="font-size:22px;">저장소 접근 방법</strong><br>
					git에서 저장소에 접근하는 방법은 다음과 같습니다.<br><br>
					<pre>git clone http://forweaver.com/아이디/저장소.git</pre>
				</div>
			</div> -->
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>

</body>
</html>