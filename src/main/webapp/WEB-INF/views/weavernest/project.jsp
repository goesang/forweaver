<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
<script src="/resources/weavernest/js/project.js"></script>
</head>
<body style="overflow:hidden;overflow-x:auto;">
	<div data-role="page" id="page1">
		<div id ="header" data-theme="a" data-role="header">
			<h4 class = "title" ></h4>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a onclick="windowMinimize()" data-role="button" data-iconpos="notext"
					data-icon="minus"></a> 
				<a onclick="windowExit()"
					data-role="button" data-iconpos="notext" data-icon="delete"></a>
			</div>
		</div>

		<div data-role="content" >
			<select data-native-menu="false" data-overlay-theme="c"
				id="selectmenuBranch" name="" data-theme="a">
				<option value=''>브랜치를 선택해주세요!</option>
			</select>
			<div id="simpleGitManage" data-role="collapsible-set" data-theme="a"
				data-content-theme="c">
			<a data-icon="refresh" data-role="button" onClick="recover();" data-theme="b"> 작업 복구하기</a> 
				<div class="gitCollapsible" data-role="collapsible">
					<h3>커밋 관리하기</h3>
					<select data-native-menu="false" data-overlay-theme="c"	id="selectmenuCommitLog" data-theme="a">
					</select> 
					<a data-icon="check" data-role="button" data-theme="a" onclick="createCommitWindow('false')"> 커밋하기</a> 
					<a data-icon="plus" data-role="button" data-theme="a" onclick="createCommitWindow('true')"> 최근 커밋에 덧붙이기</a>
			</div>

				<div class="gitCollapsible" data-role="collapsible">
					<h3>브랜치 관리하기</h3>
					<a data-icon="star" data-role="button" data-theme="a" onclick="createBranchWindow()"> 브랜치 파생하기 </a> 
					<a data-icon="delete"	data-role="button" data-theme="a" onClick="createDeleteBranchWindow()"> 브랜치 삭제하기 </a>
				</div>

				<div class="gitCollapsible" data-role="collapsible">
					<h3>프로젝트 공유하기</h3>
					<a data-icon="grid" data-role="button" data-theme="a" onclick="createPullWindow()"> 프로젝트 업데이트</a>
					<a data-icon="home" data-role="button" data-theme="a" onclick="createPushWindow()"> 작업내역 올리기 </a>
				</div>
				
				<a class = "etcButton" data-icon="back" data-role="button" data-theme="b" onClick="window.location.replace('/wn/front');"> 첫화면으로 가기</a>
			</div>
		</div>
	</div>
</body>
</html>