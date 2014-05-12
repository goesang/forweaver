<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>Forweaver : 소통해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">
		function showPostContent() {
			$('#post-pagination').hide();
			$('#repost-table').hide();
			$('#post-content-textarea').fadeIn('slow');
			$('#show-content-button').hide();
			$('#hide-content-button').show();
		}

		function hidePostContent() {
			$('#post-pagination').show();
			$('#repost-table').show();
			$('#post-content-textarea').hide();
			$('#show-content-button').show();
			$('#hide-content-button').hide();
		}
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header">
			<h5>
				<big><big>소통해보세요!</big></big> <small>프로젝트 진행사항이나 궁금한 점들을
					올려보세요!</small>
			</h5>
		</div>
		<div class="row">
			<div class=" span12">
				<table id="post-table" class="table table-hover">
					<tbody>
						<tr>
							<td class="td-post-writer-img none-top-border" rowspan="2">
								<img
								src="${post.getImgSrc()}">
							</td>
							<td class="post-top-title none-top-border">${post.title}</td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${post.push}
									<p class="p-button">추천</p>
							</span></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${rePosts.size()}
									<p class="p-button">답변</p>
							</span></td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${post.writerName}</b>
								${post.getFormatCreated()} &nbsp;&nbsp; <c:forEach
									items="${post.tags}" var="tag">
									<span class="tag-name 
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('$')}">
										tag-massage
										</c:if>
									">${tag}</span>
								</c:forEach></td>
						</tr>
						<!-- 글내용 시작 -->
						<c:if test="${post.isLong()}">
							<tr>
								<td style="border-top: 0px"></td>
								<td style= "post-top-title" colspan="3">${post.content}</td>
							</tr>
						</c:if>
						<!-- 글내용 끝 -->
					</tbody>
				</table>
				<form action="/community/${post.postID}/add-repost"
					method="POST">
					<div class="span9">
						<input class="title span9" placeholder="글 내용을 입력해주세요!"
							name="title" type="text" value="" />
					</div>
					<div class="span2">
						<span> <a id="show-content-button"
							href="javascript:showPostContent();"
							class="post-button btn btn-primary"> <i
								class="icon-edit icon-white"></i>
						</a> <a style="display: none;" id="hide-content-button"
							href="javascript:hidePostContent();"
							class="post-button btn btn-primary"> <i
								class="icon-edit icon-white"></i>
						</a>
							<button type="submit" class="post-button btn btn-primary">
								<i class="icon-ok icon-white"></i>
							</button>
						</span>
					</div>
					<div class="span11">
						<textarea name="content" style="display: none;"
							id="post-content-textarea" class="post-content"
							onkeyup="textAreaResize(this)" placeholder="글 내용을 입력해주세요!"></textarea>
					</div>
				</form>
				<!-- 답변에 관련된 테이블 시작-->
					<table id="repost-table" class="table table-hover">
						<tbody>
						<c:forEach items="${rePosts}" var="rePost">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${rePost.getImgSrc()}">
								</td>
								<td class="repost-top-title">${rePost.title}</td>
								<td class="td-button" rowspan="2"><span class="span-button">${rePost.push}
										<p class="p-button">추천</p>
								</span></td>
							</tr>
							<tr>
								<td class="post-bottom"><b>${rePost.writerName}</b>
									${rePost.getFormatCreated()}</td>
							</tr>
							<!-- 글내용 시작 -->
							<c:if test="${rePost.isLong()}">
								<tr>
									<td style="border-top: 0px"></td>
									<td colspan="3">${rePost.content}</td>
								</tr>
							</c:if>
							<!-- 글내용 끝 -->
							</c:forEach>
						</tbody>
					</table>
					<!-- 답변에 관련된 테이블 끝-->
			
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>