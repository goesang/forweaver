<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>ForWeaver : 모두를 위한 소셜 코딩!</title>
</head>
<body>
<script>
${script}
$(document).ready(function() {
	$("#repasswordButton").click(function(){
		$("#repasswordFieldset").show();
		$("#loginForm").hide();
	});
	$("#loginButton").click(function(){
		$("#loginForm").show();
		$("#repasswordFieldset").hide();
	});
	$("#repasswordSendButton").click(function(){
		$.ajax({                         
		    type: "POST",
		    url: "/repassword",
		    data: "email="+$("#email").val(),
		    success: function(msg) {  //성공시 이 함수를 호출한다.
			    if(msg){
			    	alert("이메일로 비밀번호 변경 링크가 발송되었습니다. 5분 이내에 접속하시길 바랍니다!");
				    }else{
				    alert("가입된 이메일이 아니거나 비밀번호가 이미 발송되었습니다!");
			 }
		   }
		});
	});
});
</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="row">
		<div class="span8">
		<br>
						<div style="height: 406px;" class="active item text-center">
							<embed src="/resources/svg/noun_project_8428.svg" width="240"
								height="240" type="image/svg+xml" />
							<h2>모두를 위한 소셜 코딩!</h2>
							<h5>포위버에 오신것을 환영합니다!</h5>
						</div>

			</div>
			<div class="span4">
					<form action="j_spring_security_check" method="post" id="loginForm">
						<fieldset >
							<legend>로그인</legend>
							<label>닉네임 혹은 이메일</label> 
							<input style="width:90%;" class="text" type="text"
								name="j_username" id="j_username" /> 
								<label>비밀번호</label> 
								<input style="width:90%;"
								class="text" type="password" name="j_password" id="j_password" />
							<div class="form-actions-white">
								<button style="width:145px;" class="btn btn-inverse" type="submit"><i class="fa fa-user"></i>&nbsp;&nbsp;접속하기</button>
								<a style="width:120px;" class="btn btn-inverse" rel="popover" href="/join"><i class="fa fa-pencil-square"></i>&nbsp;&nbsp;회원가입 </a>
								<a class="btn full-button btn-primary" id="repasswordButton"><i class="fa fa-lock"></i></i>&nbsp;비밀번호 재발급</a>
							</div>
						</fieldset>
					</form>
					
					<fieldset id="repasswordFieldset" style="display:none">
							<legend>비밀번호 재발급</legend>
							<label>이메일</label> 
							<input style="width:90%;" class="text" type="text"
								name="email" id="email" /> 
							<div class="form-actions-white">
								<a class="btn full-button btn-inverse" id="repasswordSendButton"><i class="fa fa-lock"></i></i>&nbsp;재발급하기</a>
								<a class="btn full-button btn-primary" id="loginButton"><i class="fa fa-user"></i></i>&nbsp;로그인하기</a>
							</div>
						</fieldset>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
	
</body>
</html>