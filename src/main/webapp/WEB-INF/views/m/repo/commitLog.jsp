<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>
<script>
$(document).ready(function() {
	 if ($(window).width() > 500) {
	     	$( ".full-content" ).show();
	    }else{
	    	$( ".full-content" ).hide();
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
	     	$( ".full-content" ).hide();
	     } else{
	 	    $( ".full-content" ).show();
	      }
	 });
	 
		var pageCount = ${gitCommitListCount}/10;
		if(pageCount < 1 ) 
			pageCount = 1;
		
		var options = {
	            currentPage: ${pageIndex},
	            totalPages: pageCount,
	            pageUrl: function(type, page, current){
	                return "/lecture/${repo.lectureName}/repo/${repo.name}/commitlog/commit:${selectBranch}/page:"+page;
	            }
	        }

	        $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
	 
});

</script>

<body>
	<div id ="page1" data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/repoPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#repoPanel" data-role="button" data-iconpos="notext"	data-icon="gear"></a>
			</div>
			<h1>커밋로그</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${gitCommitList}" var="gitCommit">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${gitCommit.getImgSrc()}">
								</td>
								
								<td class="post-top-title"><a
									class="none-color" rel='external'
									href="/lecture/${repo.lectureName}/repo/${repo.name}/commitlog-viewer/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										${fn:substring(gitCommit.shortMassage,0,27)}</a></td>
								
								<td class="td-commitlog-button" rowspan="2">
								<a rel="external" 	href="/lecture/${repo.lectureName}/repo/${repo.name}/browser/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										<span class="span-button"> <i
											class="icon-eye-open icon-white"></i>
											<p class="p-button">소스</p></span>
									</a>
									
								<a	rel="external" class = "full-content" href="/project:${repo.name}-${fn:substring(gitCommit.commitLogID,0,8)}.zip">
										<span class="span-button"> <i
											class="icon-circle-arrow-down icon-white"></i>
											<p class="p-button">다운</p></span>
									</a>									
								</td>
							</tr>
							<tr>
								<td class="post-bottom"><b>${gitCommit.commiterName}</b>
									${gitCommit.getCommitDate()} &nbsp; 
									<span class="full-content tag-commit tag-name">${gitCommit.commitLogID}</span>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class = "text-center">
					<div id="page-pagination"></div>
				</div>
	</div>
	
	</div>
</body>
</html>