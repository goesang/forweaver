<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
<style>
.syntaxhighlighter{overflow:hidden !important;}
.syntaxhighlighter table td.code .container2 {
    width: 100px !important;
}
</style>
</head>
<script>

$(document).ready(function() {
	
	$("#selectmenuBranch").change(function(){
			setTimeout(function(){
				window.location.replace($("#selectmenuBranch option:selected").val());
			});

	});
	
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

	        $('#page-pagination').bootstrapPaginator(options);
		
			 $('a').not('#selectmenuBranch-button').attr('rel', 'external');
	        
	        $( "#"+getSort(document.location.href) ).css("background-color", "#16a085");
	        
	        <c:forEach	items="${codes}" var="code" varStatus="status">	
				 $("#code-${status.count}").addClass("brush: "+extensionSeach('${code.getFirstCodeName()}')+";");
		 	</c:forEach>
});

SyntaxHighlighter.all();

</script>

<body>
	<div data-role="page">

		<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<h1>${weaver.getId()}님의 코드</h1>

			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">
		<div>
			<div style="text-align: center">
				<img style="height: 40px; width: 40px;" class="img-polaroid"
					src="${weaver.getImgSrc()}">
			</div>
			<h6 style="text-align: center">

				<b><i class="fa fa-quote-left"></i> ${cov:htmlEscape(weaver.getSay())} <i
					class="fa fa-quote-right"></i></b> <small>- ${weaver.getId()}</small>
			</h6>

		</div>

			<div style="text-align: center;">
				<div data-theme="a" data-mini="true" data-type="horizontal"
					data-role="controlgroup">
					<a id="age-desc" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-order" data-role="button" rel="external"
						href="/${weaver.getId()}/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1"></a>
					<a id="age-asc" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-order-alt" data-role="button" rel="external"
						href="/${weaver.getId()}/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1"></a>
					<a class="full-content" id="repost-desc" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-attributes" data-role="button" rel="external"
						href="/${weaver.getId()}/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1"></a>
					<a class="full-content" id="repost-many" data-iconpos="notext" : data-theme="a"
						data-icon="sort-by-attributes-alt" data-role="button"
						rel="external"
						href="/${weaver.getId()}/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1"></a>
					<a id="download-desc" data-iconpos="notext" : data-theme="a"
						data-icon="download" data-role="button" rel="external"
						href="/${weaver.getId()}/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:download-desc/page:1"></a>
					<button data-theme="f" >${codeCount}</button>
				<select id="selectmenuBranch" data-mini="true" data-native-menu="false" data-overlay-theme="c" data-theme="a">
						<option value="/${weaver.getId()}/code">코드</option>
						<option value="/${weaver.getId()}/">커뮤니티</option>
						<option value="/${weaver.getId()}/project">프로젝트</option>
					</select>
				</div>
					
				
			</div>

			<table id="post-table" class="table table-hover">
				<c:forEach items="${codes}" var="code" varStatus="status">
					<tr>
						<td class="td-post-writer-img" rowspan="2">
						<a rel="external" href="/${code.writerName}">
						<img src="${code.getImgSrc()}"></a></td>
						<td colspan="2" class="post-top-title"><a rel="external"
							class="a-post-title" href="/code/${code.codeID}"> 
							<i class="fa fa-download"> &nbsp;${cov:htmlEscape(code.content)}
						</a></td>
						<td class="td-button" rowspan="2"><a
									href="/code/${code.codeID}/${cov:htmlEscape(code.name)}.zip"> <span
										class="span-button"> ${code.downCount}
											<p class="p-button">다운</p>
									</span>
								</a> <a rel="external" class="full-content"
							href="/code/${code.codeID}"> <span class="span-button">${code.rePostCount}
									<p class="p-button">답변</p>
							</span></a></td>

					</tr>
					<tr>
						<td class="post-bottom"><b>${code.writerName}</b> <date
								class="full-content"> ${code.getFormatCreated()}</date></td>
						<td class="post-bottom-tag"><c:forEach items="${code.tags}"
										var="tag">
										<span class="tag-name">${tag}</span>
									</c:forEach></td>
					</tr>
					
					<tr><td style="padding-top: 20px;"class="none-top-border"colspan="5">
							<a href="/code/${code.codeID}">
							<pre id="code-${status.count}">${cov:htmlEscape(code.getFirstCode())}</pre>
							</a>
							 </td>
							</tr>
				</c:forEach>
			</table>
			<div class="text-center">
				<div id="page-pagination"></div>
			</div>

		</div>
		<%@ include file="/WEB-INF/panel/common/footer.jsp"%>
	</div>
</body>
</html>