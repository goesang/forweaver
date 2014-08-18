<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
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
	<form enctype="multipart/form-data" onsubmit="return check" class="form-horizontal" action="" method="POST">
<!--
 						<legend style="padding-left : 15px;">
							<i class="fa fa-pencil-square"></i>&nbsp;&nbsp;회원정보 수정
						</legend>
-->

		<ul class="nav nav-tabs">
			<li class="active"><a href="#home" data-toggle="tab">기본정보</a></li>
			<li><a href="#profile" data-toggle="tab">비밀번호</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane active in" id="home" align="center">
					<div class="control-group">
						<img id="preview" style="height: 120px; width: 120px;" class="img-polaroid" src="/${weaver.id}/img">
					</div>

					<div class="control-group">
						<div id="file-div" style="padding-left: 10px;">
							<div class='fileinput fileinput-new' data-provides='fileinput'>
								<div class='input-group' style="width: 340px;">
									<div class='form-control' data-trigger='fileinput'
										style="width: 210px; text-align: left">
										<i class='icon-file'></i> <span class='fileinput-filename'></span>
									</div>
									<span class='input-group-addon btn btn-primary btn-file'>
										<span class='fileinput-new'> <i
											class='fa fa-arrow-circle-o-up icon-white'></i>
									</span> <span class='fileinput-exists'> <i
											class='icon-repeat icon-white'></i>
									</span> <input onchange='fileUploadChange(this);' type='file'
										id='image' multiple='true' name='image'>
									</span> <a href='#'
										class='input-group-addon btn btn-primary fileinput-exists'
										data-dismiss='fileinput'> <i
										class='icon-remove icon-white'></i>
									</a>
								</div>
							</div>
						</div>
						<!--  
				<div class="control-group">
							<label for="password" class="control-label">비밀번호</label>
							<div class="controls">
								<input style="width:40%;" id="password" name="password" class="input-large"
									type="password" />
							</div>
						</div>
-->
						<div class="control-group" style="padding-left: 7px;">
							<textarea placeholder="자기소개를 다시 입력해주세요!" name="say" id="say"
								style="width: 249px;" rows="3">${weaver.say}</textarea>
						</div>

					<div class="control-group" style="padding-left: 6px;">
						<button style="width: 275px;" type="submit"
							class="btn btn-block btn-inverse">
							<i class="fa fa-pencil-square"></i>&nbsp;&nbsp;수정하기
						</button>
					</div>
				</div>
			</div>

			<div class="tab-pane fade" id="profile">
					<br/>
					<div class="control-group" align="center">
						<i style="font-size:100px;"class="fa fa-key"></i>
					</div>
					<div align="center" class="control-group" style="padding-left: 7px;">
						<label style="margin-right: 120px;" for="password">기존 비밀번호</label>
						<input type="password" id="password" name="password" class="input-large"></input>
					</div>
					<div align="center" class="control-group" style="padding-left: 7px;">
						<label style="margin-right: 130px;" for="newpassword">새 비밀번호</label>
						<input type="password" id="newpassword" name="newpassword" class="input-large"></input>
					</div>
					<div align="center" style="padding-left: 6px;">
						<button style="width: 225px;" type="submit" class="btn btn-block btn-inverse">
							<i class="fa fa-pencil-square"></i>&nbsp;&nbsp;수정하기
						</button>
					</div>
			</div>
		</div>
	</form>
</body>


</html>