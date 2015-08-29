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
			$(".td-button").css("width","72px");
	     	$( ".full-content" ).show();
	     
	    }else{
	    	$(".td-button").css("width","36px");
	    	$( ".full-content" ).hide();
	    	
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());

	     if (width < 500) {
		    	$(".td-button").css("width","36px");
		    	$( ".short-content" ).show();
		    	$( ".full-content" ).hide();
	     } else{
				$(".td-button").css("width","72px");
			 	$( ".short-content" ).hide();
		     	$( ".full-content" ).show();
	      }
	 });
	 
	 
	 $("#selectBranch").change(function() {
			if ($("#selectBranch option:selected").val() != "empty_Branch")
				window.location = $("#selectBranch option:selected").val();
		});
	 
	 var pageCount = ${gitCommitListCount+1}/10;
	 pageCount = Math.ceil(pageCount);
		
		var options = {
	            currentPage: ${pageIndex},
	            totalPages: pageCount,
	            pageUrl: function(type, page, current){

	                return "/project/${project.name}/commitlog/commit:${selectBranch}/page:"+page;

	            }
	        }

	        $('#page-pagination').bootstrapPaginator(options);
		
	 
});

</script>

<body>
	<div id ="page1" data-role="page">

<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<h1>커밋로그</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              <select data-native-menu="false" data-overlay-theme="c"
						id="selectBranch" data-theme="a">
						<option value="/project/${project.name}/browser/commit:${selectBranch}">${fn:substring(selectBranch,0,20)}</option>
						<c:forEach items="${gitBranchList}" var="gitBranchName">
							<option value="/project/${project.name}/commitlog/commit:${gitBranchName}">${fn:substring(gitBranchName,0,20)}</option>
						</c:forEach>
					</select>
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${gitCommitList}" var="gitCommit">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${gitCommit.getImgSrc()}">
								</td>
								
								<td style="width: 340px;" class="post-top-title"><a
									class="none-color" rel='external'
									href="/project/${project.name}/commitlog-viewer/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										${fn:substring(gitCommit.shortMassage,0,27)}</a></td>
								
								<td class="td-button" rowspan="2">
								<a rel="external" 	href="/project/${project.name}/browser/commit:${fn:substring(gitCommit.commitLogID,0,8)}">
										<span class="span-button"> <i
											class="icon-eye-open icon-white"></i>
											<p class="p-button">소스</p></span>
									</a>
								<a	rel="external" class = "full-content" href="/project/${project.name}/${fn:substring(gitCommit.commitLogID,0,8)}/${project.getChatRoomName()}-${fn:substring(gitCommit.commitLogID,0,8)}.zip">
										<span class="span-button"> <i
											class="icon-circle-arrow-down icon-white"></i>
											<p class="p-button">다운</p></span>
									</a>									
								</td>
							</tr>
							<tr>
								<td class="post-bottom"><b>${gitCommit.commiterName}</b>
									${gitCommit.getCommitDate()} &nbsp; 
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