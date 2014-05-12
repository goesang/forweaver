<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name} ~ ${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<body>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			    <h5><big><big>${project.name}</big></big> 
			    	<small>${project.description}</small>
			    </h5>  
		</div>
		<div class="row">
				<div class="span3">
					<div class=" ">
						<ul id="sidenav" class="nav nav-pills nav-stacked">
						<li><a href="/project/${project.name}/"> <strong>프로젝트 브라우져</strong></a></li>
						<li><a href="/project/${project.name}/commitlog"><strong>커밋 내역</strong></a></li>
						<li><a href="/project/${project.name}/community"><strong>커뮤니티</strong></a></li>
						<li class="active"><a href="/project/${project.name}/information"><strong>프로젝트 분석</strong></a></li>
						<li><a href="/project/${project.name}/weaver"><strong>참가자 목록</strong></a></li>
						<li><a href="/manageAll"><strong>나가기</strong></a></li>
						</ul>
					</div>
				</div>
				<!-- .span3 -->
					<div class = "span9">
					
					</div>			
				<!-- .span9 -->

			<!-- .tabbable -->
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>