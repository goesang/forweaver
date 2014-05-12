<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name} - forWeaver</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<body>
<script>
$(document).ready(function() {
	$('#labelPath').append("/");
	$('#tags-input').textext()[0].tags().addTags(
			getTagList("/tags:<c:forEach items='${project.tags}' var='tag'>	${tag},</c:forEach>"));

	
	$("select").selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});
	$("#selectBranch").change(function(){
		if($("#selectBranch option:selected").val() != "체크아웃한 브랜치 없음")
			window.location = $("#selectBranch option:selected").val();
	});
});

var commitlogHref= "/project/${project.name}/commitlog-viewer/commit:";
var fileBrowser = Array();
<c:forEach items="${gitFileInfoList}" var="gitFileInfo">
fileBrowser.push({
	"name" : "${fn:substring(gitFileInfo.name,0,20)}",
	"path" : "${gitFileInfo.path}",
	"directory" : ${gitFileInfo.isDirectory},
	"depth" : ${gitFileInfo.depth},
	"commitLog" :  "${fn:substring(gitFileInfo.simpleCommitLog,0,35)}",
	"dateInt" :  ${gitFileInfo.commitDateInt},
	"commitID" :  "${fn:substring(gitFileInfo.commitID,0,8)}",
	"date": "${gitFileInfo.getCommitDate()}"
});
</c:forEach>
var fileBrowserTree = fileListTransform(fileBrowser);
var fileBrowserURL = "/project/${project.name}/browser/commit:";
showFileBrowser("/");

</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<h5>
				<big><big><i class="fa fa-bookmark"></i> ${project.name}</big></big> 
				<small>${project.description}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li class="active"><a href="/project/${project.name}/">프로젝트
							브라우져</a></li>
					<li><a href="/project/${project.name}/commitlog">커밋 내역</a></li>
					<li><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-link"></i></span> <input
						value="http://forweaver.com/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
			
			<div class="span12 row">	
				<div class="span8"><label id ="labelPath"></label></div>
				<div style = "margin-right:-10px;" class="span1">
					<a class="btn btn-primary" href="/project/down/${project.name}-${fn:substring(selectBranch,0,20)}.zip">
					<i style="zoom: 1.3; -moz-transform: scale(1.3);" class="icon-white icon-circle-arrow-down">
					</i></a>
				</div>				
								
				<select id="selectBranch" class="span3">
					<option value="/project/${project.name}/browser/commit:${selectBranch}">${fn:substring(selectBranch,0,20)}</option>
					<c:forEach items="${gitBranchList}" var="gitBranchName">
						<option value="/project/${project.name}/browser/commit:${gitBranchName}">${fn:substring(gitBranchName,0,20)}</option>
					</c:forEach>
				</select>
				

							
				<table id="fileBrowserTable" class="table table-hover">
				</table>
			</div>

			<!-- .span9 -->
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>