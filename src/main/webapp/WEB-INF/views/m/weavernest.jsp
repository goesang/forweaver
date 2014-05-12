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
			<h1>위버네스트 소개</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>

		<div data-role="content" align="center">
			<div class="alert alert-warning">
				<p style="text-align:center; font-size:12px;"><b><i class="fa fa-info-circle"></i> 경고!<b></p>
			   			<p style="font-size:11px;">현재 위버네스트는 비공식 배포본이고 라이센스는 각각 설명해 드린 프레임워크 및 라이브러리의 라이센스를 지닙니다!</p>
			   		</div>
			   		<div>
			<h3>WeaverNest란?</h3>
			</div>
			<img src="/resources/img/w1.png">
			 <p>WeaverNest는 버젼관리 툴을 모르는 초보자들을 위해 제작된 GIT 클라이언트입니다.</p>
			 <a href="http://eclipse.org/jgit/">jgit</a>라이브러리 <a href="http://www.eclipse.org/swt/">SWT</a>와 <a href="http://jquerymobile.com/">Jquery Mobile</a>로 만든 웹 앱입니다.<br>
			 지금은 Forweaver.com에만 연동되게 초점이 맞춰졌지만, 개발 목표는 "쉬운 GIT 클라이언트"입니다. <br>
			 현재 윈도우 버젼과 리눅스 버젼 두 개가 있으며 윈도우 버젼은 오류가 발생해서 실행이 아예 되지 않을 수 있습니다.<br>
			 JRE 1.7.0 이상 버젼이 설치되면 사용하실 수 있으며 회원가입을 하시고 로그인하시면 됩니다!<br>
			 윈도우 버젼의 경우 <a href="https://developer.mozilla.org/ko/docs/XULRunner">xulrunner</a>와 같이 배포하고 있습니다!
			 <img src="/resources/img/w2.png">
			 <img src="/resources/img/w3.png">
			 <img src="/resources/img/w4.png">
			 </div>
		</div>

</body>
</html>