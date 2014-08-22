<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>ForWeaver : 학생들을 위한 소셜 코딩!</title>
</head>
<body>

	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="row">
		<div class="span8">
				<div id="myCarousel" class="carousel slide">
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
						<li data-target="#myCarousel" data-slide-to="3"></li>
					</ol>
					<!-- Carousel items -->
					<div class="carousel-inner">
						<div style="height: 406px;" class="active item text-center">
							<embed src="/resources/svg/noun_project_8428.svg" width="240"
								height="240" type="image/svg+xml" />
							<h2>포위버닷컴에 오신것을 환영합니다!</h2>
							<h5>소스코드 공유가 어려우셨나요?</h5>
							<h5>분산버젼관리 GIT의 보편화 forweaver.com!</h5>
						</div>
						<div style="height: 406px;" class="item text-center">
							<embed src="/resources/svg/noun_project_14888.svg" width="240"
								height="240" type="image/svg+xml" />
							<h2>강의를 진행해보세요!</h2>
							<h5>새로운 숙제 제출 시스템을 원하시나요?</h5>
							<h5>커밋 로그를 통해 숙제를 검사하세요!</h5>
						</div>
						<div style="height: 406px;" class="item text-center">
							<embed src="/resources/svg/noun_project_2174.svg" width="240"
								height="240" type="image/svg+xml" />
							<h2>프로젝트를 개설해보세요!</h2>
							<h5>같이 프로젝트를 진행할 동료를 찾으시나요?</h5>
							<h5>forweaver.com을 얼른 사용해보세요!</h5>
						</div>
						<div style="height: 406px;" class="item text-center">
							<embed src="/resources/svg/noun_project_5742.svg" width="240"
								height="240" type="image/svg+xml" />
							<h2>커뮤니티에 질문해보세요!</h2>
							<h5>혹시 프로그래밍을 하다 궁금한게 있나요?</h5>
							<h5>채팅과 커뮤니티를 이용해보세요!</h5>
						</div>
					</div>
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
								<a class="btn full-button btn-primary" href="/forweaver"><i class="fa fa-rocket"></i></i>&nbsp;ForWeaver.com을 소개합니다.</a>
							</div>
						</fieldset>
					</form>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
	
</body>
</html>