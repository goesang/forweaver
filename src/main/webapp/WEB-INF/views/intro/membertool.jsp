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
					<li><a href="/intro/forweaver">ForWeaver란?</a></li>
					<li class="active"><a href="/intro/membertool">기본 기능 사용하기!</a></li>
					<li><a href="/intro/community">커뮤니티 이용 방법!</a></li>
					<li><a href="/intro/project">프로젝트 진행 방법!</a></li>
					<li><a href="/intro/code">코드 공유 방법!</a></li>
				</ul>
		
			<center><h2><i class="fa fa-home"></i>&nbsp;Member Tools</h2></center>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>마이페이지!</h3></center><br><br>
			<div class="row">
				<div class="span5" align="right">
					<br><br><br><br>
					<big><strong>ForWeaver</strong>의 마이페이지는<br>
							우측 상단 <i class="fa fa-home"></i>개인화면 버튼으로 접속 가능합니다.<br><br>
							내가 작성한 게시글 및 나와 관련된 글을<br>
							항목별로 확인할 수 있습니다.<br>
					</big>
				</div>				
				<embed class="span7" src="/resources/forweaver/img/membertool01.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span5" align="right">
					<br><br><br><br>
					<big><strong>마이페이지</strong>의 오른쪽 메뉴를 누르면<br>
							각 페이지별로 나와 관련된 게시글을<br>
							확인할 수 있습니다.<br>
					</big>
				</div>				
				<embed class="span7" src="/resources/forweaver/img/membertool02.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3><i class="fa fa-gear"></i>&nbsp;회원 정보 수정!</h3></center><br><br>
			<div class="row">
			<embed class="span7" src="/resources/forweaver/img/membertool03.png"/>	
				<div class="span5">
					<br><br><br>
					<big><strong>정보수정</strong> 페이지에서는<br><br>
							회원가입 시 설정했던 관심사 태그,<br>
							비밀번호 및 프로필, 학번, 자기 소개<br> 
							모두 수정할 수 있습니다.</p>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3><i class="fa fa-envelope"></i>&nbsp;메세지 주고받기!</h3></center><br><br>
			<div class="row">
			<embed class="span7" src="/resources/forweaver/img/membertool04.png"/>	
				<div class="span5">
					<br><br><br><br><br>
					<big><strong>메세지함</strong>에서는<br><br>
							받고 보낸 메세지를 모두 확인할 수 있습니다.<br><br>
							우측 상단의 항목을 클릭하거나<br>
							<strong>$ + 본인 아이디</strong>를 태그란에 입력하면<br>
							메세지함으로 접속합니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
			<embed class="span7" src="/resources/forweaver/img/membertool05.png"/>	
				<div class="span5">
					<br><br><br><br>
					<big>회원에게 <strong>메세지</strong>를 보내려면<br><br>
							1. <strong>$ + 대상 아이디</strong>를 입력하고<br>
							2. 보낼 메세지 내용을 입력 후<br>
							3. <i class="fa fa-check"></i> 전송 버튼을 클릭하세요.<br>
					</big>
				</div>
			</div>
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