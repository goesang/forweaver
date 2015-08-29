<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>

<body>
	<div data-role="page">
		<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<h1>프로젝트 정보</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div style="font-size:12px"data-role="content">
			
				<h5>프로젝트 기간 <br>
				<small>${gitInfo.getEnd().getAuthorIdent().getWhen().toLocaleString()} ~ ${gitInfo.getStart().getAuthorIdent().getWhen().toLocaleString()}</small></h5>
				<div class="row">
				<div class="span3">
					<ul>
						<li><span class="label label-info" title="라인">${gitInfo.getCommits()}</span>
							커밋들</li>
						<li><span class="label label-info" title="라인">${gitInfo.getMerges()}</span>
							병합 커밋들</li>
						<li><span class="label label-info" title="라인">${gitInfo.getAuthors().size()}</span>
							저자들</li>
						<li><span class="label label-info" title="라인">${gitInfo.getCommitters().size()}</span>
							커미터들</li>

					</ul>
				</div>
				<div class="span3">
					<ul>
						
						<li><span class="label label-info" title="라인">${gitInfo.getLinesAdded()}</span>
							라인 추가</li>
						<li><span class="label label-info" title="라인">${gitInfo.getLinesEdited()}</span>
							라인 수정</li>
						<li><span class="label label-info" title="라인">${gitInfo.getLinesDeleted()}</span>
							라인 삭제</li>
					</ul>
				</div>
				<div class="span3">
					<ul>
						<li><span class="label label-info" title="라인">${gitInfo.getAdded()}</span>
							파일 추가</li>
						<li><span class="label label-info" title="라인">${gitInfo.getModified()}</span>
							파일 수정</li>
						<li><span class="label label-info" title="라인">${gitInfo.getDeleted()}</span>
							파일 삭제</li>
					</ul>
				</div>
			</div>
		<hr>
		<h5>
			기여자들 <small>소스 코드를 수정한 사람들</small>
		</h5>
		<div class="row">
			<div class="span3">
				<h6>
					저자 <small>${gitInfo.getAuthors().size()}</small>
				</h6>
				<ul>
					<c:forEach items="${gitInfo.getAuthors()}" var="author">
						<li>${author}&nbsp;&nbsp;<span class="label label-success" title="라인">${gitInfo.getAuthoredCommits(author)}</span></li>
					</c:forEach>
				</ul>
			</div>
			<div class="span3">
				<h6>
					커미터 <small>${gitInfo.getCommitters().size()}</small>
				</h6>
				<ul>
					<c:forEach items="${gitInfo.getCommitters()}" var="committer">
						<li>${committer}&nbsp;&nbsp;<span class="label label-success" title="라인">${gitInfo.getCommittedCommits(committer)}</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="span3">
				<h6>
					라인 수정 <small>라인 수정 내역</small>
				</h6>
				<ul>
					<c:forEach items="${gitInfo.getAuthorLineImpacts()}" var="author">
						<li>${author}&nbsp;&nbsp;<span class="label label-success" title="라인">+${gitInfo.getAuthorLineImpact(author).getAdd()}</span>
							<span class="label label-warning" title="라인">${gitInfo.getAuthorLineImpact(author).getEdit()}</span>
							<span class="label label-important" title="라인">-${gitInfo.getAuthorLineImpact(author).getDelete()}</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="span3">
				<h6>
					파일 수정 <small>파일 수정 내역</small>
				</h6>
				<ul>
					<c:forEach items="${gitInfo.getAuthorFileImpacts()}" var="author">
						<li>${author}&nbsp;&nbsp;<span class="label label-success" title="라인">+${gitInfo.getAuthorFileImpact(author).getAdd()}</span>
							<span class="label label-warning" title="라인">${gitInfo.getAuthorFileImpact(author).getEdit()}</span>
							<span class="label label-important" title="라인">-${gitInfo.getAuthorFileImpact(author).getDelete()}</span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
				
		</div>

	</div>
</body>
</html>