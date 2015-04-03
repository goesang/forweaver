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
			
			$("#repost-content").focus();
			
			$('#tags-input').textext()[0].tags().addTags(
					getTagList("/tags:<c:forEach items='${post.tags}' var='tag'>	${tag},</c:forEach>"));


		});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="row">
			<div class=" span12">
				<table id="post-table" class="table table-hover">
					<tbody>
						<tr>
							<td class="td-post-writer-img none-top-border" rowspan="2">
								<img src="${post.getImgSrc()}">
							</td>
							<td  colspan="2" class="post-top-title none-top-border">
							<a rel="external" class="a-post-title"	href="/community/tags:<c:forEach items='${post.tags}' var='tag'>${tag},</c:forEach>"> 
							<c:if test="${!post.isNotice()}">${cov:htmlEscape(post.title)}</c:if>
										<c:if test="${post.isNotice()}">${post.title}</c:if></a></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${post.push}
									<p class="p-button">추천</p>
							</span></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${post.getRePostCount()}
									<p class="p-button">답변</p>
							</span></td>
						</tr>
						<tr>
							<td class="post-bottom">
																		
								<b>${post.writerName}</b>
								${post.getFormatCreated()}
								</td>	
								<td class="post-bottom-tag">
									<c:forEach	items="${post.tags}" var="tag">
										<span title="태그를 클릭해보세요. 태그가 추가됩니다!"
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('$')}">
										tag-massage
										</c:if>
										">${tag}</span>
									</c:forEach>
								</td>

						</tr>
		
						<!-- 글내용 시작 -->
						<c:if test="${post.isLong()}">
														
							<tr>
								<td class="post-content post-content-max"colspan="5">
								<s:eval expression="T(com.forweaver.util.WebUtil).markDownEncoder(post.getContent())" /></td>
							</tr>

						</c:if>
						<!-- 글내용 끝 -->
					</tbody>
				</table>
				
		
				<!-- 답변에 관련된 테이블 시작-->
				
				<form id ="repost-form" action="/community/${post.postID}/${rePost.rePostID}/update"
					method="POST">

					<div style ="margin-left:0px" class="span11">
						<textarea name="content"
							id="repost-content" class="post-content span10"
							 placeholder="답변할 내용을 입력해주세요!">${rePost.content}</textarea>
					</div>
					<div class="span1">
						<span>
							<button type="submit" class="post-button btn btn-primary">
								<i class="fa fa-check"></i>
							</button>
						</span>
					</div>
				</form>			
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>