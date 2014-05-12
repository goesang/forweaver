<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>

	<div class="container">
	                        
		<%@ include file="/WEB-INF/common/nav.jsp"%>
				<div style="text-align:center; background-color: #fff;" class="hero-unit center">
					<h1><i class="fa fa-frown-o"></i> 페이지 없음.
					</h1>
					<br />
					<p>
						<b>게시글을 삭제하였거나 그외 여러가지 사정으로 인해 페이지가 존재하지 않습니다.</b>
					</p><br />
					<a onclick="javascript:history.back(-1);" class="btn btn-large btn-info"><i
						class="icon-home icon-white"></i> 이전 화면으로 돌아가기</a>
				</div>
				<br />
		
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>