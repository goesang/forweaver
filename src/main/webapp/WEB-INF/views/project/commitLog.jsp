<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
</head>
<body>
	<script>
		$(document).ready(function() {
			$('#tags-input').textext()[0].tags().addTags(
					getTagList("/tags:<c:forEach items='${project.tags}' var='tag'>	${tag},</c:forEach>"));

			
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

		                return "/project/${project.name}/commitlog/commit:${fn:replace(selectBranch,'.', ',')}/page:"+page;

		            }
		        }

		        $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
			
		});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<h5>
				<big><big><i class="fa fa-bookmark"></i> ${project.name}</big></big> 
				<small>${project.description}</small>
			</h5>
		</div>
			<!-- .span3 -->
			<div class ="row">

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


			<div class="span12 row">
				<div class="span9">
					<h4 style="margin: 10px 0px 0px 0px"><i class="fa fa-info-circle"></i>  커밋 내역 목록</h4>
				</div>
				<select id="selectBranch" class="span3">
					<option value="/project/${project.name}/commitlog/commit:${fn:replace(selectBranch,'.', ',')}">${ selectBranch}</option>
					<c:forEach items="${gitBranchList}" var="gitBranchName">
						<option value="/project/${project.name}/commitlog/commit:${fn:replace(gitBranchName,'.', ',')}">${ gitBranchName}</option>
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
									href="/project/${project.name}/commitlog-viewer/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										${fn:substring(gitCommit.shortMassage,0,45)}</a></td>
								
								<td class="td-commitlog-button" rowspan="2">
								<a	href="/project/${project.name}/browser/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										<span class="span-button"> <i
											style="zoom: 1.5; -moz-transform: scale(1.5);"
											class="icon-eye-open icon-white"></i>
											<p class="p-button">소스</p></span>
									</a>
									
								<a	href="/project/${project.name}/${selectBranch}/${project.getChatRoomName()}-${fn:substring(gitCommit.commitLogID,0,8)}.zip">
										<span class="span-button"> <i
											style="zoom: 1.5; -moz-transform: scale(1.5);"
											class="icon-circle-arrow-down icon-white"></i>
											<p class="p-button">다운</p></span>
									</a>									
								</td>
							</tr>
							<tr>
								<td class="post-bottom"><b>${gitCommit.commiterName}</b>
									${gitCommit.getCommitDate()} &nbsp;&nbsp; 
									<span class="tag-commit tag-name">${gitCommit.commitLogID}</span>
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