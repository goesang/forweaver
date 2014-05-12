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
	
	var pageCount = ${postCount+1}/10;
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
<%@ include file="/WEB-INF/panel/lecturePanel.jsp"%>
<%@ include file="/WEB-INF/panel/postPanel.jsp"%>


		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#lecturePanel" data-role="button" data-iconpos="notext"	data-icon="gear"></a>
				<a href="#postPanel" data-role="button" data-iconpos="notext"	data-icon="comments"></a>
			</div>
			<h1>${lecture.name}</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              
       <table id = "post-table" class="table table-hover">
						<c:forEach items="${posts}" var="post">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${post.getImgSrc()}">
								</td>
								<td colspan="2" class="post-top-title"><a rel="external"  class="a-post-title"
									href="/community/${post.postID}"> 
									<c:if test="${post.isLong()}">
											<i class=" icon-align-justify"></i>
									</c:if> 
									<c:if test="${!post.isLong()}">
											<i class=" fa fa-comment"></i>
									</c:if> &nbsp;${post.title}
								</a></td>
								<td class="td-button" rowspan="2"> <c:if
										test="${post.kind == 2}">
										<a rel="external"  href="/community/${post.postID}/delete"> <span
											class="span-button"> X
												<p class="p-button">삭제</p>
										</span>
										</a>
									</c:if> <c:if test="${post.kind != 2}">
										<a rel="external"  href="/community/${post.postID}/push"> <span
											class="span-button"> ${post.push}
												<p class="p-button">추천</p>
										</span>
										</a>
									</c:if>
									<a	rel="external"  class = "full-content" href="/community/${post.postID}"> 
									<span	class="span-button">${post.rePostCount}
											<p class="p-button">답변</p>
									</span></a>
								</td>
								
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${post.writerName}</b>
								<date class="full-content"> ${post.getFormatCreated()}</date> 
								</td>
								<td class="post-bottom-tag">
								
									<c:forEach	items="${post.tags}" var="tag">
										<c:if test="${!tag.startsWith('@')}">
											<span	class="tag-name">${tag}</span>		
										</c:if>								
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