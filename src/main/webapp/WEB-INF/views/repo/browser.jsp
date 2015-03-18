<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>${repo.lectureName}/${repo.name}~${cov:htmlEscape(repo.description)}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<body>
<script>
$(document).ready(function() {
	$('#labelPath').append("/");
	$("select").selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});
	$("#selectBranch").change(function(){
		if($("#selectBranch option:selected").val() != "empty_Branch")
			window.location = $("#selectBranch option:selected").val();
	});
});

var commitlogHref= "/lecture/${repo.lectureName}/${repo.name}/commitlog-viewer/commit:";
var fileBrowser = Array();
<c:forEach items="${gitFileInfoList}" var="gitFileInfo">
fileBrowser.push({
	"name" : "${fn:substring(gitFileInfo.name,0,20)}",
	"path" : "${gitFileInfo.path}",
	"directory" : ${gitFileInfo.isDirectory},
	"depth" : ${gitFileInfo.depth},
	"commitLog" :  "${fn:substring(gitFileInfo.simpleCommitLog,0,40)}",
	"dateInt" :  ${gitFileInfo.commitDateInt},
	"commiterName" :  "${gitFileInfo.commiterName}",
	"commiterEmail" :  "${gitFileInfo.commiterEmail}",
	"commitID" :  "${fn:substring(gitFileInfo.commitID,0,8)}",
	"date": "${gitFileInfo.getCommitDate()}"
});
</c:forEach>
var fileBrowserTree = fileListTransform(fileBrowser);
var fileBrowserURL = "/lecture/${repo.lectureName}/${repo.name}/browser/commit:";
showFileBrowser("/");
</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header page-header-none">
			<h5>
				<big><big><i class="fa fa-bomb"></i> ${repo.name}</big></big> 
				<small>${cov:htmlEscape(repo.description)}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/lecture/${repo.lectureName}/repo">돌아가기</a></li>
					<li class="active"><a href="/lecture/${repo.lectureName}/${repo.name}/browser">소스목록</a></li>
					<li><a href="/lecture/${repo.lectureName}/${repo.name}/commitlog">커밋내역</a></li>
					<c:if test="${repo.getCategory() == 2}">
						<li><a onclick="return confirm('정말 팀프로젝트로 포크하시겠습니까?')"
						href="/lecture/${repo.lectureName}/${repo.name}/fork">포크</a></li>
					</c:if>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/g/${repo.lectureName}/${repo.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
		

			<div class="span12 row">	
				<div class="span8"><label id ="labelPath"></label></div>
				<div style = "margin-right:-10px;" class="span1">
					<a	class = "btn btn-primary" href="/lecture/${repo.lectureName}/${repo.name}/${selectBranch}/${repo.lectureName}-${repo.name}.zip">
					<i  class="fa fa-arrow-circle-o-down">
					</i></a>
				</div>				
							
				<select id="selectBranch" class="span3">
					<option value="/lecture/${repo.lectureName}/${repo.name}/browser/commit:${selectBranch}">${selectBranch}</option>
					<c:forEach items="${gitBranchList}" var="gitBranchName">
						<option value="/lecture/${repo.lectureName}/${repo.name}/browser/commit:${gitBranchName}">${gitBranchName}</option>
					</c:forEach>
				</select>
				<table id="fileBrowserTable" class="table table-hover">
				</table>
			</div>
			<c:if test="${readme.length() > 0}">
				<div class="span12 readme-header"><i class="fa fa-info-circle"></i> 프로젝트 소개</div>
				<div class="span12 readme"><s:eval expression="T(com.forweaver.util.WebUtil).markDownEncoder(readme)" /></div>
			</c:if>
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>