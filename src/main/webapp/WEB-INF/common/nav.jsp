<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<sec:authentication property="principal" var="currentUser"/>
<h1>
	<a href="/">ForWeaver</a> <small>학생들을 위한 소셜 코딩!</small>
</h1>

<div class="navbar navbar-inverse">
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
					<sec:authorize ifNotGranted="ROLE_USER">
						<li><a href="<c:url value="/login" />"><i class="fa fa-user"></i>&nbsp;로그인</a></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_USER">

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> 
							<img style="height:28px;width:28px;" src='<c:out value="${currentUser.getImgSrc()}" escapeXml="false"></c:out>' />&nbsp;&nbsp;${currentUser.username}
								<b class="caret"></b></a>
							<ul class="dropdown-menu">
							
								<li><a href="/"><i class="icon-white icon-home"></i>&nbsp;&nbsp;개인화면</a></li>
								<li><a href="javascript:void(0);" onclick="openWindow('/${currentUser.id}/edit', 360, 500);"><i class="icon-cog"></i>&nbsp;&nbsp;정보수정</a></li>
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
				<div class="span10 tag-span">
					<textarea placeholder="태그를 입력해 보세요!" name="tags"
						class="tagarea tagarea-full" id="tags-input" rows="1"></textarea>
					<script>
					
					
						$('#tags-input').textext({
							plugins : 'tags',
						});
						
						$('#tags-input').textext()[0].tags().addTags(
								getTagList(document.location.href));


						$('#tag-addon').click(function(){
							movePage($("input[name='tags']").val(),"");
						});

						$('#tags-input').keydown(
								function(e) {
									if (e.keyCode == 13) {
										$('#tags-input').textext()[0].tags().removeTag($('#tags-input').val());
									}
								});
						$('#tags-input').keyup(
								function(e) {
									if(e.keyCode == 32){
										var tagArray = new Array();
										var taginput = $('#tags-input').val();
										taginput = taginput.substring(0,taginput.length-1);
										
										tagArray.push(taginput);
										$('#tags-input').textext()[0].tags().removeTag(taginput);
										$('#tags-input').textext()[0].tags().addTags(tagArray);
										$('#tags-input').val('');
										
										movePage($("input[name='tags']").val(),"");
										
									}
									else if (e.keyCode == 13) {
										movePage($("input[name='tags']").val(),"");
									}
								});
					</script>
				</div>
			</div>
		</div>
	</div>
</div>