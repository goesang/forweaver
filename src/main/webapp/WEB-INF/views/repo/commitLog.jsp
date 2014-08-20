<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>${repo.lectureName}/${repo.name} ~ ${repo.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<body>
	<script>
		$(document).ready(function() {
			$("select").selectpicker({
				style : 'btn-primary',
				menuStyle : 'dropdown-inverse'
			});
			$("#selectBranch").change(function() {
				if ($("#selectBranch option:selected").val() != "체크아웃한 브랜치 없음")
					window.location = $("#selectBranch option:selected").val();
			});
		
			var pageCount = ${gitCommitListCount}/10;
			if(pageCount < 1 ) 
				pageCount = 1;
			
			var options = {
		            currentPage: ${pageIndex},
		            totalPages: pageCount,
		            pageUrl: function(type, page, current){
		                return "/lecture/${repo.lectureName}/${repo.name}/commitlog/commit:${fn:replace(selectBranch,'.', ',')}/page:"+page;
		            }
		        }

		        $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
			

		});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header page-header-none">
			<h5>
				<big><big><i class="fa fa-bomb"></i> ${repo.name}</big></big> 
				<small>${repo.description}</small>
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
				<div class="span9">
					<h4 style="margin: 10px 0px 0px 0px"><i class="fa fa-info-circle"></i> 커밋 목록</h4>
				</div>
				<select id="selectBranch" class="span3">
					<option value="/lecture/${repo.lectureName}/${repo.name}/commitlog/commit:${fn:replace(selectBranch,'.', ',')}">${selectBranch}</option>
					<c:forEach items="${gitBranchList}" var="gitBranchName">
						<option value="/lecture/${repo.lectureName}/${repo.name}/commitlog/commit:${fn:replace(gitBranchName,'.', ',')}">${gitBranchName}</option>
					</c:forEach>
				</select>
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${gitCommitList}" var="gitCommit">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${gitCommit.getImgSrc()}">
								</td>
								<td style="width: 710px;" class="post-top-title-short"><a
									class="none-color"
									href="/lecture/${repo.lectureName}/${repo.name}/commitlog-viewer/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										${fn:substring(gitCommit.shortMassage,0,50)}</a></td>
								<td class="td-commitlog-button" rowspan="2">
								<a	href="/lecture/${repo.lectureName}/${repo.name}/browser/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										<span class="span-button"> <i class="fa fa-eye"></i>
											<p class="p-button">전체</p></span>
									</a>
								<a	href="/lecture/${repo.lectureName}/${repo.name}/${gitCommit.commitLogID}/${repo.lectureName}-${repo.name}.zip">
										<span class="span-button"> <i class="fa fa-arrow-circle-o-down"></i>
											<p class="p-button">다운</p></span>
									</a>									
								</td>

							</tr>
							<tr>
								<td class="post-bottom"><b>${gitCommit.commiterName}</b>
									${gitCommit.getCommitDate()} &nbsp;&nbsp; <span
									style="cursor: text;" class="tag-commit tag-name">${gitCommit.commitLogID}</span>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class = "text-center">
					<div id="page-pagination"></div>
				</div>
			</div>
			<!-- .span9 -->

			<!-- .tabbable -->
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>