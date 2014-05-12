<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
</head>

<body style="overflow:hidden;overflow-x:auto;">
<script>
initStatic();
rememberWeaverEmail("${weaverEmail}");
$(document).ready(function(){
var projectList=eval(loadProject());
if(projectList.length==0){
	$('#selectProject').selectmenu('refresh');
}
else{
$.each(projectList,function(index,value){$("#selectProject").append("<option value="+value[1]+">"+value[0]+"</option>");});
	$('#selectProject').selectmenu('refresh');
}

$('#selectOnlineProject').change(function(){
	setTimeout(function(){
		window.location.replace("/wn/"+$("#selectOnlineProject option:selected").val());
	},0);
});

$('#selectProject').change(function(){
	var selectProject = $("#selectProject option:selected").text();
	var selectProjectPath = $("#selectProject option:selected").val();
		if(selectProjectPath == ''){
			setTimeout(function(){window.location.replace("/wn/project-admin");},0);
			return;
		}
		rememberSelectProject(selectProject,selectProjectPath);
		setTimeout(function(){window.location.replace("/wn/project");},0);
		});
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
			<embed src="/resources/svg/noun_project_6217.svg" width="100" height="100" type="image/svg+xml"/>
			<div data-role="fieldcontain">
				<select data-native-menu="false" data-overlay-theme="b" id="selectOnlineProject" name="" data-theme="a">
					<option value=''>프로젝트 가져오기!</option>
					
					<optgroup label="강의">
					<c:forEach items="${lectures}" var="lecture">
						<option value='/${lecture.name}/download'>${lecture.name}</option>
					</c:forEach>
					</optgroup>
					<optgroup label="프로젝트">
					<c:forEach items="${projects}" var="project">
						<option value='/${project.name}/download'>${project.name}</option>
					</c:forEach>					
					</optgroup>
				</select>
			</div>
			<br>
			<embed src="/resources/svg/noun_project_14894.svg" width="100" height="100" type="image/svg+xml"/>
			<div data-role="fieldcontain">
				<select id="selectProject" data-native-menu="false" data-overlay-theme="b" data-theme="a">
				<option value=''>프로젝트 진행하기!</option>
				<option value=''>프로젝트 관리하기!</option>
				</select>
			</div>
			<a data-icon="delete" data-role="button" data-theme="a" href="/wn/logout"> 로그아웃 하기 </a>
		</div>
	</div>
</body>
</html>