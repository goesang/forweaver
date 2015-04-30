<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 소통해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
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
		SyntaxHighlighter.all();
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="row">
			<div class=" span12">
				<table id="post-table" class="table table-hover">
					<tbody>
						<tr>
							<td class="td-post-writer-img none-top-border" rowspan="2">
								<img src="${code.getImgSrc()}">
							</td>
							<td colspan="2" class="post-top-title none-top-border"><a
								rel="external" class="a-post-title"
								href="/code/tags:<c:forEach items='${code.tags}' var='tag'>${tag},</c:forEach>">
									<i class="fa fa-download"></i>&nbsp;${cov:htmlEscape(code.name)} -
									${cov:htmlEscape(code.content)}
							</a></td>
							<td class="td-button none-top-border" rowspan="2"><a
								href="/code/${code.codeID}/${cov:htmlEscape(code.name)}.zip"> <span
									class="span-button"> ${code.downCount}
										<p class="p-button">다운</p>
								</span></a></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${rePosts.size()}
									<p class="p-button">답변</p>
							</span></td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${code.writerName}</b>
								${code.getFormatCreated()}</td>
							<td class="post-bottom-tag"><c:forEach items="${code.tags}"
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
								<c:if test="${code.writerName==currentUser}">	
								<div class="function-div pull-right">
									<a onclick="return confirm('정말로 삭제하시겠습니까?');"
										href="/code/${code.codeID}/delete"> <span
										class="function-button">삭제</span></a>
								</div>
								</c:if>
								</td>

						</tr>
						<c:forEach items="${code.codes}" var="simpleCode" varStatus="status">
							<tr>
								<td colspan="5"><span
									onclick="javascript:hideAndShowSourceCode(${status.count})"
									class="function-button function-file"> <i
										class='icon-file icon-white'></i> ${simpleCode.fileName}
								</span>
								<a href="${simpleCode.fileName}">
									<span class="function-button" title='파일 다운로드'> <i class='icon-file icon-white'></i> 다운로드
									</span>
								</a>
								</td>
							</tr>
							
							<tr>
								<td id="td-code-${status.count}" class="well-white " style="padding-top: 20px; max-width: 480px;"
									colspan="5">
									
									<c:if test="${status.count > 5}" >style='display:none;'</c:if>
									
									<c:if test="${!simpleCode.fileName.endsWith('.md')}">
									
										<c:if test="${!simpleCode.isImgFile()}">
											<pre id="code-${status.count}">${cov:htmlEscape(simpleCode.getContent())}</pre>
										</c:if>
										<c:if test="${simpleCode.isImgFile()}">
											<img src="/code/${code.codeID}/${simpleCode.fileName}">
										</c:if>
										
									</c:if>
									
									<c:if test="${simpleCode.fileName.endsWith('.md')}">
										<div id="code-${status.count}"><s:eval expression="T(com.forweaver.util.WebUtil).markDownEncoder(simpleCode.getContent())" /></div>
									</c:if>
									</td>
							</tr>
							
							
							
						</c:forEach>
					</tbody>
				</table>


				<!-- 답변에 관련된 테이블 시작-->

				<form enctype="multipart/form-data" id="repost-form"
					action="/code/${code.codeID}/add-repost" method="POST">

					<div style="margin-left: 0px" class="span11">
						<textarea style="height:250px"  name="content" id="repost-content"
							class="post-content span10" 
							placeholder="답변할 내용을 입력해주세요!(직접적인 html 대신 마크다운 표기법 사용가능)">${rePost.content}</textarea>
					</div>
					<div class="span1">
						<span>
							<sec:authorize access="isAnonymous()">
						<button disabled="disabled" type="submit" class="post-button btn btn-primary" title='로그인을 하셔야 답변을 달 수 있습니다!'>
								<i class="fa fa-check"></i>
							</button>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<button type="submit" class="post-button btn btn-primary" title='답변 작성하기'>
								<i class="fa fa-check"></i>
							</button>
					</sec:authorize>
						</span>
					</div>
				</form>

				<!-- 답변에 관련된 테이블 끝-->

			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>
