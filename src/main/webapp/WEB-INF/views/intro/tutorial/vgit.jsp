<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<title>Forweaver : 포위버란 무엇인가?</title>
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
				<ul class="nav nav-tabs" id="myTab">
					<li><a href="/intro/tutorial/default">웹 업로드</a></li>
					<li><a href="/intro/tutorial/egit">이클립스</a></li>
					<li class="active"><a href="/intro/tutorial/vgit">비주얼 스튜디오</a></li>
				</ul>

			<center><h2>비주얼 스튜디오(2010)과 포위버 연동!</h2></center>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3><strong>비주얼 스튜디오</strong>에 <strong>Git</strong> 환경 구성</h3></center><br><br>
			<center><div class="row"><big>
				  <p>우선 <strong>비주얼 스튜디오</strong>는  <strong>Git</strong>을 2013부터 기본으로 설치되어 있습니다.</p>
				  <p>따라서 그 이전 버젼은 설치를 하셔야 합니다.</p>
				  <p>아래 <strong>두 가지</strong>의 설치파일을 <strong>모두 받아서 기본 설치</strong>하세요!</p><br>
				  <p><strong>Windows Git</strong> 설치 파일 : <a href="https://github.com/msysgit/msysgit/releases/download/Git-1.9.5-preview20150319/Git-1.9.5-preview20150319.exe">다운로드!</a></p>
				  <p><strong>Windows TortoiseGit</strong> 설치 파일 : <a href="http://download.tortoisegit.org/tgit/1.8.14.0/TortoiseGit-1.8.14.0-32bit.msi">32bit</a> / <a href="http://download.tortoisegit.org/tgit/1.8.14.0/TortoiseGit-1.8.14.0-64bit.msi">64bit</a></p>
			</big>
			</div>
            </center>

			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br>
			<center><h4>이제, <strong>비주얼 스튜디오</strong>에 <strong>Git</strong>을 연동해주는 <strong>확장 프로그램</strong>을 설치해 봅시다.</h4></center>
            <br><br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit01.png"/>	
				<div class="span5">
					<br><br><br><br><br><br>
					<big><strong>비주얼 스튜디오</strong>를 실행하고,<br>
							<strong>'도구' > '확장 관리자'</strong>를 클릭합니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit02.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>왼쪽 메뉴에서 <strong>'온라인 갤러리'</strong>를 선택한 후,<br>
					검색창에 <strong>'git provider'</strong> 입력 후 검색하게 되면<br>
					<strong>Git Source Control Provider</strong>를 보실 수 있습니다.<br><br>
					해당 확장 프로그램을 <strong>다운로드</strong>합니다.
					</big>
				</div>
			</div>
			<br>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit03.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>다운로드</strong>가 진행되고...
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit04.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>설치</strong>를 클릭하여 수행합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit05.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						작업이 끝나면, <strong>비주얼 스튜디오</strong>를 재시작합니다.
					</big>
				</div>
			</div>
			<br>
			<br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br>
			<center><h4><p><strong>확장 프로그램</strong>이 성공적으로 설치되었습니다.</p>
			<p>몇 가지 <strong>설정</strong>을 거치면 프로젝트 관리가 가능합니다.</p></h4></center>
            <br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit06.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>'도구' > '옵션'</strong>을 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit07.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>'Source Control' > '플러그인 선택'</strong>에 진입하여<br><br>
						소스 제어 플러그인으로<br>
						<strong>'Git Source Control Provider'</strong>를 선택합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit08.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>'Source Control' > 'Git Source Control Provider'</strong>에 진입하여<br><br>
						<strong>Path to Git</strong>에서 <strong>git.exe</strong>가 존재하는 폴더<br>
						(<strong>Git</strong> 설치 폴더에 있음)경로로 진입하여<br>
						실행 파일을 선택하여 설정하고,<br><br>
						<strong>TortoiseGit</strong>도 같은 방법으로 설정합니다.
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<br><br>
			<center><h4>이제 프로젝트를 관리할 준비가 <strong>완료</strong>되었습니다.</h4></center>
			<br>
			<div class="row">
				<div class="span12">
					<hr style="border-top: 3px dashed;"/>
				</div>
			</div>
			<br>
			<center><h3>프로젝트 저장소와 비주얼 스튜디오를 연동하기</h3></center><br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit10.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						프로젝트 페이지의 오른쪽 상단을 보면,<br>
						프로젝트의 <strong>고유 저장소</strong> 주소를 확인할 수 있습니다.<br><br>
						해당 주소를 복사합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit09.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br>
					<big>
						사용자가 프로젝트를 진행할 <strong>위치</strong>에서<br>
						마우스 오른쪽 클릭 후 <strong>'Git Clone'</strong>을 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit11.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br>
					<big>
						복사한 프로젝트 주소를 <strong>URL</strong> 부분에 입력하고<br>
						<strong>Load Putty Key</strong> 항목의 체크를 해제한 후<br>
						<strong>'OK'</strong>를 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit12.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>forweaver.com</strong>에 가입된 <strong>아이디</strong>를 입력합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit13.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br>
					<big>
						아이디에 맞는 <strong>비밀번호</strong>를 입력합니다.<br><br>
						(앞으로 여러번 하게 될 것, <br>그 때마다 매번 입력하면 됩니다.)
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit14.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br>
					<big>
						<strong>저장소 연동</strong>에 성공하여<br>
						프로젝트가 내려받아진 것을 볼 수 있습니다.
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
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit15.png"/>	
				<div class="span5">
					<br><br><br><br>
					<big>
						<strong>ForWeaver 저장소</strong>를 PC에 처음 연동한 상황입니다.<br>
						따라서 현재 폴더 안은 텅 빈 상태입니다.<br><br>
						<strong>기존에 진행하던 프로젝트를 업로드</strong>하기 위해<br>
						프로젝트 내용을 <strong>모두 복사</strong>하여<br>
						생성된 폴더 위치에 붙여넣습니다.<br><br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit16.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						복사한 프로젝트 폴더로 진입해<br>
						<strong>프로젝트 파일을 더블클릭</strong> 후<br>
						<strong>비주얼 스튜디오</strong>로 열어 다음 작업을 수행합니다.<br>
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
			<center><h4><big><strong>Git 도구로 프로젝트 업로드하기</strong></big></h4></center>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit17.png"/>	
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
				<embed class="span7" src="/resources/forweaver/img/visualgit18.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						<strong>1. 코드 저장 상황을 설명하는 메세지</strong>를 적은 후,<br><br>
						<strong>2. 'Set author'</strong>에 체크하고 <br>
						&nbsp;&nbsp;&nbsp;&nbsp;<strong>ForWeaver</strong>에 등록된 아이디와 이메일을<br>
						&nbsp;&nbsp;&nbsp;&nbsp;입력합니다.<br><br>
						<strong>3.</strong> 다음, <strong>화면에 표시된 'All'을 클릭</strong>해서<br>
						&nbsp;&nbsp;&nbsp;&nbsp;프로젝트 전체 파일을 업로드할 목록에 추가하고<br><br>
						<strong>4. 'OK'</strong>를 클릭합니다.<br><br><br><br><br><br>
						
						<strong>*</strong> ForWeaver에서 <strong>쓰고 있는 이메일</strong>을 입력해야<br>
						&nbsp;&nbsp;&nbsp;프로젝트에 본인 아이디로 기록되니<br>
						&nbsp;&nbsp;&nbsp;꼭 정확히 기입하세요.<br><br>
						
						<strong>* 파일 오류</strong>로 <strong>Commit</strong> 완료가 되지 않으면<br>
						&nbsp;&nbsp;&nbsp;그 파일만 제외하고 다시 시도하세요.<br>
						
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit19.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>Commit</strong>에 성공하면 <strong>Push</strong> 버튼을 누릅니다.<br>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit20.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						해당 화면에서 <strong>'OK'</strong>를 클릭하면...<br>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit21.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						VS에서 <strong>Push</strong>가 성공적으로 수행되었습니다.<br><br>
						<strong>ForWeaver</strong>의 프로젝트 페이지로 돌아가 보겠습니다.<br>
					</big>
				</div>
			</div>
			<br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit22.png"/>	
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
				<embed class="span7" src="/resources/forweaver/img/visualgit23.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br>
					<big>
						ForWeaver에 <strong>업로드되어 있는 프로젝트</strong>에<br>
						<strong>수정사항</strong>이 있는지 <strong>확인</strong>하기 위해<br>
						<strong>Pull</strong>을 수행하겠습니다.<br><br>
						프로젝트에서 <strong>오른쪽 마우스</strong> 클릭 후<br>
						<strong>'Git' > 'Sync'</strong> 버튼을 클릭하세요.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit24.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br>
					<big>
						<strong>Remote Branch</strong>에 <strong>'master'</strong>로 입력하고<br>
						<strong>Pull</strong>을 클릭합니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit25.png"/>	
				<div class="span5">
					<br><br>
					<big>
						해당 창의 메세지를 보면<br>
						<strong>진행사항</strong> 및 <strong>수정된 내용</strong>을 알려줍니다.<br>
						이제 <strong>'close'</strong>를 눌러 프로젝트 창으로 돌아오세요.<br><br><br>

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
				<embed class="span7" src="/resources/forweaver/img/visualgit26.png"/>	
				<div class="span5">
					<br><br><br>
					<big>
						이제 프로젝트에 코딩 작업을 수행하고,<br>
						해당 작업 내용을 <strong>Commit</strong>해 보겠습니다.<br><br>
						우선,<br>
						화면과 같이 <strong>코드 내용</strong>을 추가, 수정 및 삭제하게 되면<br>
						프로젝트 탐색기에 <i class="fa fa-check"></i>가 표시됩니다.<br><br>
						위 상태와 같이 변경된 사항이 있을 때에야만<br>
						<strong>코드 저장</strong>을 수행할 수 있습니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit27.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						프로젝트에서 <strong>오른쪽 마우스</strong> 클릭 후<br>
						<strong>'Git' > 'Commit'</strong> 버튼을 클릭합니다.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit28.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						프로젝트 변경사항을 적은 후<br>
						<strong>'OK'</strong> 버튼을 클릭합니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit29.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>'Push'</strong> 버튼을 클릭하여<br>
						Commit을 곧바로 업로드할수도 있고,<br>
						<strong>'ReCommit'</strong> 버튼으로<br>
						Commit 내역을 더 추가할 수도 있습니다.<br><br>
						<strong>* Commit</strong>을 여러 번 하게 되면 <strong>Push</strong>로<br>
						&nbsp;&nbsp;&nbsp;한꺼번에 저장 내역을 올릴 수도 있습니다.<br>
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit30.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br>
					<big>
						업로드가 <strong>성공적으로 완료</strong>되었습니다.<br>
					</big>
				</div>
			</div>
			<br><br>
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
			<a href="https://tortoisegit.org/docs/tortoisegit/tgit-dug-conflicts.html" target="_blank">'TortoiseGit으로 충돌 해결 방법'</a> 이 글을 참조하시면 됩니다.<br><br></h4></center>
			<center><h4>하지만 초보자에게는 어려울 수 있으므로, </h4></center>
			<center><h4>프로젝트 저장소를 <strong>전체 삭제</strong>하고 다시 내려받아</h4></center>
			<center><h4>소스 <strong>재수정</strong> 후 <strong>업로드</strong>하는 과정을 추천드립니다.</h4></center>
			<div class="row">
				<div class="span12">
					<hr/>
				</div>
			</div>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit31.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						업로드할 소스는 미리 백업해 두시고,<br>
						<strong>마우스 오른쪽 클릭 > <br>
						'Windows 탐색기에서 폴더 열기'</strong> 를 클릭하세요.
					</big>
				</div>
			</div>
			<br><br>
			<div class="row">
				<embed class="span7" src="/resources/forweaver/img/visualgit32.png"/>	
				<div class="span5">
					<br><br><br><br><br><br><br><br><br><br><br><br><br>
					<big>
						<strong>상위 폴더</strong>로 가서 <br>
						진행하던 프로젝트 폴더를 지우세요.
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
			
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>