<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css" />
<link rel="stylesheet" type="text/css"	href="/resources/forweaver/css/flat-ui-tag.css" />
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
<script src="/resources/forweaver/js/jquery.tagsinput.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/jqm-icon-pack-fa.css"/>
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/forweaverMobile.css"/>

</head>

<body>

<div data-role="page">
<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
			</div>
			<h1>처음 화면</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>

		<div data-role="content" align="center">
			<div class="alert alert-info">
				<p style="text-align:center; font-size:12px;"><b><i class="fa fa-info-circle"></i> 선택해보세요!<b></p>
			   			<p style="font-size:11px;">이제 수강 신청을 할 강의를 찾거나 새로 진행할 프로젝트를 찾아보세요! 물론 모르는게 있으면 질문해보세요!</p>
			   		</div>
			<a rel="external" href="/m/lecture/" data-theme="f" data-role="button" data-icon="book">강의 찾기</a>
			<a rel="external" href="/m/project/" data-theme="f" data-role="button" data-icon="bookmark">프로젝트 찾기</a>
			<a rel="external" href="/m/community/" data-theme="f" data-role="button" data-icon="comments">질문 하기</a>
			<a rel="external" href="/m/chat/" data-theme="f" data-role="button" data-icon="coffee">채팅 하기</a>
			<a rel="external" href="/m/manage/" data-theme="a" data-role="button" data-icon="gear">관리 하기</a>
		</div>
	</div>
</body>
</html>