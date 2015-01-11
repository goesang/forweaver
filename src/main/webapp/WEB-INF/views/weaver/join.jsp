<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>ForWeaver - 얼른 가입해보세요!</title>
</head>
<body>
<script>
var check = false;
var close = "<button type='button' class='close' data-dismiss='alert'>&times;</button>";
$(document).ready(function() {
	
	$("#id").focusout(function() {
		var t = escape($("#id").val());
		var objPattern =  /[~!@\#$%^&*\()\=+_']/gi;
		  if(objPattern.test(t)){
  		$(".container-page").prepend("<div class='alert alert-danger' role='alert'>"+close+"<strong>경고!</strong> 특수문자를 입력할수 없습니다!</div>");
  		check = false;
  	    }else{
			$.ajax({                         
			    type: "POST",
			    url: "/check",
			    data: "id="+$("#id").val(),
			    success: function(msg) {  //성공시 이 함수를 호출한다.
				    if(msg==true){
				    	$("#signupform").prepend("<div class='alert alert-danger' role='alert'>"+close+"<strong>경고!</strong> 닉네임이 중복됩니다!</div>");
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
	    	$(".container-page").prepend("<div class='alert alert-danger' role='alert'>"+close+"<strong>경고!</strong> 비밀번호를 4자리 이상 입력해주세요!</div>");
    		check = false;
    	    }else{
	    	check = true;
		 }
	});
	$("#rePassword").focusout(function() {
		
		if($("#password").val()!=$("#rePassword").val()){
			$(".container-page").prepend("<div class='alert alert-danger' role='alert'>"+close+"<strong>경고!</strong> 비밀번호가 일치하지 않습니다!</div>");
    		check = false;
    	    }else{
	    	check = true;
		 }
	});
	$("#email").focusout(function() {
		var t = escape($("#email").val());
		  if(t.match(/^(\w+)@(\w+)[.](\w+)$/ig) == null && t.match(/^(\w+)@(\w+)[.](\w+)[.](\w+)$/ig) == null){
			  $(".container-page").prepend("<div class='alert alert-danger' role='alert'>"+close+"<strong>경고!</strong> 올바른 이메일 주소를 입력해주세요!</div>");
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
		<%@ include file="/WEB-INF/views/common/nav.jsp" %>	
			<form  class="container-page" enctype="multipart/form-data"  onsubmit="return check" class="form-horizontal" action="" method="POST">		
			<fieldset >
			<div class="col-md-6">
				<legend><i class="fa fa-pencil-square"></i>&nbsp;&nbsp;회원가입</legend>
				
				<div class="form-group col-lg-12">
					<label>닉네임</label>
					<input type="text" name="id" class="form-control form-inline-input" id="id" value="">
				</div>
				
				<div class="form-group col-lg-12">
					<label>이메일</label>
					<input type="email" name="email" class="form-control form-inline-input" id="email" value="">
				</div>
				
				<div class="form-group col-lg-6">
					<label>비밀번호</label>
					<input type="password" name="password" class="form-control" id="password" value="">
				</div>
				
				<div class="form-group col-lg-6">
					<label>비밀번호 확인</label>
					<input type="password" name="rePassword" class="form-control" id="rePassword" value="">
				</div>
				<div class="form-group col-lg-12">
						<label>자기소개</label>
						<input  name="say" class="form-control" id="say" value="">
					</div>
			</div>
		
			<div class="col-md-6 join-div-left">
				<div class="fileinput fileinput-new" data-provides="fileinput">
				  <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 200px; height:200px;"></div>
				  <div>
				    <span class="btn btn-default btn-file"><span class="fileinput-new">이미지 선택</span><span class="fileinput-exists">변경</span><input type="file" id='image' multiple='true' name='image'></span>
				    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">삭제</a>
				  </div>
				</div>
				
			</div>
			
			<div class="col-md-12">
					<button style="width:200px"type="submit" class="center-block btn btn-inverse">
						<i class="fa fa-pencil-square"></i>&nbsp;&nbsp;가입하기
					</button>
			</div>
			</fieldset>
			
			</form>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>


</body>


</html>