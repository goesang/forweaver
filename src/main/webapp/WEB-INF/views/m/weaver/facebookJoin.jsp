<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css" />
<link rel="stylesheet" type="text/css"	href="/resources/forweaver/css/flat-ui-tag.css" />
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/jqm-icon-pack-fa.css"/>
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/forweaverMobile.css"/>

<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
<script src="/resources/forweaver/js/jquery.tagsinput.js"></script>
</head>


<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
			</div>
			<h1>페이스북으로 회원가입</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>

		<div data-role="content">
	            <input name="" id="textinput1" placeholder="아이디" value="" type="text">
	            <input name="" id="textinput4" placeholder="이메일 주소" value="{$email}" type="email" readonly="readonly">
	        <input type="submit" data-theme="a" data-icon="facebook" data-iconpos="left"  value="가입하기">
	    </div>
	</div>
</body>
</html>