<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
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
									moveUserPage("/${weaver.getId()}/","[\"" + tagname + "\"]","");
								var tagArray = eval(tagNames);
								$.each(tagArray, function(index, value) {
									if (value == tagname)
										exist = true;
								});
								if (!exist){
									moveUserPage("/${weaver.getId()}/",tagNames.substring(0,
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
									moveUserPage("/${weaver.getId()}/",tagNames,$('#post-title-input').val());							
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
		<div class="pull-right">

			<button class="btn btn-warning">
				<b><i class="fa fa-database"></i> ${postCount}</b>
			</button>

		</div>
		<div class="page-header page-header-none">
			<alert></alert>
			<h5 style="margin-left: 50px;text-align: center">
				<img style="height: 60px; width: 60px;" class="img-polaroid"
					src="${weaver.getImgSrc()}">
			</h5>
			<h5 style="text-align: center">

				<big><i class="fa fa-quote-left"></i> ${weaver.getSay()} <i
					class="fa fa-quote-right"></i></big> <small>- ${weaver.getId()}</small>
			</h5>

		</div>

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

				<div class="navbar navbar-inverse">
					<ul style="border-bottom: 0px;" class="nav  pull-right">
						<li class="dropdown">
							<button class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">
								<span style="font-size: 14px;"> <i
									class=" fa fa-comments"></i>&nbsp;커뮤니티
								</span> <b class="caret"></b>
							</button>
							<ul class="dropdown-menu">
								<li><a href="/${weaver.getId()}/project"><i
										class=" fa fa-bookmark"></i>&nbsp;&nbsp;프로젝트</a></li>
								<li><a href="/${weaver.getId()}/lecture"><i
										class=" fa fa-university"></i>&nbsp;&nbsp;강의</a></li>
								<li><a href="/${weaver.getId()}/code"><i
										class=" fa fa-rocket"></i>&nbsp;&nbsp;코드</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<div class="span11">
				<input name="title" id="post-title-input" class="title span11"
					placeholder="찾고 싶은 검색어를 입력해주세요!" type="text" />
			</div>
			<div class="span1">
				<span> <a id='search-button'
					class="post-button btn btn-primary"> <i class="icon-search"></i>
				</a>
				</span>
			</div>
			<div class="span12">
				<table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items="${posts}" var="post">
							<tr>
							<td class="td-post-writer-img" rowspan="2"><a
									href="/${post.writerName}"> <img src="${post.getImgSrc()}"></a></td>
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
									href="/community/${post.postID}"> <span class="span-button">${post.rePostCount}
											<p class="p-button">답변</p>
									</span></a></td>
							</tr>
							<tr>
								<td class="post-bottom"><a href="/${post.writerName}"><b>${post.writerName}</b></a>
									${post.getFormatCreated()}</td>
								<td class="post-bottom-tag"><c:forEach items="${post.tags}"
										var="tag">
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

</body>


</html>