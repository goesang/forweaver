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
					<!-- <li><a href="/weaver/"><i class="fa fa-twitter"></i>&nbsp;위버</a></li> -->
					<li><a href="/project/"><i class="fa fa-bookmark"></i>&nbsp;프로젝트</a></li>
					<li><a href="/code/"><i class="fa fa-rocket"></i>&nbsp;코드</a></li>
					<li><a href="/community/"><i class="fa fa-comments"></i>&nbsp;커뮤니티</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-flickr"></i>&nbsp;도움말</a>
							<ul class="dropdown-menu">
							
								<li><a href="/intro/forweaver"><i class="fa fa-question-circle"></i>&nbsp;&nbsp;기본 사용법</a></li>
								<li><a href='/intro/tutorial/default' ><i class="fa fa-git"></i>&nbsp;&nbsp;개발툴과 연동</a></li>
							</ul></li>
				</ul>

				<ul class="nav pull-right">
					<sec:authorize ifNotGranted="ROLE_USER, ROLE_ADMIN">
						<li><a href="<c:url value="/login?state=null" />"><i class="fa fa-user"></i>&nbsp;로그인</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_USER, ROLE_ADMIN">

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> 
							<img style="height:28px;width:28px;" src='<c:out value="${currentUser.getImgSrc()}" escapeXml="false"></c:out>' />&nbsp;&nbsp;${currentUser.username}
								<b class="caret"></b></a>
							<ul class="dropdown-menu">
							
								<li><a href="/"><i class="icon-white icon-home"></i>&nbsp;&nbsp;마이페이지</a></li>
								<li><a href='/edit' ><i class="icon-cog"></i>&nbsp;&nbsp;정보수정</a></li>
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
				<div title="태그를 입력하시고 나서 꼭 엔터키나 스페이스키를 누르시면 추가가 됩니다."  class="span10 tag-span">
					<input placeholder="태그를 입력해주세요!" 
						class="tagarea tagarea-full" id="tags-input" />
					<input name="tags" type="hidden" id="tag-hidden"/>

					<script>
					
					var move = true;

					$('#tags-input').tagsinput({
						  confirmKeys: [13, 32],
						  maxTags: 6,
						  maxChars: 30,
						  trimValue: true
					});
					$("#tag-hidden").val(getTagList(document.location.href));
					
					$.each(getTagList(document.location.href).split(","), function(index, value) { 
						  $('#tags-input').tagsinput('add',value);
					});
					
					$('#tags-input').on('itemAdded', function(event) {
						 if(event.item.indexOf("?") !=-1 || event.item.indexOf("#") !=-1 || 
								 event.item.indexOf(".") !=-1){
							 $('#tags-input').tagsinput('remove',event.item);
							 return;
						 }
						
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