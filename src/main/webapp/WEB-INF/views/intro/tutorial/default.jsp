<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>Forweaver : 포위버란 무엇인가?</title>
</head>

<body>
	<div  class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="/intro/tutorial/default">웹 업로드</a></li>
					<li><a href="/intro/tutorial/egit">이클립스</a></li>
					<li><a href="/intro/tutorial/vgit">비주얼 스튜디오</a></li>
				</ul>
		
			<center><h3>포위버에 프로젝트 업로드하기</h3></center><br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut1.png"/>	
				<div class="span5">
					<br><br><br><br><br><br>
					<big>학교 홈페이지에 과제를 올리는 것과 같이<br>
							자신의 프로젝트를 <strong>Zip</strong>파일로 압축합니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut2.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>그리고 본인이 가입한 프로젝트에 접속하여 <br>
					왼쪽 상단에 있는 <strong>파일 업로드</strong>버튼을 선택합니다.<br><br>
					</big>
				</div>
			</div>
			<br>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut3.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						이제 자신이 수행한 <strong>작업 내역</strong>을 입력하고 나서<br>
						방금전에 압축한 <strong>압축파일</strong>을 업로드하면 됩니다.<br><br>
					</big>
				</div>
			</div>
			<br>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut4.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						업로드가 진행되면 로딩화면이 나옵니다.</br>
						(인터넷 속도 + 압축크기에 따라 시간이 결정됩니다)
					</big>
				</div>
			</div>
			<br>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut5.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<b>프로젝트가 업로드 되었습니다!</b>
					</big>
				</div>
			</div>
			<br>
			<br>
			
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>파일 하나만 업로드하기</h3>
			<h5>보통 프로젝트를 하다보면 간단하게 파일 하나만 변경하는 경우가 많은데,</h5>
			<h5>이럴 경우 그냥 파일 하나만 업로드하시면 됩니다.</h5>
			<h5>(<b>ZIP파일</b>은 프로젝트 전체가 바뀌고 그외 소스파일은 추가됩니다.)</h5></center><br><br>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut6.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>일단 소스코드</strong>를 선택하여 <br>
						zip파일과 같은 방법으로 업로드합니다.<br><br>
						이때 <strong>디렉토리</strong>를 클릭하시면 <br>
						접근할 수 있기 때문에 원하는 위치에 가셔서<br>
						파일을 업로드하실 수 있습니다.
					</big>
				</div>
			</div>
			<br><br>
			
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut7.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<b>파일 하나가 추가되었습니다!</b><br>
					</big>
				</div>
			</div>
			<br>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>한 저장소에 여러 프로젝트 업로드하기</h3>
					<h5>프로젝트를 진행하다보면 실제로는 여러 프로젝트를 동시에 수행하는 경우가 있습니다.</h5>
					<h5>예를 들어 아두이노와 안드로이드를 연동하는 프로젝트의 경우  </h5>
					<h5>아두이노 소스코드와 안드로이드 소스코드를 나누어 관리하여야 하는데, </h5>
					<h5>저장소를 두개 생성하여 관리해야 하지만 <strong>GIT의 경우 하나로 관리</strong>가 가능합니다. </h5>
			</center>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut8.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						본인의 웹 브라우져 상단에 주소창에서 <br>
						commit:부분을 <strong>master에서 master2</strong>로 변경하고<br>
						사이트에 다시 접속합니다. <br>
						(다른 이름을 입력하셔도 됩니다.)<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut9.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						이제 master2로 변경된걸 확인하실 수 있는데<br>
						<b>이 작업이 주소로 브랜치를 변경하는 방법입니다.</b>
					</big>
				</div>
			</div>
			<br><br>
			
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut10.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br>
					<big>
						그리고나서 <b>압축파일</b>을 업로드하시면 됩니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut11.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						업로드후에 <b>브랜치 선택 버튼</b>을 클릭하시면<br>
						master2 브랜치가 추가된걸 확인하실 수 있습니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/tutorial/default/defalut12.png"/>	
				<div class="span5">
					<br><br><br><br><br><br>
					<big>
						<b>이제부터 또 다른 코드를 관리할 수 있습니다!</b>
					</big>
				</div>
			</div>
			<br>
			
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>