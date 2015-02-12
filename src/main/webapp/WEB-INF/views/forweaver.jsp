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
					<strong class="strong-size">ForWeaver</strong>는 <a href="https://about.gitlab.com/">GitLab</a>과 같이 소프트웨어 개발 및 저장소와 회원 관리 기능을 제공하는 협업 개발 플랫폼입니다.<br><br>
					'숙제 저장소'나 '예제 저장소'와 같은 특별한 저장소 관리 서비스, 각종 문서와 정보를 간편하게 공유할 수 있는 게시판, 소스코드의 변경내역을 편리하게 관리할 수 있는 형상관리 툴을 비롯하여, 팀 개발을 위한 다양한 기능을 포함하고 있습니다.<br><br>
				</div>
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/mainpage.png"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br> <!--  style="background-color:#F3F3F3" -->
			<div class="row">
			<center><h3>Weaver란?</h3></center>
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
						<img class="img" src="http://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Philetairus_socius_%28Etosha%2C_2013%29.jpg/800px-Philetairus_socius_%28Etosha%2C_2013%29.jpg" style = "max-width : 100%;"/>
					</div>
					<div class="col-xs-6 col-md-3">
						<img class="img" src="http://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Webervogelnst_Auoblodge.JPG/800px-Webervogelnst_Auoblodge.JPG" style="max-width: 89%;"/>
					</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3><i class="fa fa-git"></i>&nbsp;저장소 복제 방법</h3></center>
			<div class="row">			
				<div class="col-md-6 col-md-offset-3">
				<strong class="strong-size">저장소 접근</strong><br>
					git에서 ForWeaver의 저장소에 접근하는 방법은 다음과 같습니다.<br><br>
					<pre>git clone http://forweaver.com/아이디/저장소.git</pre>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h4><i class="fa fa-tag"></i>&nbsp;TAG</h4></center>
			<div class="row">
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>태그 기반 사이트!</h4></center>
					<p><strong class="strong-size">Tag</strong>는 <br>
					위키 혹은 이슈트래커의 쉬운 대체 수단입니다.</p>
					<p><br>프로젝트, 게시글을 검색 및 관리할 수 있으며,<br>
					2개 이상의 태그를 이용한 조합 검색 및,<br>
					위버들에게 프로젝트 메시지, 쪽지 기능을<br>직관성 있게 제공합니다.</p>
				</div>
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/tagpage.png"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h4><i class="fa fa-bookmark"></i>&nbsp;PROJECT</h4></center>
			<div class="row">
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/projectpage.png"/>
				</div>
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>디렉토리 및 채팅!</h4></center>
					<p><strong class="strong-size">Project</strong>는 <br>
					안전하게 서버에 보관되며,</p>
					<p><br>해당 프로젝트 위버들 간의 채팅,<br>
					프로젝트의 전체 소스 내용, 진행 사항 알림 및 <br>
					위버들의 대화를 위한 커뮤니티 기능까지!<br>
					다양한 기능으로 구성됩니다.</p>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>압축파일 관리 툴!</h4></center>
					<p><strong class="strong-size">Zip</strong>파일 Up&Download는 <br>
					Weaver만의 강력한 프로젝트 관리 기능입니다.</p>
					<p><br>압축 프로젝트를 업로드 시<br>자체 기술로 브랜치에 Commit되며,<br>
					프로젝트 전체 다운로드 또한 <br> 압축파일로 내려받기가 가능합니다.</p>
				</div>
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/compresspage.png"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/chartpage.png"/>
				</div>
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>진행사항 시각화!</h4></center>
					<p><strong class="strong-size">Chart</strong>는 위버들의 발자취를 엿볼 수 있는<br>
					 아주 효과적인 툴입니다.</p>
					<p><br>Line Chart, HeatMap은 주일 간의 작업량을, <br>
					Punch Card는 주일 상세 시간대의 작업량을! <br>
					차트를 통해 팀 위버의 진행사항을 지켜보세요.</p>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>체리픽 페이지!</h4></center>
					<p><strong class="strong-size">Cherry Pick</strong>이란 <br>
					Weaver만의 코드 주고받기 기능을 지칭합니다.</p>
					<p><br>맛있는 체리를 바구니에서 골라 먹듯이<br>
					Commit 단위의 코드 공유는 불필요한 충돌을 방지할 것입니다!</p>
				</div>
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/cherrypickpage.png"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h4><i class="fa fa-university"></i>&nbsp;REPOSITORY</h4></center>
			<div class="row">
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/addrepopage.png"/>
				</div>
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>특별한 숙제 공간!</h4></center>
					<p><strong class="strong-size">Repository</strong>는 숙제 및 강의<br>
					 ForWeaver만의 두 가지 항목이 제공됩니다.</p>
					<p><br>강사가 저장소를 생성 시 기한 지정이 가능하며,<br>
					마감이 지날 시 숙제 제출은 불가해집니다.<br></p>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-4 col-md-offset-1"><br>
					<center><h4>강사와 학생의 관계 부여!</h4></center>
					<p><strong class="strong-size">Branch</strong>의 개별성은 <br>
					Git에 없던 특정 권한을 부여하는 것을 <br>가능케 했습니다.</p>
					<p><br>저장소 생성 시 가입자들의 브랜치가 자동 생성, <br>
					강사는 모두를 관리할 수 있으며<br>
					학생은 본인의 브랜치 외엔 접근할 수 없습니다!<br></p>
					<p><br>강사와 학생간의 Commit은 <br>그들의 원활한 소통을 가능케 할 것입니다.</p>
				</div>
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/repomainpage.png"/>
				</div>
			</div>
			<br><!--  
			<div class="row">
				<div class="col-md-10 col-md-offset-1">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h4><i class="fa fa-twitter"></i>&nbsp;WEAVER</h4></center>
			<div class="row">
				<div class="col-md-5 col-md-offset-1"><br>
					<embed class="img" src="/resources/img/weaverpage.png"/>
				</div>
				<div class="col-md-4 col-md-offset-1">
					<center><h4>위버 관리 페이지!</h4></center>
					<p><strong class="strong-size">Weaver</strong>&nbsp; 페이지를 통해<br> 다른 위버를 찾아보세요.</p>
					<p><br>관심 있는 주제를 확인할 수 있고,<br>
					다른 위버들의 최근 정보 조회가 가능합니다.</p>
				</div>
			</div>
			-->
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