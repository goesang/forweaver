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
				text-align:center; background-color: #fff;" class="well-white hero-unit center">
					<h1><i class="fa fa-file-o"></i> 페이지 없음.
					</h1>
					<br />
					<p>
						<b>게시글이 삭제되었거나, 그 외 원인으로 페이지가 존재하지 않습니다!</b>
					</p><br />
					<a onclick="javascript:history.back(-1);" class="btn btn-large btn-info"><i
						class="icon-home"></i> 이전 화면으로 돌아가기</a>
				</div>
				<br />
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>

</body>
</html>