<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css" />
<link rel="stylesheet" type="text/css"	href="/resources/forweaver/css/flat-ui-tag.css" />
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
<script src="/resources/forweaver/js/jquery.tagsinput.js"></script>
</head>


<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext"	data-icon="gear"></a>
			</div>
			<h1>회원가입</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>

		<div data-role="content">
	            <input name="" id="textinput1" placeholder="아이디" value="" type="text">
	            <input name="" id="textinput4" placeholder="이메일 주소" value="" type="email">
	            <input name="" id="textinput2" placeholder="비밀번호" value="" type="password">
	            <input name="" id="textinput3" placeholder="비밀번호 확인" value="" type="password">

	        <input type="submit" data-theme="a" data-icon="flat-new" data-iconpos="left"  value="가입하기">
	    </div>
	</div>
</body>
</html>