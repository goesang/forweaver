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
		 $('#page-pagination').bootstrapPaginator(options);
		$('a').attr('rel', 'external');
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
	 
	 $( "#"+getSort(document.location.href) ).css("background-color", "#16a085");
});
</script>

<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<h1>프로젝트</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
        
        <div style="text-align: center;">
				<div data-theme="a" data-mini="true" data-type="horizontal"
					data-role="controlgroup">
					<a id="age-desc" data-iconpos="notext" data-theme="a"
						data-icon="sort-by-order" data-role="button" rel="external"
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1"></a>
					<a id="age-asc" data-iconpos="notext" data-theme="a"
						data-icon="sort-by-order-alt" data-role="button" rel="external"
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1"></a>
					<a id="public" data-iconpos="notext" data-theme="a"
						data-icon="share" data-role="button" rel="external"
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:public/page:1"></a>
					<a id="private" data-iconpos="notext" data-theme="a"
						data-icon="lock" data-role="button"
						rel="external"
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:private/page:1"></a>
					<a id="homework" data-iconpos="notext" data-theme="a"
						data-icon="book" data-role="button" rel="external"
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:homework/page:1"></a>
					<button data-theme="f" >${projectCount}</button>
				</div>
			</div>
              
       <table id = "post-table" class="table table-hover">
						<c:forEach items="${projects}" var="project">
							<tr>
								<td class="td-post-writer-img" rowspan="2">
								<a rel="external" href="/${project.creatorName}"><img src="${project.getImgSrc()}"></a>
								</td>
								<td colspan="2" class="post-top-title"><a class="a-post-title"
									rel='external' href="/project/${project.name}/"> <i class="fa fa-bookmark"></i>
										&nbsp;${project.name} ~
										&nbsp;${fn:substring(project.description,0,100-fn:length(project.name))}
								</a></td>
								
								<td class="td-button" rowspan="2">
								 <c:if test="${project.category == 0}">
										<span
											class="span-button"><i class="fa fa-share-alt"></i><p class="p-button">공개</p>
										</span>
									</c:if>
								<c:if test="${project.category == 1}">
										<span
											class="span-button"><i class="fa fa-file-archive-o"></i>
												<p class="p-button">일반</p> </span>
									</c:if>
								<c:if test="${project.category == -1}">
										<a href="/project/${project.name}"> <span
											class="span-button"><i class="fa fa-code-fork"></i>
												<p class="p-button">파생</p> </span>
										</a>
									</c:if>	
								<c:if test="${project.category == 3}">
										<span
											class="span-button"><i class="fa fa-lock"></i>
												<p class="p-button">비공개</p> </span>
									</c:if>
									<sec:authorize
										access="isAnonymous()">
										<a href="/login?state=null"> <span
											class="full-content span-button"><i class="fa fa-hand-o-up"></i>
												<p class="p-button">가입</p></span>
										</a>      
									</sec:authorize> <sec:authorize access="isAuthenticated()">
										<c:if
											test="${project.isJoin() == 0}">
											<span
												class="full-content span-button"><i class="fa fa-hand-o-up"></i>
													<p class="p-button">가입</p></span>
										
										</c:if>
										<c:if
											test="${project.isJoin() == 1}">
											<a href="/project/${project.name}"> <span
												class="full-content span-button"><i class="fa fa-user"></i>
													<p class="p-button">회원</p></span>
											</a>
										</c:if>
										<c:if test="${project.isJoin () == 2}">
											<a href="/project/${project.name}"> <span
												class='full-content span-button'><i class="fa fa-user"></i>
													<p class='p-button'>관리자</p></span></a>
										</c:if>
									</sec:authorize></td>
								
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
	<%@ include file="/WEB-INF/panel/common/footer.jsp"%>	
	</div>
	
</body>
</html>