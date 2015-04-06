<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>Forweaver : 포위버란 무엇인가?</title>
</head>

<body>

	<div  class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="/intro/forweaver">ForWeaver란?</a></li>
					<li><a href="/intro/membertool">기본 기능 사용하기!</a></li>
					<li><a href="/intro/community">커뮤니티 이용 방법!</a></li>
					<li><a href="/intro/project">프로젝트 진행 방법!</a></li>
					<li><a href="/intro/code">코드 공유 방법!</a></li>
				</ul>
		
			<div class="row">
				<div class="span6"><br/><br/>
					<center><h1><i class="fa fa-twitter"></i> ForWeaver!</h1></center><br/>
					<big><strong>ForWeaver</strong>는 <a href="https://github.com/">GitHub</a>와 같이 소프트웨어 개발 및 저장소와 회원 관리 기능을 제공하는 협업 개발 플랫폼입니다.<br/><br/>
					각종 문서와 정보를 간편하게 공유할 수 있는 게시판, 소스코드의 변경내역을 편리하게 관리할 수 있는 형상관리 툴을 비롯하여, 팀 개발을 위한 다양한 기능을 포함하고 있습니다.</big><br><br>
				</div>
					<embed class="span6" src="/resources/forweaver/img/mainpage.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
			<center><h1>GIT이란 무엇인가?</h1></center><br/>
			<div class="span6"> 출처 - <a href="http://opentutorials.org/course/1492/8035">생활코딩</a> <iframe width="460" height="260" src="https://www.youtube.com/embed/XUEuYq64HKI" frameborder="0" allowfullscreen></iframe></div>
				<div class="span6">
					<br>
					<big>
					ForWeaver에서는 <B>GIT</B> 저장소를 제공합니다.<br><br>
					GIT은 리누스 토발즈가 만든 버젼 관리 프로그램으로<br>
					분산형 시스템을 제공하여 사용자들이 손쉽게 이용 가능합니다.<br>
					Forweaver를 제대로 사용하기 위해서는 GIT을 공부해야지만<br>
					기본적으로 GIT이 없이도 프로젝트를 진행할 수 있습니다.<br>
					<br><br><br></big>
				</div>
				
			</div>
			
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
			<center><h1>Weaver란?</h1></center><br/>
				<div class="span6">
					<center>
					<br>
					<big>
					아이디어 구상 중 TV에서 다큐멘터리를 보다가<br>
					<a href="http://en.wikipedia.org/wiki/Sociable_Weaver">Sociable Weaver</a>라는 새를 알게 됐습니다.<br>
					소셔블 위버라는 새는 조그만 새지만 집단생활을 하며, <br>
					다같이 잔가지를 주워와 몇 톤에 이르는 둥지를 짓습니다.<br>
					이 조그맣고 경이로운 새들을 본따<br>
					프로젝트 컨셉을 Weaver로 정했습니다!<br>
					<br><br><br></big>
					</center>
				</div>
				<img class="span3" src="http://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Philetairus_socius_%28Etosha%2C_2013%29.jpg/800px-Philetairus_socius_%28Etosha%2C_2013%29.jpg"/>
				<img class="span3" src="http://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Webervogelnst_Auoblodge.JPG/800px-Webervogelnst_Auoblodge.JPG"/>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h2><i class="fa fa-info"></i>&nbsp;&nbsp;주요 기능 소개</h2></center><br/><br/>
			<div class="row">
				<div class="span6"><br/><br/>
					<center><h3><i class="fa fa-tag"></i> 태그 기반 사이트!</h3></center><br/>
					<big><strong>Tag</strong>를 통해 프로젝트와 게시글이 통합 관리되는 사이트입니다.<br><br>
					2개 이상의 태그로도 항목 분류가 가능하며,<br>
					태그와 검색어를 조합하여 검색하는 기능이 제공됩니다. <br>
				</div>
				<embed class="span6" src="/resources/forweaver/img/tagpage.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span6" src="/resources/forweaver/img/projectpage.png"/>
				<div class="span6"><br/><br/>
					<center><h3><i class="fa fa-bookmark"></i> 프로젝트 관리!</h3></center><br>
					<strong>Project</strong>는 안전하게 서버에 보관되며,<br>
					<br>
					프로젝트의 전체 소스 내용, 진행 사항 알림 및 <br>
					해당 프로젝트 위버들 간의 채팅,<br>
					위버들의 대화를 위한 커뮤니티 기능 등으로 구성됩니다.
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span6"><br/><br/>
					<center><h3><i class="fa fa-rocket"></i> 코드 관리!</h3></center><br>
					프로젝트 외에 개별 <strong>Code</strong>도 공유할 수 있습니다.<br>
					<br>
					단일 코드 업로드 뿐만 아니라 <br>
					여러 개의 코드도 압축하여 한 번에 올릴 수 있습니다.<br>
					코드 뷰어 기능을 통해 사용자들의 다양한 리뷰가 가능합니다.
				</div>
				<embed class="span6" src="/resources/forweaver/img/codepage.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span6" src="/resources/forweaver/img/weaverpage.png"/>
				<div class="span6"><br/><br/>
					<center><h3><i class="fa fa-rocket"></i> 회원 관리!</h3></center><br>
					<strong>Weaver</strong> 페이지로 사용자를 관리합니다.<br>
					<br>
					가입한 회원의 전체 목록을 확인할 수 있으며<br>해당 회원이 올린 글, 답변, 업로드한 프로젝트 및 <br>코드 갯수 목록을 보실 수 있습니다.
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><a href="/" 
					class="btn btn-primary">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ForWeaver 시작하기!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
			</center>
			<br>
			<!-- 
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			 -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>