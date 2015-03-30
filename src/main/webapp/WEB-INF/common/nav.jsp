<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<sec:authentication property="principal" var="currentUser"/>
<h1>
	<a href="/">ForWeaver</a> <small>학생들을 위한 소셜 코딩!</small>
</h1>

<div id="forweaver-nav"class="navbar navbar-inverse">
	<div class="navbar-inner">
		<div class="container-fluid">
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li><a href="/weaver/"><i class="fa fa-twitter"></i>&nbsp;위버</a></li>
					<li><a href="/lecture/"><i class="fa fa-university"></i>&nbsp;강의</a></li>
					<li><a href="/project/"><i class="fa fa-bookmark"></i>&nbsp;프로젝트</a></li>
					<li><a href="/code/"><i class="fa fa-rocket"></i>&nbsp;코드</a></li>
					<li><a href="/community/"><i class="fa fa-comments"></i>&nbsp;커뮤니티</a></li>
					<li><a href="/forweaver"><i class="fa fa-flickr"></i>&nbsp;도움말</a></li>
					
				</ul>

				<ul class="nav pull-right">
					<sec:authorize ifNotGranted="ROLE_USER, ROLE_ADMIN">
						<li><a href="<c:url value="/login" />"><i class="fa fa-user"></i>&nbsp;로그인</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_USER, ROLE_ADMIN">

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> 
							<img style="height:28px;width:28px;" src='<c:out value="${currentUser.getImgSrc()}" escapeXml="false"></c:out>' />&nbsp;&nbsp;${currentUser.username}
								<b class="caret"></b></a>
							<ul class="dropdown-menu">
							
								<li><a href="/"><i class="icon-white icon-home"></i>&nbsp;&nbsp;개인화면</a></li>
								<li><a href='/${currentUser.username}/edit' ><i class="icon-cog"></i>&nbsp;&nbsp;정보수정</a></li>
								<li><a href="/community/tags:$${currentUser.username}"><i
										class="icon-envelope"></i>&nbsp;&nbsp;메세지함</a></li>
								<li class="divider"></li>
								<li><a href="<c:url value="/j_spring_security_logout" />">
										<i class="icon-off"></i>&nbsp;&nbsp;로그아웃
								</a></li>
							</ul></li>

					</sec:authorize>

				</ul>

				<!--/.nav-collapse -->
			</div>
			<div class="span11">
				<span id = "tag-addon" style="cursor:pointer;" class="span1 tag-addon"><i class="icon-white icon-tag"></i></span>
				<div title="태그를 입력하시고 나서 엔터키나 스페이스키를 누르시면 추가가 됩니다."  class="span10 tag-span">
					<input placeholder="태그를 입력해 보세요!" 
						class="tagarea tagarea-full" id="tags-input" />
					<input name="tags" type="hidden" id="tag-hidden"/>

						
					<script>
					
					var move = true;
					function ieVersion () {
						  var myNav = navigator.userAgent.toLowerCase();
						  return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
						}
					
					if (ieVersion() && ieVersion()<10) {	
						$(function() {$("#forweaver-nav").after(
								"<div class='alert'>이 사이트는 인터넷 익스플로러 10 버젼 부터 지원합니다! "+
								"<a href='http://windows.microsoft.com/ko-kr/internet-explorer/download-ie'>최신버젼</a>으로 업그레이드 "+
								"하시거나 <a href='http://www.google.co.kr/chrome/browser/desktop/'>크롬</a>"+
								"이나 <a href='http://www.mozilla.or.kr/ko/firefox/new/'>파이어폭스</a>로 이용해주세요!</div>");
					});
						}
					$('#tags-input').tagsinput({
						  confirmKeys: [13, 32],
						  maxTags: 6,
						  maxChars: 10
					});
					$("#tag-hidden").val(getTagList(document.location.href));
					
					$.each(getTagList(document.location.href).split(","), function(index, value) { 
						  $('#tags-input').tagsinput('add',value);
					});
					
					$('#tags-input').on('itemAdded', function(event) {
						$("#tag-hidden").val($("#tags-input").val());
						if(move)
							movePage($("#tags-input").val(),"");
						});
					
					$('#tags-input').on('itemRemoved', function(event) {
						$("#tag-hidden").val($("#tags-input").val());
						movePage($("#tags-input").val(),"");
						});
					
					$('#tags-input').tagsinput('focus');
					</script>
				</div>
			</div>
		</div>
	</div>
</div>