<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<title>Forweaver : 에러!</title>
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
		<h3><i class="fa fa-file-o"></i> 404 페이지를</h3>
		<h3>찾을 수 없습니다!</h3>
		<br><br><br>
		<div >
			<a rel="external" onclick="javascript:history.back(-1);" data-theme="a" data-role="button" data-icon="back">돌아가기</a>		
		</div>
			
		</div>
	</div>
</body>
</html>