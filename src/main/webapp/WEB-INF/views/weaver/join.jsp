<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
<script>
editorMode = true;
var check = false;
var close = "<button type='button' class='close' data-dismiss='alert'>&times;</button>";

function checkWeaver(){
		if($("input[name='tags']").val().length < 3){
			$("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 태그를 하나 이상 입력해주세요!</div>");
			return false;
		}
		
		if($("input[name='tags']").val().length > 65){
			$("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 태그를 너무 많이 입력하셨습니다!</div>");
			return false;
		}
		
		if(!check)
			alert("회원 정보를 제대로 입력하지 않으셨습니다!");
				
		$("form:first").append($("input[name='tags']"));
		return check;
	}

$(document).ready(function() {
	
	$("#image").change(function(){
        readURL(this);
    });
	
	
	$("#id").focusout(function() {
		var t = escape($("#id").val());
		var objPattern =  /[~!@\#$%^&*\()\=+_']/gi;
		  if(objPattern.test(t)){
  		$("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 특수문자를 입력할수 없습니다!</div>");
  		check = false;
  	    }else{
			$.ajax({                         
			    type: "POST",
			    url: "/check",
			    data: "id="+$("#id").val(),
			    success: function(msg) {  //성공시 이 함수를 호출한다.
				    if(msg==true){
				    	$("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 닉네임이 중복됩니다!</div>");
			    		check = false;
					    }else{
			    	check = true;
				 }
			   }
			});
  	    }
	});
	$("#password").focusout(function() {
	    if($("#password").val().length<=3){
	    	$("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 비밀번호를 4자리 이상 입력해주세요!</div>");
    		check = false;
    	    }else{
	    	check = true;
		 }
	});
	$("#rePassword").focusout(function() {
		if($("#password").val()!=$("#rePassword").val()){
			$("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 비밀번호가 일치하지 않습니다!</div>");
    		check = false;
    	    }else{
	    	check = true;
		 }
	});
	$("#email").focusout(function() {
		var t = escape($("#email").val());
		  if(t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null && t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null){
			  $("#signupform").prepend("<div class='alert'>"+close+"<strong>경고!</strong> 올바른 이메일 주소를 입력해주세요!</div>");
    		check = false;
    	    }else{
	    	check = true;
		 }
		  $.ajax({                         
			    type: "POST",
			    url: "/check",
			    data: "id="+$("#email").val(),
			    success: function(msg) {  //성공시 이 함수를 호출한다.
				    if(msg=="true"){
			    		$("#email").tooltip({title: "이메일이 중복됩니다!",trigger: 'manual',placement:"right"}).tooltip('show');
			    		check = false;
					    }else{
			    	check = true;
				 }
			   }
			});
	});
});


</script>

	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

			<div id="signupform" class="well-white">
				<form onsubmit="return checkWeaver()" enctype="multipart/form-data" class="form-horizontal" action="" method="POST">
					<fieldset >
						<legend><i class="fa fa-pencil-square"></i>&nbsp;&nbsp;회원가입</legend>
						<div class="span6">
						<div class="control-group">
							
							<label  for="id" class="control-label">닉네임</label>
							<div class="controls">
								<input id="id" name="id" class="input-large"
									type="text" value="" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="email" class="control-label">이메일</label>
							<div class="controls">
								<input id="email" name="email" class="input-large" type="text"
									value="" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="password" class="control-label">비밀번호</label>
							<div class="controls">
								<input id="password" name="password" class="input-large"
									type="password" />

							</div>
						</div>
						<div class="control-group">
							<label for="rePassword" class="control-label">비밀번호 확인</label>
							<div class="controls">
								<input id="rePassword" class="input-large"
									type="password" />

							</div>
						</div>
						
						<div class="control-group">
							<label for="studentID" class="control-label">학번</label>
							<div class="controls">
								<input name="studentID" id="studentID" class="input-large" type="text"/>

							</div>
						
						</div>
						</div>
						<div class="span4">
						
						<div class ="control-group" style="text-align:center;">
						<img id="preview" style="height:190px;width:190px;" class="img-polaroid" src="">
						</div>
						
						
						<div class="control-group">
							<div id="file-div" style="padding-left: 20px;">
					<div class='fileinput fileinput-new' data-provides='fileinput'>
					  <div class='input-group' style="width: 340px;">
					    <div class='form-control' data-trigger='fileinput' style="width: 210px;" ><i class='icon-file '></i> <span class='fileinput-filename'></span></div>
					    <span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>
					    <i class='fa fa-arrow-circle-o-up icon-white'></i></span>
					    <span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>
						<input onchange ='fileUploadChange(this);' type='file' id='image' multiple='true' name='image'></span>
					   <a href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>
					  </div>
					</div>
						</div>
					</div>
						</div>
						<div class="span11">
						<div class="control-group">
							<label for="say" class="control-label">자기소개</label>
							<div class="controls">
								<input name="say" id="say" style="width:90%;" type="text"/>

							</div>
						</div>
						<div class="join-form-actions-white">

							<button type="submit" class="btn btn-block btn-inverse"><i class="fa fa-pencil-square"></i>&nbsp;&nbsp;가입하기</button>
						</div>
						</div>
					</fieldset>
				</form>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>


</body>


</html>