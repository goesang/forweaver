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
	
	var pageCount = ${projectCount}/10;
	if(pageCount < 1 ) 
		pageCount = 1;				
		var options = {
	            currentPage: ${pageIndex},
	            totalPages: pageCount,
	            pageUrl: function(type, page, current){

	                return "${pageUrl}"+page;

	            }
	        }
		 $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
	 if ($(window).width() > 500) {
			$(".td-button").css("width","80px");
		 	$( ".short-content" ).hide();
	     	$( ".full-content" ).show();
	     
	    }else{
	    	$(".td-button").css("width","36px");
	    	$( ".short-content" ).show();
	    	$( ".full-content" ).hide();
	    	
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
		    	$(".td-button").css("width","36px");
		    	$( ".short-content" ).show();
		    	$( ".full-content" ).hide();
	     } else{
				$(".td-button").css("width","80px");
			 	$( ".short-content" ).hide();
		     	$( ".full-content" ).show();
	      }
	 });	 
});
</script>

<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/projectCreatePanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectCreatePanel" data-role="button" data-iconpos="notext"	data-icon="bookmark"></a>
			</div>
			<h1>프로젝트</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              
       <table id = "post-table" class="table table-hover">
						<c:forEach items="${projects}" var="project">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${project.getImgSrc()}">
								</td>
								<td colspan="2" class="post-top-title"><a class="a-post-title"
									rel='external' href="/project/${project.name}/"> <i class="icon-bookmark"></i>
										&nbsp;${project.name} ~
										&nbsp;${fn:substring(project.description,0,100-fn:length(project.name))}
								</a></td>
								
								<td class="td-button" rowspan="2">
								<c:if test="${project.category != 0}">
								<a href="/project/${project.name}/join"> 
									<span class="span-button full-content"><i class="fa fa-key"></i><p class="p-button-small">비공개</p>
									</span>
									</a>
								</c:if>
									<c:if test="${project.category == 0}">
									<a href="/project/${project.name}/"> 
									<span class="span-button full-content">${project.push}<p class="p-button">추천</p>
									</span>
								</a>
								</c:if>
								<c:if test="${project.tmpPermission == 0}">
								<a	href="/project/${project.name}/join"> 
								<span class="span-button"><i class="fa fa-times-circle"></i><p class="p-button">가입</p></span>
								</a>
								</c:if>
								<c:if test="${project.tmpPermission == 1}">
								<a	href="/project/${project.name}/">
								 <span class="span-button"><i class="fa fa-dot-circle-o"></i><p class="p-button">가입</p></span>
								 </a>
								</c:if>
								<sec:authorize ifAnyGranted="ROLE_USER">
									<c:if test="${project.creatorName == currentUser.username}">
									<a	href="/project/${project.name}/">
									<span class='span-button'><i class='icon-user icon-white'></i>
									<p class='p-button-small'>괸리자</p>"
									</span>
									</c:if>
								</sec:authorize>
								</td>
								
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${project.creatorName}</b>
								<date class="full-content"> ${project.getOpeningDateFormat()}</date> 
								</td>
								<td class="post-bottom-tag">
								
									<c:forEach	items="${project.tags}" var="tag">
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
						</c:forEach>
				</table>
				<div class = "text-center">
					<div id="page-pagination"></div>
				</div>
	</div>
	
	</div>
</body>
</html>