<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/jquery.mobile.flatui.css"/>
<link rel="stylesheet" type="text/css" href="/resources/weavernest/css/weavernest.css"/>
<script src="/resources/forweaver/js/jquery-1.10.2.min.js"></script>
<script src="/resources/weavernest/js/jquery.mobile-1.3.1.min.js"></script>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
	<script>
		var commiglogInfor = eval(loadCommitLog());
		$(document).ready(function(){	// 깃 메뉴를 숨김
			$("#commiterImg").attr("src", ""+commiglogInfor[4]+"");
			$('#commiterName').append(commiglogInfor[2]);
			$('#commitTime').append(commiglogInfor[5]);
			$('#commiterEmail').append(commiglogInfor[3]);
			$('#commitLogMassage').append(commiglogInfor[1]);
			$('#source-code').append(
					commiglogInfor[6].split("q0@").join("\n")
					.split("q1@").join("'")
					.split("q2@").join("\"")
					.split("q3@").join("/"));
		});
		SyntaxHighlighter.all();
	</script>
	
<body  style="overflow:hidden;overflow-x:auto;">
	<div data-role="page" id="page1">
		<div data-theme="a" data-role="header">
			<h4>커밋 로그</h4>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a onclick="windowCancel()" data-role="button" data-iconpos="notext"
					data-icon="delete"></a>
			</div>
		</div>
		<div data-role="content">
			<table style="margin-top:-20px;">
				<tr>
					<td rowspan="2"><img	id = "commiterImg" style="margin-top:10px; padding-right:10px;"></td>
					<td style="width:300px;">
						<p id = "commiterName" style="margin-top:30px; font-size: 20px; font-weight:bold;"></p>
					</td>
					<td rowspan="2"> <p id = "commitTime" style="font-size: 20px;  font-weight:bold;"></p></td>
				</tr>
				<tr>
				<td>
				<p id ="commiterEmail" style="margin-top:-5px"></p>
				</td>
				</tr>
			</table>
			<p id = "commitLogMassage" style="margin-top:0px;font-size:13px;"></p>
			<div style="padding-top:10px; height: 300px;">
				<pre id="source-code" class="brush: diff"></pre>
			</div>
		</div>
	</div>
</body>
</html>