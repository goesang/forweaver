<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title> 고객센터</title>
</head>
<body>

	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div id="signupform" class="well-white">
			<form  
				class="form-horizontal" action="" method="POST">
				<fieldset>
					<legend>
						<i class="fa fa-phone-square"></i>&nbsp;&nbsp;고객센터
					</legend>
						<div class="control-group">
							<label for="say" class="control-label">제목</label>
							<div class="controls">
								<input maxlength="50" name="title"
									placeholder="제목을 적어주세요!" id="title"
									style="width: 80%;" type="text" />

							</div>
							<br>
							<div class="control-group">
							<label for="say" class="control-label">회신 이메일</label>
							<div class="controls">
								<input maxlength="50" name="email"
									placeholder="이메일을 적어주세요!" id="email"
									style="width: 80%;" type="text" />

							</div>
							<br>
							<div class="control-group">
							<label for="content" class="control-label">내용</label>
							<div class="controls">
								<textarea name="content"
									placeholder="사이트 버그 및 개선사항 문의 사항을 최대한 자세히 적어주세요!" id="content"
									style="width: 78%; height: 200px"></textarea>

							</div>
						</div>
						</div>
						<div class="join-form-actions-white">

							<button type="submit" class="btn btn-block btn-inverse">
								<i class="fa fa-pencil-square"></i>&nbsp;&nbsp;보내기
							</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>


</body>


</html>