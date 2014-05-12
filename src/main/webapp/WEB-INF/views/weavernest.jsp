<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>

<body>

	<div  class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
			<ul class="nav nav-tabs" id="myTab">
					<li><a href="/forweaver">포위버란 무엇인가</a></li>
					<li class="active"><a href="/weavernest">위버네스트란 무엇인가?</a></li>
				</ul>
			<div class='page-header'>

			<h3>WeaverNest란?</h3>
			</div>
			<img src="/resources/img/w1.png">
			 WeaverNest는 버젼관리 툴을 모르는 초보자들을 위해 제작된 GIT 클라이언트입니다.<br>
			 <a href="http://eclipse.org/jgit/">jgit</a>라이브러리 <a href="http://www.eclipse.org/swt/">SWT</a>와 <a href="http://jquerymobile.com/">Jquery Mobile</a>로 만든 웹 앱입니다.<br>
			 지금은 Forweaver.com에만 연동되게 초점이 맞춰졌지만, 개발 목표는 "쉬운 GIT 클라이언트"입니다. <br>
			 현재 윈도우 버젼과 리눅스 버젼 두 개가 있으며 윈도우 버젼은 오류가 발생해서 실행이 아예 되지 않을 수 있습니다.<br>
			 JRE 1.7.0 이상 버젼이 설치되면 사용하실 수 있으며 회원가입을 하시고 로그인하시면 됩니다!<br>
			 (가입하기 귀찮으시면 'test'라는 여분 아이디가 있습니다. 비밀번호는 123)<br>
			 윈도우 버젼의 경우 <a href="https://developer.mozilla.org/ko/docs/XULRunner">xulrunner</a>와 같이 배포하고 있습니다!
			 <img src="/resources/img/w2.png">
			 <img src="/resources/img/w3.png">
			 <img src="/resources/img/w4.png">
			
					
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>