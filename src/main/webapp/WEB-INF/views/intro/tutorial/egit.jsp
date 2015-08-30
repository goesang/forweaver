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
					<li><a href="/intro/tutorial/default">웹 업로드</a></li>
					<li class="active"><a href="/intro/tutorial/egit">이클립스</a></li>
					<li><a href="/intro/tutorial/vgit">비주얼 스튜디오</a></li>
				</ul>
			<center><h2>&nbsp;이클립스(Luna)와 포위버 연동!</h2></center>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>이클립스에 Git 관리 창 추가하기</h3></center><br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit01.png"/>	
				<div class="span5">
					<br><br><br><br><br><br>
					<big><strong>Eclipse</strong>를 실행하고,<br>
							Git 작업공간을 추가하기 위해<br>
							오른쪽 상단부분의 <embed src="/resources/forweaver/img/perspectiveicon.png"/> 버튼(<strong>Open Perspective</strong>)<br>
							을 클릭하여 Perspective 창을 엽니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit02.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>목록에서 <strong>Git</strong>을 선택하고 Ok를 클릭합니다.<br><br>
					</big>
				</div>
			</div>
			<br>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit03.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						왼쪽에 <strong>Git Repository</strong> 메뉴가 생성된 것을<br>
						확인할 수 있습니다.<br><br>
						이제 포위버에서 생성한 프로젝트 공간을<br>
						이클립스에 불러오기 위해,<br>
						앞서 만들었던 프로젝트 페이지로 돌아가 봅니다.<br><br>
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
			<center><h3>프로젝트 저장소와 이클립스를 연동하기</h3></center><br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit04.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						오른쪽 상단을 보면, 프로젝트에 할당된<br>
						<strong>고유 저장소</strong>의 주소를 확인할 수 있습니다.<br><br>
						이 주소를 복사해서 이클립스로 돌아갑시다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit05.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>Git Repositories</strong> 창에서 <embed src="/resources/forweaver/img/clonegiticon.png"/>을 클릭하거나<br>
						가운데 팝업되어 있는 <embed src="/resources/forweaver/img/clonegiticon2.png"/>항목을 <br>클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit06.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						복사한 프로젝트 주소가 URL 부분에 입력되면<br>
						<strong>User</strong>와 <strong>Password</strong> 항목에<br>
						<strong>ForWeaver 아이디, 비밀번호</strong>를 입력하고<br>
						<strong>'Next'</strong>를 두 번 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit20.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>'Import all existing projects after clone finishes'</strong><br>
						에 체크표시하고<br><br>
						<strong>'Finish'</strong>를 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br><br><br>
			<center><h4>이제 내려받은 저장소의 <strong>상태</strong>를 확인해 봅니다.</h4></center>
			<center><h4>프로젝트가 업로드되어 있지 않아 저장소가 비어 있다면, 하단의 <strong>빈 저장소에 프로젝트 전체 업로드</strong>를 참고하세요.</h4></center>
			<center><h4>저장소에 프로젝트가 존재하고, 진행중이었다면, 하단의 <strong>진행중인 프로젝트에 코드 업로드</strong>를 참고하세요.</h4></center>
			<br><br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>처음 프로젝트를 진행할 때!</h3></center><br><br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br>
			<center><h4><big><strong>비어있는 저장소에 기존 프로젝트 연동하기</strong></big></h4></center>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit07.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>ForWeaver 저장소</strong>를 PC에 처음 연동한 상황입니다.<br><br>
						따라서 프로젝트 개설자가 기존에 진행하던 프로젝트를 저장소에 옮겨 
						업로드하게 되면, 프로젝트의 멤버들과 함께 코드를 관리할 수 있습니다.<br><br>
						연동된 저장소 위치의 폴더로 접근합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit08.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br>
					<big>
						프로젝트 내용을 모두 복사하여<br>
						<strong>.git</strong> 폴더가 존재하는 위치에 붙여넣습니다.<br><br>
						프로젝트가 정상적으로 복사되면<br>
						이클립스로 돌아옵니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit09.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						이제 <strong>Git Repository</strong> 창으로 돌아와<br>
						생성한 저장소에 마우스 오른쪽 클릭을 한 후, <br><br>
						<strong>Import Projects</strong>를 클릭하고<br>
						<strong>Import existing projects</strong>를 선택하여<br>
						방금 전 복사한 프로젝트를 저장소에 연동합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit10.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br>
					<big>
						프로젝트가 정상적으로 인식되었습니다.<br>
						<strong>'Finish'</strong>를 클릭합니다.<br><br>
						
						<strong>*</strong> 만약 <strong>'Finish'</strong>가 클릭되지 않는다면<br>
						  프로젝트가 이미 이클립스에 <strong>존재한다</strong>는 뜻이므로<br>
						  프로젝트 관리창에서 <strong>지우고</strong> 인식시키면 됩니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br>
			<center><h4><big><strong>프로젝트 업로드하기</strong></big></h4></center>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit11.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						이제 <strong>Git</strong> 명령어를 이용해 프로젝트 상태를 저장하고<br>
						중앙 저장소에 업로드해 보겠습니다.<br><br>
						사용자가 코드를 추가 및 수정하고 나서 저장하면,<br>
						<strong>Commit</strong>을 통해 소스코드를<br>
						사용자가 원하는 시점으로 저장할 수 있고,<br><br>
						<strong>Push</strong>로 Commit을 통해 저장한 사항을<br>
						ForWeaver에 업로드할 수 있습니다.<br><br>
						<strong>Commit</strong> 항목을 클릭합니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit12.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						처음 Commit이라면,<br>
						사용자의 <strong>기본정보 일부를 등록하는 절차</strong>를<br>거쳐야 합니다.<br><br>
						<strong>*</strong>ForWeaver에서 <strong>쓰고 있는 이메일</strong>을 입력해야<br>
						&nbsp;&nbsp;프로젝트에 본인 아이디로 기록되니<br>
						&nbsp;&nbsp;꼭 정확히 기입하세요.<strong>*</strong><br>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit13.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						프로젝트에서 추가된 사항,<br>
						수정된 사항, 삭제된 사항 등의<br>
						<strong>코드 저장 상황을 설명하는 메세지</strong>를 적은 후,<br><br>
						<strong>Commit and Push</strong> 버튼을 누릅니다.<br>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit14.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						처음 업로드 시 생성해야 할 항목이 있습니다.<br>
						(굳이 이해할 필요는 없고, 따라해 주시면 됩니다.)<br><br>
						첫화면은 <strong>'Next'</strong>로 넘긴 후<br>
						<strong>Source ref</strong>와 <strong>Defination ref</strong>를 <strong>master</strong>로 설정하고<br>
						<strong>Add Spec</strong>을 누릅니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit15.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>'master'</strong>로 <strong>Source</strong>와 <strong>Destination</strong>이 설정되었으면,<br><br>
						왼쪽 하단의 <strong>Save specification in 'origin' configuration</strong> 항목에 <strong>*체크*</strong>하고<br>
						<strong>'Finish'</strong>를 눌러 첫 업로드를 마칩니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit16.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br>
					<big>
						이클립스에서 <strong>Push</strong>가 성공적으로 수행되었습니다.<br><br>
						<strong>ForWeaver</strong>의 프로젝트 페이지로 돌아가 보겠습니다.<br>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit17.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br>
					<big>
						정상적으로 프로젝트가 <strong>업로드</strong>된 것을<br>확인할 수 있습니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>앞으로 프로젝트를 진행하는 방법!</h3></center><br><br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br><br>
			<center><h4><big><strong>*먼저 읽어야 할 사항</strong></big></h4></center>
			<br><br>
			<center><h4><strong>Git</strong>으로 프로젝트 진행을 하실 때에는,</h4></center>
			<center><h4><strong>Pull <i class="fa fa-arrow-right"></i> 코딩 작업 <i class="fa fa-arrow-right"></i> Commit <i class="fa fa-arrow-right"></i> Push</strong> 순으로 작업하는 것을 권장드립니다.</h4></center>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<center><h4><big><strong>Pull</strong><i class="fa fa-question"></i></big></h4></center>
			<center><h4>멤버들이 업로드한 코드를 모두 모아, <strong>ForWeaver에서 내려받고 적용</strong>하는 작업입니다.</h4></h3></center>
			<center><h4><strong>Git</strong>의 <strong>다운로드 기능</strong>이라고 보면 되며,</h4></center>
			<center><h4>Pull을 수행하게 되면 멤버가 어떤 코드를 <strong>추가</strong>하고 <strong>수정</strong>하였는지 <strong>최신 버전</strong>으로 볼 수 있고</h4></center>
			<center><h4>다른 멤버가 이전에 했던 <strong>작업</strong>과, 당신이 할 작업이 <strong>겹치지 않도록 미리 방지</strong>할 수 있습니다.</h4></center>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<center><h4><big><strong>Commit</strong><i class="fa fa-question"></i></big></h4></center>
			<center><h4>현재 PC에 존재하는 프로젝트에 당신이 한 <strong>코딩 작업을 저장</strong>합니다.</h4></center>
			<center><h4><strong>Git</strong>의 <strong>세이브(저장) 기능</strong>이라고 보면 됩니다.</h4></center>
			<center><h4>Commit을 수행하면 당신 아이디로 코드 작업한 것이 <strong>기록</strong>됩니다.</h4></center>
			<center><h4>또한 이렇게 저장한 코드 및 기록 <strong>덩어리</strong>는 Push를 통해 ForWeaver에 업로드할 수 있게 됩니다.</h4></center>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<center><h4><big><strong>Push</strong><i class="fa fa-question"></i></big></h4></center>
			<center><h4>Commit으로 저장한 코드와 메세지를 <strong>ForWeaver에 업로드</strong>합니다.</h4></center>
			<center><h4>(Commit때 적용했던 <strong>이메일</strong>을 통해 사용자가 식별됩니다.)</h4></center>
			<center><h4>이렇게 Push된 여러 내용은 <strong>프로젝트 멤버</strong>라면 Pull을 통해 <strong>언제라도 내려받을 수 있습니다.</strong></h4></center>
			<br><br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<center><h4>이 순서로 진행한다면, <strong>팀원과 작업이 겹치는 경우를 방지</strong>하고</h4></center>
			<center><h4><strong>꼼꼼하게</strong> 프로젝트를 진행할 수 있습니다.</h4></center>
			<center><h4>자세한 예시는 다음과 같습니다.</h4></center>
			<br><br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br><br>
			<center><h4><big><strong>수정사항을 ForWeaver상의 프로젝트에 적용하기</strong></big></h4></center>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit29.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						ForWeaver에 <strong>업로드되어 있는 프로젝트</strong>에<br>
						<strong>수정사항</strong>이 있는지 <strong>확인</strong>하기 위해<br>
						<strong>Pull</strong>을 수행합니다.<br><br>
						프로젝트에서 <strong>오른쪽 마우스</strong> 클릭 후<br>
						<strong>'Team' > 'Pull'</strong> 버튼을 클릭하세요.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit30.png"/>	
				<div class="span5">
					<big>
						메시지를 보면,<br>
						<strong>모든 사항이 최신 버전</strong>이라고 합니다.<br>
						(<strong>수정사항</strong>이 있다면 해당 창에서<br>
						수정된 <strong>내용</strong>을 알려줍니다.)<br><br><br>

						* 만약 <strong>Pull</strong>하기 전에 <strong>Commit</strong>해야 한다는<br>
						&nbsp;&nbsp;&nbsp;메시지가 뜨면서 <strong>Pull</strong>이 불가능해질 경우,<br>
						&nbsp;&nbsp;&nbsp;작업내용을 <strong>Commit</strong>하시거나<br>
						&nbsp;&nbsp;&nbsp;프로젝트를 <strong>새로</strong> 받으세요.<br><br>
						
						* 프로젝트를 <strong>삭제</strong>하고 다시 받는 방법은 하단의<br>
						&nbsp;&nbsp;&nbsp;<strong>소스 업로드 시 충돌사항 해결하기</strong>에 있습니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit22.png"/>	
				<div class="span5">
					<br><br><br>
					<big>
						이제 프로젝트에 코딩 작업을 수행하고,<br>
						해당 작업 내용을 <strong>Commit</strong>해 보겠습니다.<br><br>
						우선,<br>
						화면과 같이 <strong>소스 내용</strong>을 추가, 수정 및 삭제하게 되면<br>
						프로젝트 탐색기에&nbsp;&nbsp;<strong>></strong> 모양의<br>
						<strong>꺽쇠</strong>가 표시됩니다.<br><br>
						위 상태와 같이 변경된 사항이 있을 때에야만<br>
						<strong>코드 저장</strong>을 수행할 수 있습니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit23.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						프로젝트에서 <strong>오른쪽 마우스</strong> 클릭 후<br>
						<strong>'Team' > 'Commit'</strong> 버튼을 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit12.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						처음 Commit이라면,<br>
						사용자의 <strong>기본정보 일부를 등록하는 절차</strong>를<br>거쳐야 합니다.<br><br>
						<strong>*</strong>ForWeaver에서 <strong>쓰고 있는 이메일</strong>을 입력해야<br>
						&nbsp;&nbsp;프로젝트에 본인 아이디로 기록되니<br>
						&nbsp;&nbsp;꼭 정확히 기입하세요.<strong>*</strong><br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit24.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						프로젝트 변경사항을 적은 후<br>
						<strong>'Commit and Push'</strong> 버튼을 클릭하여<br>
						Commit과 Push를 한번에 수행합니다.<br><br>
						<strong>* Commit</strong> 여러번하고 <strong>Push</strong>로<br>
						&nbsp;&nbsp;&nbsp;한꺼번에 업로드할수도 있습니다.<br>
						&nbsp;&nbsp;&nbsp;(Push 버튼은 <strong>'Team' > 'Push To Upstream'</strong>)
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit25.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						업로드 완료 화면에서<br>
						<strong>변경사항</strong>과 <strong>등록된 메세지</strong>,<br>
						<strong>아이디</strong>를 확인할 수 있습니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit26.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						변경사항이 <strong>적용된</strong> 화면입니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>프로젝트 업로드가 실패할 때</h3></center><br><br>
			<br>
			<center><h4>버젼관리를 사용하다보면 충돌 문제가 발생하는데,<br>
			<a href="http://lapan.tistory.com/53" target="_blank">'Eclipse에서 Git Rebase 수행 및 충돌 해결 방법'</a> 이 글을 참조하시면 됩니다.<br><br></h4></center>
			<center><h4>하지만 초보자에게는 어려울 수 있으므로, </h4></center>
			<center><h4>프로젝트 저장소를 <strong>전체 삭제</strong>하고 다시 내려받아</h4></center>
			<center><h4>소스 <strong>재수정</strong> 후 <strong>업로드</strong>하는 과정을 추천드립니다.</h4></center>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit27.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>Git</strong> 저장소에 마우스 오른쪽 클릭 후<br>
						<strong>Delete Repository</strong>를 선택하세요.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/egit28.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>모두 선택</strong>해서 지우세요.
					</big>
				</div>
			</div><br>
			<br><br><br>
			<br>
			<center><h4>이후, 다시 저장소를 내려받아 <strong>수정하려던 사항을 적용</strong>하고 업로드하시면 됩니다.</h4></center>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<center><a href="/project/" 
					class="btn btn-primary">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;프로젝트 페이지로&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
			</center>
			<br>
			<!-- 
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			 -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>