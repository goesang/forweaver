<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Mobile Home</title>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
</head>


<body style="overflow:hidden;overflow-x:auto;">

	<div data-role="page">
		<script>
		$(document).ready(function() {
			$('#treeSelectMenu').change(function() {
				setTimeout(function() { 
					window.location.replace("/wn/front");
				}, 0);
				downloadTree(true,"http://forweaver.com/${lecture.name}/"+$("#treeSelectMenu option:selected").val()+'.git',"${lecture.name}@"+$("#treeSelectMenu option:selected").val()); 
			});
			
			$('#example').click(function() {
				setTimeout(function() { 
					window.location.replace("/wn/front");
				}, 0);
				downloadTree(false,"http://forweaver.com/${lecture.name}/example.git","${lecture.name}@example"); 
			});
		});
	</script>
		<div data-theme="a" data-role="header">
			<h4>위버네스트</h4>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a onclick="windowMinimize()" data-role="button" data-iconpos="notext"
					data-icon="minus"></a> 
				<a onclick="windowExit()"
					data-role="button" data-iconpos="notext" data-icon="delete"></a>
			</div>
		</div>
		<div data-role="content" align="center">
		
			<a data-icon="back" data-role="button" data-theme="a" 
			onClick="window.location.replace('/wn/front');">처음화면으로 나가기</a>
				
			<embed src="/resources/svg/noun_project_12584.svg" 
			width="170"	height="170" type="image/svg+xml" />
				
			<a id = "example" data-iconpos="right" data-icon="star" data-role="button" data-theme="a">예제 소스 가져오기</a>	
				
			<select data-native-menu="false" data-overlay-theme="b"
				id="treeSelectMenu" data-theme="a">
				<option value=''>숙제 가져오기!</option>
				<c:forEach items="${lecture.repoes}" var="repo">
					<c:if test="${repo.name != 'example'}">
						<option  value="${repo.name}">${repo.name}</option>
					</c:if>
				</c:forEach>
			</select>
			
		</div>

	</div>

</body>
</html>