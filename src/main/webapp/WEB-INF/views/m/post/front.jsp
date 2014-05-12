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
	
	
	$( "#"+getSort(document.location.href) ).attr('data-theme','b');
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
});



</script>

<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/postPanel.jsp"%>
		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#postPanel" data-role="button" data-iconpos="notext"	data-icon="comments"></a>
			</div>
			<h1>커뮤니티</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
			<div style="text-align: center;">
				<div data-theme="a" data-mini="true" data-type="horizontal"
					data-role="controlgroup">
					<a id="age-desc" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-order" data-role="button" rel="external"
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1"></a>
					<a id="age-asc" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-order-alt" data-role="button" rel="external"
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1"></a>
					<a id="repost-desc" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-attributes" data-role="button" rel="external"
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1"></a>
					<a id="repost-many" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-attributes-alt" data-role="button"
						rel="external"
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1"></a>
					<c:if test="${massage == null }">
						<a id="push-desc" data-iconpos="notext" : data-icon="thumbs-up"
							data-theme="a" data-role="button" rel="external"
							href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-desc/page:1"></a>
					</c:if>
					<a id="repost-null" data-iconpos="notext" : data-theme="a"
						data-icon="exclamation" data-role="button" rel="external"
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-null/page:1"></a>
				</div>
			</div>

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
										<span
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('@') && tag.contains('/')}">
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