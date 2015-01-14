<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 소통해보세요!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp" %>
</head>
<body>	
	<div class="container">
		<%@ include file="/WEB-INF/views/common/nav.jsp" %>
		<div class="page-header">
			<h6>
				<i class=" fa fa-comments"></i> 소통해보세요!<small class="hidden-xs">  <small>프로젝트
					진행사항이나 궁금한 점들을 올려보세요!</small></small>
				<div style="margin-top: -10px" class="pull-right">

					<button class="btn btn-warning">
						<b><i class="fa fa-database"></i> ${postCount}</b>
					</button>

				</div>
			</h6>
		</div>
		<div class="row">
			<div class="col-md-12">
				<ul class="nav nav-tabs" id="myTab">
					<li id="age-desc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<c:if test="${massage == null }">
						<li id="push-desc"><a
							href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-desc/page:1">추천순</a></li>
					</c:if>
					<li id="repost-desc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1">최신
							답변순</a></li>
					<li id="repost-many"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1">많은
							답변순</a></li>
					<li id="age-asc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>
					<li id="repost-null"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-null/page:1">답변
							없는 글</a></li>
				</ul>
			</div>

			<form id="post-form" onsubmit="return checkPost()"
				action="/community/add" enctype="multipart/form-data" METHOD="POST">

				 <div class="form-group col-md-9 col-lg-10">
				    <input class="form-control col-md-12" id="post-title-input" name="title"
										placeholder="찾고 싶은 검색어나 쓰고 싶은 단문의 내용을 입력해주세요!" type="text"/>
				  </div>
				<div class="col-md-3 col-lg-2 pull-right">
					<span> <a id='search-button'
						class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> <a id="show-content-button" href="javascript:showPostContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> <a style="display: none;" id="hide-content-button"
						href="javascript:hidePostContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a>
						<button id='post-ok' class="post-button btn btn-danger">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
				<div class="col-md-12">
					<textarea style="display: none;" id="post-content-textarea" name="content"
						class="post-content col-md-12" onkeyup="textAreaResize(this)"
						placeholder="글 내용을 입력해주세요!"></textarea>
						<div class="file-div"></div>
				</div>
			</form>

			<div class="col-md-12">

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
										test="${post.kind == 3}">
										<a href="/community/${post.postID}/delete"> <span
											class="span-button"> <i class="fa fa-trash-o"></i>
												<p class="p-button">삭제</p>
										</span>
										</a>
									</c:if> <c:if test="${post.kind <= 2}">
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
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>

</body>


</html>