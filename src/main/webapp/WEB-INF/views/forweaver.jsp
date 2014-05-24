<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>Forweaver : 포위버란 무엇인가?</title>
</head>

<body>

	<div  class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="/forweaver">포위버란 무엇인가?</a></li>
				</ul>
		
			<div class='page-header'>
			<h3>Forweaver.com이란?</h3>
			</div>
			Forweaver.com은 <a href="http://gitstack.com/">GitStack</a>과 같이 저장소와 회원을 관리하는 도구입니다. <br>
			하지만 단순히 저장소 관리를 넘어서 특별히 '숙제 저장소'나 '예제 저장소'와 같은 서비스가 구현되어 있습니다.<br>
			참고로 사이트에 대해 생소한 단어들이 있어 설명해 드리자면 나무는 GIT 저장소이고 숲은 저장소들이 모인 공간을 말합니다.<br>
			그리고 git에서 저장소에 접근하시려면 다음과 같이 접근하시면 됩니다.<br><br>
			<pre>git clone http://forweaver.com/아이디/저장소.git</pre>
			저장소 접근은 현재 http로 가능하며 위버네스트라는 클라이언트가 있지만, 기본적으로 git 클라이언트로 모든 접근이 가능합니다.<br>			 
			참고로 사이트는 스프링과 스프링 시큐리티 그리고 부트스트랩으로 구성하였습니다!
			<br>
			<div class='page-header'>
			<h3>수업을 위한 저장소?</h3>
			</div>
			현재 새로 만들어본 저장소 서비스는 예제 저장소와 숙제 저장소입니다.<br>
			예제 저장소는 단순히 읽기 전용 저장소라고 생각하시면 됩니다.<br>
			그래서 만일 숲 개설자가 아닌 다른 사람이 저장소에 쓰기 동작을 하면 차단됩니다.<br>
			하지만 숙제 저장소의 경우 교수와 학생이라는 두 관계가 있어서 교수는 모든 브랜치에 접근이 가능하지만<br>
			학생은 오로지 자신의 브랜치에만 읽고 쓰기가 가능하도록 설정해 놓았습니다.<br>
			<br>
			
			<div class='page-header'>
			<h3>그런데 Forweaver가 뭔 뜻인가요?</h3>
			</div>
			<img src="http://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Philetairus_socius_%28Etosha%2C_2013%29.jpg/800px-Philetairus_socius_%28Etosha%2C_2013%29.jpg"><br>
			아이디어 구상 중에 TV에서 다큐멘터리를 보다가 <a href="http://en.wikipedia.org/wiki/Sociable_Weaver">Sociable Weaver</a>라는 새를 알게 됐습니다.<br>
			소셔블 위버라는 새는 보시는 바와 같이 조그만 새지만 집단생활을 하고, 또 집단으로 둥지를 짓습니다.<br>
			근데 그 이 새들이 잔가지를 물어와 짓는 둥지가 몇 톤까지 나간다고 합니다. 조금만 새들이 참 경이롭기까지 하더군요.<br>
			<img src="http://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Webervogelnst_Auoblodge.JPG/800px-Webervogelnst_Auoblodge.JPG"><br>
			그래서 프로젝트 컨셉?을 Weaver로 정하고 사이트 주소는 forweaver(위버를 위한!) 그리고 클라이언트 프로그램 이름은 WeaverNest로 하였습니다.<br>



		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>