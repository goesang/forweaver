<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
</head>

<body ondragstart="return false;" ondrop="return false;" style="overflow:hidden;overflow-x:auto;">
<script>
function loadProjectJS(){
	var projectList=eval(loadProject());
	$("#selectProject > option").remove();
	if(projectList.length==0){
		$("#selectProject").append("<option value=''>프로젝트 목록</option>");
		$('#selectProject').selectmenu('refresh');
	}
	else{
		$("#selectProject").append("<option value=''>프로젝트 목록</option>");
		$.each(projectList,function(index,value){
		$("#selectProject").append("<option value="+value[1]+">"+value[0]+"</option>");
	});
		$('#selectProject').selectmenu('refresh');
	}
}

function deleteProjectJS(){
	var selectProject = $("#selectProject option:selected").text();
	var selectProjectPath = $("#selectProject option:selected").val();
	if(selectProject != '') {
			createAlertWindow("프로젝트 삭제하기",
			selectProject+"를 정말 삭제하시겠습니까?",
			"deleteProject('"+selectProject+"'); loadProjectJS();	"); 
	}
		
}	

$(document).ready(function(){
	loadProjectJS();
});
	
</script>
		<div data-role="page">
		<div data-theme="a" data-role="header">
			<h4>위버네스트</h4>
			<div class="ui-btn-right" data-role="controlgroup" data-type="horizontal" data-mini="true">
				<a onclick="windowMinimize()" data-role="button" data-iconpos="notext" data-icon="minus"></a> 
				<a onclick="windowExit()" data-role="button" data-iconpos="notext" data-icon="delete"></a>
			</div>
		</div>
		<div data-role="content" align="center">
				<select id="selectProject" data-native-menu="false" data-overlay-theme="b" data-theme="a">
				</select>
			<a data-icon="plus" data-role="button" data-theme="a" onclick='addProject(createDirectoryWindow()); loadProjectJS();'> 프로젝트 추가하기 </a>
			<a data-icon="delete" data-role="button" data-theme="a" onclick="deleteProjectJS()"> 프로젝트 삭제하기 </a>
			<embed src="/resources/svg/noun_project_20482.svg" width="200" height="200" type="image/svg+xml"/>
			<a class = "etcButton" data-icon="back" data-role="button" data-theme="b" onClick="window.location.replace('/wn/front');"> 첫화면으로 가기</a>
	</div>
	</div>
</body>
</html>