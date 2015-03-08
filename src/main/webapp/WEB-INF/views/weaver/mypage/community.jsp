<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 소통해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">	
		$(function() {
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
					$('.tag-name').click(
							function() {
								var tagname = $(this).text();
								var exist = false;
								var tagNames = $("input[name='tags']").val();
								if (tagNames.length == 2)
									moveUserPage("${weaver.getId()}","[\"" + tagname + "\"]","");
								var tagArray = eval(tagNames);
								$.each(tagArray, function(index, value) {
									if (value == tagname)
										exist = true;
								});
								if (!exist){
									moveUserPage("${weaver.getId()}",tagNames.substring(0,
											tagNames.length - 1)
											+ ",\"" + tagname + "\"]","");
								}
							});
					
					$('#search-button').click(
							function() {
									var tagNames = $("input[name='tags']").val();
									if(tagNames.length == 2){
										alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
										return;
									}
									moveUserPage("${weaver.getId()}",tagNames,$('#post-title-input').val());							
							});
					
					var pageCount = ${postCount+1}/${number};
					pageCount = Math.ceil(pageCount);					
					var options = {
				            currentPage: ${pageIndex},
				            totalPages: pageCount,
				            pageUrl: function(type, page, current){

				                return "${pageUrl}"+page;

				            }
				        }

				        $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
		});

		
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header page-header-none">
			<alert></alert>
			<h5 style="text-align: center">
				<img style="height: 60px; width: 60px;" class="img-polaroid"
					src="${weaver.getImgSrc()}">
			</h5>
			<h5 style="text-align: center">

				<big><i class="fa fa-quote-left"></i> ${weaver.getSay()}
				 <i class="fa fa-quote-right"></i></big> <small>- ${weaver.getId()}</small>
			</h5>
			<div class="row">
				<div class="span12">
					<ul class="nav nav-tabs pull-left" id="myTab">
						<li id="age-desc"><a
							href="/${weaver.getId()}<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:age-desc/page:1">최신순</a></li>
						<c:if test="${massage == null }">
							<li id="push-desc"><a
								href="/${weaver.getId()}<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:push-desc/page:1">추천순</a></li>
						</c:if>
						<li id="repost-desc"><a
							href="/${weaver.getId()}<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:repost-desc/page:1">최신
								답변순</a></li>
						<li id="repost-many"><a
							href="/${weaver.getId()}<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:repost-many/page:1">많은
								답변순</a></li>
						<li id="age-asc"><a
							href="/${weaver.getId()}<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:age-asc/page:1">오래된순</a></li>
						<li id="repost-null"><a
							href="/${weaver.getId()}<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:repost-null/page:1">답변
								없는 글</a></li>
					</ul>
					<ul style="border-bottom: 0px;" class="nav nav-tabs  pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">  <button class="btn btn-mini" type="button">Mini button</button></a>
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
					</ul>
				</div>
					<div class="span11">
						<input name="title" id="post-title-input" class="title span11"
							placeholder="찾고 싶은 검색어나 쓰고 싶은 단문의 내용을 입력해주세요!" type="text" />
					</div>
					<div class="span1">
						<span> <a id='search-button'
							class="post-button btn btn-primary"> <i
								class="icon-search"></i>
						</a>
						</span>
					</div>
				<div class="span12">

						<table id="post-table" class="table table-hover">
							<tbody>
								<c:forEach items="${posts}" var="post">
									<tr>
										
										<td colspan="2" class="post-top-title"><a
											class="a-post-title" href="/community/${post.postID}"> <c:if
													test="${post.isLong()}">
													<i class=" icon-align-justify"></i>
												</c:if> <c:if test="${!post.isLong()}">
													<i class="fa fa-comment"></i>
												</c:if> &nbsp;${post.title}
										</a></td>
										<td class="td-button" rowspan="2"><c:if
												test="${post.kind == 2}">
												<a href="/community/${post.postID}/delete"> <span
													class="span-button"> <i class="fa fa-trash-o"></i>
														<p class="p-button">삭제</p>
												</span>
												</a>
											</c:if> <c:if test="${post.kind != 2}">
												<a href="/community/${post.postID}/push"> <span
													class="span-button"> ${post.push}
														<p class="p-button">추천</p>
												</span>
												</a>
											</c:if></td>
										<td class="td-button" rowspan="2"><a
											href="/community/${post.postID}"> <span
												class="span-button">${post.rePostCount}
													<p class="p-button">답변</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="post-bottom">
											${post.getFormatCreated()}</td>
										<td class="post-bottom-tag"><c:forEach
												items="${post.tags}" var="tag">
												<span
													class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('$')}">
										tag-massage
										</c:if>
										">${tag}</span>
											</c:forEach></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					<div class="text-center">
						<div id="page-pagination"></div>
					</div>
				</div>
			</div>
			<%@ include file="/WEB-INF/common/footer.jsp"%>
		</div>
	</div>
</body>


</html>