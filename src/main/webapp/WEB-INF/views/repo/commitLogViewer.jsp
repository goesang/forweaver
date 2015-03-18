<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>${repo.lectureName}/${repo.name}~${cov:htmlEscape(repo.description)}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
<body>
	<script>
		SyntaxHighlighter.all();
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header page-header-none">
			<h5>
				<big><big><i class="fa fa-bomb"></i> ${repo.name}</big></big> 
				<small>${cov:htmlEscape(repo.description)}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/lecture/${repo.lectureName}/repo">돌아가기</a></li>
					<li><a href="/lecture/${repo.lectureName}/${repo.name}/browser">소스목록</a></li>
					<li class="active"><a href="/lecture/${repo.lectureName}/${repo.name}/commitlog">커밋내역</a></li>
					<c:if test="${repo.getCategory() == 2}">
						<li><a onclick="return confirm('정말 팀프로젝트로 포크하시겠습니까?')"
						href="/lecture/${repo.lectureName}/${repo.name}/fork">포크</a></li>
					</c:if>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/g/${repo.lectureName}/${repo.name}.git" type="text"
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
								<a
								href="/lecture/${repo.lectureName}/${repo.name}/browser/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
									<span class="span-button"> <i
										
										class="fa fa-eye"></i>
										<p class="p-button">전체</p></span>
							</a> <a href="/lecture/${repo.lectureName}/${repo.name}/${gitCommitLog.commitLogID}/${repo.lectureName}-${repo.name}.zip">
									<span class="span-button"> <i
										
										class="fa fa-arrow-circle-o-down"></i>
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
								<td style="font-size:13px;" colspan="3">${cov:htmlEscape(gitCommitLog.fullMassage)}</td>
						</tr>
						<c:if test="${gitCommitLog.getNote().length() > 0}">
						<tr>
							<td style="border-top: 0px"></td>
							<td style="font-size:13px;" colspan="3">
							 <span class="label label-warning"><i class="fa fa-book"></i> 노트:</span> 
    						${cov:htmlEscape(gitCommitLog.getNote())}</td>
						</tr>
						</c:if>
					</tbody>
				</table>
				<c:if test="${fn:length(gitCommitLog.diff)>0}">
					<div style="padding-top:30px;" class="well-white">
					<pre id="source-code" class="span9 brush: diff">${cov:htmlEscape(gitCommitLog.diff)}</pre>
				</div>
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