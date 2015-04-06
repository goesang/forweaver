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
					<li class="active"><a href="/intro/community">커뮤니티 이용 방법!</a></li>
					<li><a href="/intro/project">프로젝트 진행 방법!</a></li>
					<li><a href="/intro/code">코드 공유 방법!</a></li>
				</ul>
		
			<center><h2><i class="fa fa-comments"></i>&nbsp;Community</h2></center>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3><i class="fa fa-search"></i> 게시글 검색!</h3></center><br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/community01.png"/>
				<div class="span5">
					<br><br><br><br>
					<big><strong>ForWeaver</strong>는 태그 통합형 서비스이기 때문에<br>
							태그를 반드시 기입해야 글쓰기가 가능합니다.<br><br>
							이를 통해 태그를 이용한 전체 글 분류 및<br> 검색이 이루어집니다.
					</big>
				</div>				
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/community01-1.png"/>
				<div class="span5"><br><br>
						<big>
							<strong>따라서</strong><br><br>
							1. 검색어를 통한 검색<br>
							2. 태그로 검색<br>
							3. 검색어 + 태그 조합 검색<br><br>
							등 다양한 방법으로 글을 검색할 수 있습니다.
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
			<center><h3><i class="fa fa-pencil"></i> 글 작성 방법!</h3></center><br><br>
			<div class="row">
				<div class="span4" align="right">
					<br><br><br>
					<big><strong>단문</strong>은 보시는 바와 같이&nbsp;&nbsp;<br><br>
							1. 태그를 달고 나서&nbsp;&nbsp;<br>
							2. 단문(글제목)을 작성후&nbsp;&nbsp;<br>
							3. <i class="fa fa-check"></i> 글작성 버튼을 클릭하세요.&nbsp;&nbsp;
					</big>
				</div>		
				<embed class="span8" src="/resources/forweaver/img/community02.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span4" align="right"><br><br><br><br><br><br>
						<big>
							<strong>단문이 작성된 화면입니다.</strong>&nbsp;&nbsp;<br><br>
						</big>
				</div>
				<embed class="span8" src="/resources/forweaver/img/community03.png"/>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span8" src="/resources/forweaver/img/community04-1.png"/>
				<div class="span4">
					<br><br><br><br>
					<big><strong>&nbsp;장문</strong>도 보시는 바와 같이<br><br>
							&nbsp;1. 태그를 달고 나서<br>
							&nbsp;&nbsp;<span style="font-size:13px;">
								<i class="icon-pencil"></i></span> 연필 버튼을 클릭하여 내용 탭을 열고<br><br>
							&nbsp;2. 글 제목, 내용, 첨부파일 등을 작성후<br>
							&nbsp;3. <i class="fa fa-check"></i> 글작성 버튼을 클릭하세요.<br><br>
							
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span8" src="/resources/forweaver/img/community04-2.png"/>
				<div class="span4"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
						<big>
							<strong>장문 게시글이 작성된 화면입니다.</strong>&nbsp;&nbsp;<br><br>
						</big>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br>
			<div class="row">
			<center>
				<embed style="width:700px;" src="/resources/forweaver/img/community05.png"/><br><br>
				<big><strong>커뮤니티 화면에서 작성한 글을 볼 수 있습니다.</strong></big><br>
			</center>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><a href="/community/" 
					class="btn btn-primary">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;커뮤니티 페이지로&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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