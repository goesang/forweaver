<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>

<script>
$(document).ready(function() {
	
	$("#image").change(function(){
        readURL(this);
    });
	
});


</script>

	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="span12">
			</div>
			<div id="signupform" class="well-white">
				<form enctype="multipart/form-data"  onsubmit="return check" class="form-horizontal" action="" method="POST">
					<fieldset >
						<legend><i class="fa fa-pencil-square"></i>&nbsp;&nbsp;회원정보 수정</legend>
						<div class="span11">
						
						
						
						<div class ="control-group" style="text-align:center;">
						<img id="preview" style="height:150px;width:150px;" class="img-polaroid" src="/img/${weaver.id}">
						</div>
						
						
						<div style="margin-left:260px;" class="control-group">
							<div id="file-div" style="padding-left: 20px;">
					<div class='fileinput fileinput-new' data-provides='fileinput'>
					  <div class='input-group' style="width: 340px;">
					    <div class='form-control' data-trigger='fileinput' style="width: 210px;" ><i class='icon-file '></i> <span class='fileinput-filename'></span></div>
					    <span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>
					    <i class='icon-upload icon-white'></i></span>
					    <span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>
						<input onchange ='fileUploadChange(this);' type='file' id='image' multiple='true' name='image'></span>
					   <a href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>
					  </div>
					</div>
						</div>
					</div>


				<div class="control-group">
							<label for="password" class="control-label">비밀번호</label>
							<div class="controls">
								<input style="width:40%;" id="password" name="password" class="input-large"
									type="password" />

							</div>
						</div>

						<div class="control-group">
							<label for="say" class="control-label">자기소개</label>
							<div class="controls">
								<input name="say" id="say" style="width:90%;" type="text"/>

							</div>
						</div>
						
						
						
						<div class="join-form-actions-white">

							<button type="submit" class="btn btn-block btn-inverse"><i class="fa fa-pencil-square icon-white"></i>&nbsp;&nbsp;수정하기</button>
						</div>
						</div>
					</fieldset>
				</form>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>


</body>


</html>