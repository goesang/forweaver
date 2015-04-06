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
					<li><a href="/intro/membertool">기본 기능 사용하기!</a></li>
					<li><a href="/intro/community">커뮤니티 이용 방법!</a></li>
					<li><a href="/intro/project">프로젝트 진행 방법!</a></li>
					<li class="active"><a href="/intro/code">코드 공유 방법!</a></li>
				</ul>
		
			<center><h2><i class="fa fa-rocket"></i>&nbsp;Code</h2></center>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>코드 페이지!</h3></center><br><br>
			<div class="row">
				<div class="span5" align="right">
					<br><br>
					<big><strong>ForWeaver</strong>는 프로젝트 뿐만 아니라<br>
							코드 파일만의 공유도 지원하고 있습니다.<br><br>
							단일 및 다중 소스 코드의 <br>
							뷰어 및 다운로드 기능을 제공합니다.<br><br>
					</big>
				</div>			
				<embed class="span7" src="/resources/forweaver/img/code01.png"/>	
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3><i class="fa fa-code"></i>&nbsp;코드 게시 방법!</h3></center><br><br>
			<div class="row">
			<embed class="span7" src="/resources/forweaver/img/code02.png"/>	
				<div class="span5">
					<br><br><br>
					<big><strong>코드 게시글</strong>을 작성하려면.<br><br>
							1. 태그를 작성하고<br>
							&nbsp;<span style="font-size:13px;">
							<i class="icon-pencil"></i></span> 코드 작성하기 버튼을 클릭하여<br> 
							2. 코드의 제목, 설명을 작성 후<br>
							3. 압축 파일 혹은 코드를 첨부하고 <i class="fa fa-check"></i> 게시하세요.<br><br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
			<embed class="span7" src="/resources/forweaver/img/code03.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big><strong>코드가 서식 적용되어 게시글로 작성됩니다.</strong><br><br>
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
			<center><a href="/code/" 
					class="btn btn-primary">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;코드 페이지로&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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