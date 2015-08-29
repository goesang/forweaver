<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<script>

var commitlogHref= "/project/${project.name}/commitlog-viewer/commit:";
var fileBrowser = Array();
<c:forEach items="${gitFileInfoList}" var="gitFileInfo">
fileBrowser.push({
	"name" : "${fn:substring(gitFileInfo.name,0,20)}",
	"path" : "${gitFileInfo.path}",
	"directory" : ${gitFileInfo.isDirectory},
	"commitLog" :  "${fn:substring(gitFileInfo.simpleCommitLog,0,35)}",
	"dateInt" :  ${gitFileInfo.commitDateInt},
	"commiterName" :  "${gitFileInfo.commiterName}",
	"commiterEmail" :  "${gitFileInfo.commiterEmail}",
	"commitID" :  "${fn:substring(gitFileInfo.commitID,0,8)}",
	"date": "${gitFileInfo.getCommitDate()}"
});
</c:forEach>
var fileBrowserURL = "/project/${project.name}/browser/commit:";
showFileBrowser("${filePath}","${selectBranch}",fileBrowser);

$(document).ready(function() {	
	
	$("#selectmenuBranch").change(function(){
		if($("#selectmenuBranch option:selected").val() != "체크아웃한 브랜치 없음")
			setTimeout(function(){
				window.location.replace($("#selectmenuBranch option:selected").val());
			});

	});
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
	     	$( ".td-commitlog" ).hide();
	     } else{
	 	    $( ".td-commitlog" ).show();
	      }
	 });
	 
});

</script>

<body>
	<div id ="page1" data-role="page">

		<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<h1>${project.name}</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">

			<div class="ui-grid-a">
				<div class="ui-block-a">
					<a rel="external" data-role="button" href="/project/${project.name}/${selectBranch}/${project.getChatRoomName()}-${selectBranch}.zip" data-icon="download-file" data-theme="b">다운받기</a>
				</div>
				<div class="ui-block-b">
					<select data-native-menu="false" data-overlay-theme="c"
						id="selectmenuBranch" data-theme="a">
						<option value="/project/${project.name}/browser/commit:${selectBranch}">${fn:substring(selectBranch,0,20)}</option>
						<c:forEach items="${gitBranchList}" var="gitBranchName">
							<option value="/project/${project.name}/browser/commit:${gitBranchName}">${fn:substring(gitBranchName,0,20)}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<table id="fileBrowserTable" class="table table-hover">
			</table>
			
			<c:if test="${readme.length() > 0}">
		
			<div data-collapsed-icon="custom-icon"
			data-expanded-icon="bookmark" data-role="collapsible-set"
			data-theme="b" data-content-theme="b">
			<div data-collapsed="false" data-role="collapsible">

				<h3>프로젝트 소개</h3>

				<div class="post-top-title"><s:eval expression="T(com.forweaver.util.WebUtil).markDownEncoder(readme)" /></div>
			</div>
		</div>
			</c:if>
		</div>
		
	</div>
</body>
</html>