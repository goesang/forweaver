<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 에러!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<div class="container">
		<%@ include file="/WEB-INF/views/common/nav.jsp"%>
				<div style="padding-top:60px;padding-bottom:60px;
				text-align:center;  background-color: #fff;" class="well-white hero-unit center">
					<h1><i class="fa fa-exclamation-triangle"></i> 일반 에러.
					</h1>
					<br />
					<p>
						<b>원인 모를 에러가 발생하였습니다. 이전화면으로 돌아가주세요.</b>
					</p><br />
					<a onclick="javascript:history.back(-1);" class="btn btn-large btn-info"><i
						class="icon-home"></i> 이전 화면으로 돌아가기</a>
				</div>
				<br />
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>

</body>
</html>