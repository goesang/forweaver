<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name}-forWeaver</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<body>
	<script>

function showUploadContent() {
	
	$('#show-content-button').hide();
	$('#hide-content-button').show();
	$('#upload-form').fadeIn('slow');
	$('#fileBrowserTable').fadeIn('slow');
}

function hideUploadContent() {
	$('#show-content-button').show();
	$('#hide-content-button').hide();
	$('#upload-form').hide();
	$('#fileBrowserTable').show('slow');
}


$(document).ready(function() {
	
	hideUploadContent();
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
	"commiterName" :  "${gitFileInfo.commiterName}",
	"commiterEmail" :  "${gitFileInfo.commiterEmail}",
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
					<li><a href="javascript:void(0);" onclick="openWindow('/project/${project.name}/chat', 400, 500);">채팅</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
					<li><a href="/project/${project.name}/chart">통계</a>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://forweaver.com/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>

			<div class="span12 row">
				<div class="span7">
					<label id="labelPath"></label>
				</div>
				<div style="width: 90px;" class="span2">
					<a id="show-content-button" class="btn btn-primary"
						href="javascript:showUploadContent();"> <i
						style="zoom: 1.3; -moz-transform: scale(1.3);"
						class="icon-white icon-circle-arrow-up"> </i></a> <a
						id="hide-content-button" class="btn btn-primary"
						href="javascript:hideUploadContent();"> <i
						style="zoom: 1.3; -moz-transform: scale(1.3);"
						class="icon-white icon-circle-arrow-up"> </i></a> <a
						class="btn btn-primary"
						href="/project/${project.name}/${selectBranch}/${project.getChatRoomName()}-${selectBranch}.zip">
						<i style="zoom: 1.3; -moz-transform: scale(1.3);"
						class="icon-white icon-circle-arrow-down"> </i>
					</a>

				</div>

				<select id="selectBranch" class="span3">
					<option
						value="/project/${project.name}/browser/commit:${fn:replace(selectBranch,'.', ',')}">${selectBranch}</option>
					<c:forEach items="${gitBranchList}" var="gitBranchName">
						<option
							value="/project/${project.name}/browser/commit:${fn:replace(gitBranchName,'.', ',')}">${gitBranchName}</option>
					</c:forEach>
				</select>
				<form id="upload-form" enctype="multipart/form-data" action="/project/${project.name}/upload"
					method="post">
					<div class="span12">
						<input class="title span10" type="text" name="message"
							placeholder="커밋 내역을 입력해주세요!"></input>
						<button type="submit" class="post-button btn btn-primary"
							style="margin-top: -10px; display: inline-block;">
							<i class="fa fa-check"></i>

						</button>
					</div>
					<div id="file-div" style="padding-left: 20px;">
						<div class='fileinput fileinput-new' data-provides='fileinput'>
							<div class='input-group'>
								<div class='form-control' data-trigger='fileinput'>
									<i class='icon-file '></i> <span class='fileinput-filename'></span>
								</div>
								<span class='input-group-addon btn btn-primary btn-file'><span
									class='fileinput-new'> <i class='icon-upload icon-white'></i></span>
									<span class='fileinput-exists'><i
										class='icon-repeat icon-white'></i></span> <input type='file'
									id='file' multiple='true' name='zip'></span> <a href='#'
									class='input-group-addon btn btn-primary fileinput-exists'
									data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>
							</div>
						</div>
					</div>
				</form>
				<table id="fileBrowserTable" class="table table-hover">
				</table>
			</div>
			<c:if test="${readme.length() > 0}">
				<div class="span12 readme-header"><i class="fa fa-info-circle"></i> 프로젝트 소개</div>
				<div class="span12 readme">${readme}</div>
			</c:if>
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>
