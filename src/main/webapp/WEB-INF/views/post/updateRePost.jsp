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
			move = false;
			<c:forEach items='${post.tags}' var='tag'>
			$('#tags-input').tagsinput('add',"${tag}");
			</c:forEach>
			move = true;
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
								<a href="/${post.writerName}"><img src="${post.getImgSrc()}"></a>
							</td>
							<td colspan="2" class="post-top-title none-top-border">
									<c:if test="${!post.isNotice()}">
									<s:eval expression="T(com.forweaver.util.WebUtil).addLink(post.title)" /></td>
									</c:if>
									<c:if test="${post.isNotice()}">
									${post.title}
									</c:if></td>
							<td class="td-button none-top-border" rowspan="2">
							<a onclick="return confirm('정말로 추천하시겠습니까?');" href="/community/${post.postID}/push">
							<span class="span-button">${post.push}
										<p class="p-button">추천</p>
								</span></a></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${post.rePostCount}
									<p class="p-button">답변</p>
							</span></td>
						</tr>
						<tr>
							<td class="post-bottom"><a href="/${post.writerName}"> <b>${post.writerName}</b></a>
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
								</c:forEach>
								</td>

						</tr>
						<c:if test="${post.datas.size() > 0}">
							<tr>
								<td class ="none-top-border" colspan="5"><c:forEach var="index" begin="0"
										end="${post.datas.size()-1}">
										<a href='/data/${post.datas.get(index).getId()}/${post.datas.get(index).getName()}'><span
											class="function-button function-file" title='파일 다운로드'><i
												class='icon-file icon-white'></i>
												${post.datas.get(index).getName()}</span></a>
									</c:forEach></td>
							</tr>
						</c:if>
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

				<form enctype="multipart/form-data" id="repost-form"
					action="/community/${post.postID}/${rePost.rePostID}/update" method="POST">

					<div style="margin-left: 0px" class="span11">
						<textarea style="height:250px" name="content" id="repost-content"
							class="post-content span10" 
							placeholder="답변할 내용을 입력해주세요!(직접적인 html 대신 마크다운 표기법 사용가능)">${rePost.content}</textarea>
					</div>
					<div class="span1">
						<span>
						<button type="submit" class="post-button btn btn-primary" title='답변 작성하기'>
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
