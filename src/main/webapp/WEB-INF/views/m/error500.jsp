<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>

<body>

	<div data-role="page">
<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
			</div>
			<h1>에러</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-iconpos="notext" data-role="button" data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content" align="center">
		<h3>원인 모를 에러가 발생하였습니다!</h3>
		<div style="margin-top:-20px;">
			<embed src="/resources/svg/noun_project_11426.svg" 
					width="100" height="100" type="image/svg+xml" />
			<a rel="external" onclick="javascript:history.back(-1);" data-theme="a" data-role="button" data-icon="back">돌아가기</a>		
		</div>
			
		</div>
	</div>
</body>
</html>