<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Forweaver : 찾아보세요!</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<style>
.span-button p {
	margin-top:-3px;
    font-size: 9px;
}
</style>
</head>
<script>

$(document).ready(function() {
	
	
	
	$( "#"+getSort(document.location.href) ).attr('data-theme','b');
	
	 if ($(window).width() > 500) {
			$(".td-button").css("width","160px");
	     	$( ".full-content" ).show();
	     
	    }else{
	    	$(".td-button").css("width","80px");
	    	$( ".full-content" ).hide();
	    	
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
		    	$(".td-button").css("width","80px");
		    	$( ".short-content" ).show();
		    	$( ".full-content" ).hide();
	     } else{
				$(".td-button").css("width","160px");
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
	        
	        $( "#"+getSort(document.location.href) ).css("background-color", "#16a085");
});


</script>

<body>
	<div data-role="page">

		<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<h1>위버 목록</h1>

			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">

<table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items='${weavers}' var='weaver'>
							<tr>
								<td class="td-post-writer-img" rowspan="2"><a
									href="/${weaver.getId()}"> <img
										src="/${weaver.getId()}/img"></a></td>
								<td colspan="2" class="post-top-title">
										<i class="fa fa-quote-left"></i> ${weaver.say} <i
											class="fa fa-quote-right"></i><small> -
											${weaver.getId()}</small>
								</td>
								
								<td rowspan="2" class="td-button"><span class="span-button"><i
										class="fa fa-comments"></i>
										<p title="글 갯수/답변 달린 갯수" class="p-button-mini">${weaver.getInfo('postCount')}/${weaver.getInfo('rePostCount')}</p> </span>
								<span class="span-button full-content"><i
										class="fa fa-comments-o"></i>
											<p title="답변 갯수/답변의 추천수" class="p-button-mini">${weaver.getInfo('myRePostCount')}/${weaver.getInfo('rePostPush')}</p> </span>
								<span class="span-button full-content"><i
										class="fa fa-rocket"></i>
										<p title="코드 업로드 갯수/다운로드 갯수" class="p-button-mini">${weaver.getInfo('codeCount')}/${weaver.getInfo('downCount')}</p> </span>
								<span class="span-button"><i
										class="fa fa-bookmark"></i>
										<p title="프로젝트 갯수/포크 프로젝트 갯수" class="p-button-mini">${weaver.countProject()}/${weaver.getInfo('childProjects','_id')}</p> </span></td>								
							</tr>
							<tr>
							<td class="post-bottom-tag"><c:forEach items="${weaver.tags}"
										var="tag">
										<span title="태그를 클릭해보세요. 태그가 추가됩니다!"
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('$')}">
										tag-massage
										</c:if>
										">${tag}</span>
									</c:forEach></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		<%@ include file="/WEB-INF/panel/common/footer.jsp"%>
	</div>
</body>
</html>