<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
<body>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
<script>
	
		SyntaxHighlighter.all();
		$('#tags-input').textext()[0].tags().addTags(
				getTagList("/tags:<c:forEach items='${project.tags}' var='tag'>${tag},</c:forEach>"));

		
	</script>
		<div class="page-header">
			<h5>
				<big><big><i class="fa fa-bookmark"></i> ${project.name}</big></big> 
				<small>${project.description}</small>
			</h5>
		</div>
		<div class="row">
			
			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/project/${project.name}/">프로젝트 브라우져</a></li>
					<li class="active" ><a href="/project/${project.name}/commitlog">커밋 내역</a></li>
					<li><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);" onclick="window.open('/project/${project.name}/chat','','width=400,height=500,top='+((screen.height-500)/2)+',left='+((screen.width-400)/2)+',location =no,scrollbars=no, status=no;');">채팅</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-link"></i></span> <input
						value="http://forweaver.com/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
			
			<div class="span12">
				<table class="table table-hover">
					<tbody>
						<tr>
							<td class="none-top-border td-post-writer-img" rowspan="2"><img
								src="${gitCommitLog.getImgSrc()}">
							</td>
							<td style="width: 710px;"
								class="none-top-border post-top-title-short">${fn:substring(gitCommitLog.shortMassage,0,50)}</td>
								
							<td class="none-top-border td-commitlog-button" rowspan="2">
								<a	href="/project/${project.name}/browser/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
										<span class="span-button"> <i
											style="zoom: 1.5; -moz-transform: scale(1.5);"
											class="icon-eye-open icon-white"></i>
											<p class="p-button">소스</p></span>
									</a>
									
								<a	href="/project:${project.name}-${fn:substring(gitCommitLog.commitLogID,0,8)}.zip"">
										<span class="span-button"> <i
											style="zoom: 1.5; -moz-transform: scale(1.5);"
											class="icon-circle-arrow-down icon-white"></i>
											<p class="p-button">다운</p></span>
									</a>									
							</td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${gitCommitLog.commiterName}</b>
								${gitCommitLog.getCommitDate()} &nbsp;&nbsp; <span
								style="cursor: text;" class="tag-commit tag-name">${gitCommitLog.commitLogID}</span>
							</td>

						</tr>

						<tr>
							<td style="border-top: 0px"></td>
							<td style="" colspan="3">${gitCommitLog.fullMassage}</td>
						</tr>
					</tbody>
				</table>
				<c:if test="${fn:length(gitCommitLog.diff)>0}">
					<pre id="source-code" class="span9 brush: diff"><c:out value="${gitCommitLog.diff}"></c:out></pre>
				</c:if>
			</div>
			<!-- .span9 -->

			<!-- .tabbable -->
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>