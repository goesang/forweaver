<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
<script>
var check = false;
$(document).ready(function() {
	$("#nickName").focusout(function() {
		var t = escape($("#nickName").val());
		var objPattern = /^[a-zA-Z0-9]+$/;
		  if(!objPattern.test(t)){
  		$("#nickName").tooltip({title: "영문 숫자를 조합하여 입력해주세요!",trigger: 'manual',placement:"right"}).tooltip('show');
  		check = false;
  	    }else{
	    	$("#nickName").tooltip('destroy');
			$.ajax({                         
			    type: "POST",
			    url: "/check",
			    data: "name="+$("#nickName").val(),
			    success: function(msg) {  //성공시 이 함수를 호출한다.
				    if(msg==true){
			    		$("#nickName").tooltip({title: "닉네입이 중복됩니다!",trigger: 'manual',placement:"right"}).tooltip('show');
			    		check = false;
					    }else{
				    	$("#nickName").tooltip('destroy');
			    	check = true;
				 }
			   }
			});
  	    }
	});
});


</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="span12">
			<div class="span3">
				
			</div>
			<div class="span6">

				<form id="signupform" onsubmit="return check" class="form-horizontal" action="/facebook/join" method="POST">
					<fieldset>
						<legend><i class="fa fa-facebook-square"></i>&nbsp;&nbsp;페이스북으로 회원가입</legend>
						<div class="control-group">
							<label for="nickName" class="control-label">닉네임</label>
							<div class="controls">
								<input id="nickName" name="nickName" class="input-large"
									type="text"  placeholder="닉네임을 입력해주세요!" />
							</div>
						</div>
						
						<div class="control-group">
							<label for="email" class="control-label">이메일</label>
							<div class="controls">
								<input id="email" name="email" class="input-large" type="text"
									value="${email}"  readonly="readonly" />
							</div>
						</div>

						<div class="form-actions-white">

							<button type="submit" class="btn btn-block btn-inverse">
							<i class="fa fa-facebook-square icon-white"></i>&nbsp;&nbsp;가입하기</button>
						</div>
						<input name="imgSrc" type="hidden"	value="${imgSrc}" />
					</fieldset>
				</form>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>


</body>


</html>