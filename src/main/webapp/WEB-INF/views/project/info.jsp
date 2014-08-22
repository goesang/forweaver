<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
		$(document)
				.ready(
						function() {
							$('#tags-input').textext()[0]
									.tags()
									.addTags(
											getTagList("/tags:<c:forEach items='${project.tags}' var='tag'>	${tag},</c:forEach>"));
						});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header page-header-none">
			<h5>
				<big><big> <c:if test="${!project.isForkProject()}">
							<i class="fa fa-bookmark"></i>
						</c:if> <c:if test="${project.isForkProject()}">
							<i class="fa fa-code-fork"></i>
						</c:if> ${project.name}
				</big></big> <small>${project.description}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/project/${project.name}/">브라우져</a></li>
					<li><a href="/project/${project.name}/commitlog">커밋</a></li>
					<li><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);"
						onclick="openWindow('/project/${project.name}/chat', 400, 500);">채팅</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
					<li class="active"><a href="/project/${project.name}/info">정보</a></li>
					
					<c:if test="${project.getChildProjects().size() > 0 && project.getCategory() != 2}">
						<li><a href="/project/${project.name}/cherry-pick">체리 바구니</a></li>
					</c:if>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/g/${project.name}.git"
						type="text" class="input-block-level">
				</div>
			</div>
			<div class="span8"></div>
			<div class="carousel span4">
				<ol class="carousel-indicators">
					<a href='/project/${project.name}/info'>
						<li class="active"></li>
					</a>
					<a href='/project/${project.name}/info:stream'>
						<li></li>
					</a>
					<a href='/project/${project.name}/info:frequency'>
						<li></li>
					</a>
				</ol>
			</div>
		</div>

		<div class="row">
			<div class="span12">
				<h4>프로젝트 정보 <small>${gitInfo.getEnd().getAuthorIdent().getWhen().toLocaleString()} ~ ${gitInfo.getStart().getAuthorIdent().getWhen().toLocaleString()}</small></h4>
				<div class="span4">
					<ul>
						<li><span class="label label-info">${gitInfo.getCommits()}</span>
							커밋들</li>
						<li><span class="label label-info">${gitInfo.getMerges()}</span>
							병합 커밋들</li>
						<li><span class="label label-info">${gitInfo.getAuthors().size()}</span>
							저자들</li>
						<li><span class="label label-info">${gitInfo.getCommitters().size()}</span>
							커미터들</li>

					</ul>
				</div>
				<div class="span4">
					<ul>
						
						<li><span class="label label-info">${gitInfo.getLinesAdded()}</span>
							라인 추가</li>
						<li><span class="label label-info">${gitInfo.getLinesEdited()}</span>
							라인 수정</li>
						<li><span class="label label-info">${gitInfo.getLinesDeleted()}</span>
							라인 삭제</li>
					</ul>
				</div>
				<div class="span3">
					<ul>
						<li><span class="label label-info">${gitInfo.getAdded()}</span>
							파일 추가</li>
						<li><span class="label label-info">${gitInfo.getModified()}</span>
							파일 수정</li>
						<li><span class="label label-info">${gitInfo.getDeleted()}</span>
							파일 삭제</li>
					</ul>
				</div>
			</div>
		</div>
		<hr>
		<h4>
			기여자들 <small>소스 코드를 수정한 사람들</small>
		</h4>
		<div class="row">
			<div class="span6">
				<h5>
					저자 <small>${gitInfo.getAuthors().size()}</small>
				</h5>
				<ul>
					<c:forEach items="${gitInfo.getAuthors()}" var="author">
						<li>${author}&nbsp;&nbsp;<span class="label label-success">${gitInfo.getAuthoredCommits(author)}</span></li>
					</c:forEach>
				</ul>
			</div>
			<div class="span6">
				<h5>
					커미터 <small>${gitInfo.getCommitters().size()}</small>
				</h5>
				<ul>
					<c:forEach items="${gitInfo.getCommitters()}" var="committer">
						<li>${committer}&nbsp;&nbsp;<span class="label label-success">${gitInfo.getCommittedCommits(committer)}</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="span6">
				<h5>
					라인 수정 <small>라인 수정 내역</small>
				</h5>
				<ul>
					<c:forEach items="${gitInfo.getAuthorLineImpacts()}" var="author">
						<li>${author}&nbsp;&nbsp;<span class="label label-success">+${gitInfo.getAuthorLineImpact(author).getAdd()}</span>
							<span class="label label-warning">${gitInfo.getAuthorLineImpact(author).getEdit()}</span>
							<span class="label label-important">-${gitInfo.getAuthorLineImpact(author).getDelete()}</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="span6">
				<h5>
					파일 수정 <small>파일 수정 내역</small>
				</h5>
				<ul>
					<c:forEach items="${gitInfo.getAuthorFileImpacts()}" var="author">
						<li>${author}&nbsp;&nbsp;<span class="label label-success">+${gitInfo.getAuthorFileImpact(author).getAdd()}</span>
							<span class="label label-warning">${gitInfo.getAuthorFileImpact(author).getEdit()}</span>
							<span class="label label-important">-${gitInfo.getAuthorFileImpact(author).getDelete()}</span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>