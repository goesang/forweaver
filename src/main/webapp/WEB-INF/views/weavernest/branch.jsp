<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
</head>
<body style='overflow:hidden;overflow-x:auto;'>
	<div data-role="page" id="page1">
		<div data-theme="a" data-role="header">
			<h4>브랜치 파생하기</h4>
				<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a onclick="windowExit()" data-role="button" data-iconpos="notext"
					data-icon="delete"></a>
			</div>
		</div>
		<div data-role="content">
			<div data-role="fieldcontain">
				<h4>브랜치 이름을 입력하세요.</h4>
				<input id="branchName"  placeholder="예시) helloWolrd" type="text">
			</div>
			<div class="ui-grid-a">
				<div class="ui-block-a">
					<a data-role="button" data-theme="a"
						onclick="createBranch($('#branchName').val()); parentExcute('showBranchList(eval(loadBranchList()))');  windowCancel();"
						data-icon="check" data-iconpos="left"> 확인 </a>
				</div>
				<div class="ui-block-b">
					<a data-role="button" data-theme="a" onclick="windowCancel()"
						data-icon="delete" data-iconpos="left"> 취소 </a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>