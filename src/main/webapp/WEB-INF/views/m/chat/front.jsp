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
	 
	var pageCount = ${count}/10;
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
<%@ include file="/WEB-INF/panel/chatAddPanel.jsp"%>


		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#chatAddPanel" data-role="button" data-iconpos="notext"	data-icon="coffee"></a>
			</div>
			<h1>채팅</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              
      <table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items="${chats}" var="chat">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${chat.getChatAdmin().getImgSrc()}">
								</td>
								<td colspan="2"  class="post-top-title"><a rel="external"  class="a-post-title"
									href="/chat/${chat.chatID}">
											<i class="fa fa-coffee"></i>
										&nbsp;${chat.description}
								</a></td>
								<td class="td-button" rowspan="2">
									<span	class="span-button">${chat.weavers.size()}
											<p class="p-button">인원</p>
									</span></td>
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${chat.getChatAdmin().getName()}</b>
								${chat.getFormatCreated()}
								</td>	
								<td class="post-bottom-tag">
									<c:forEach	items="${chat.tags}" var="tag">
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
						
					</tbody>
				</table>
				<div class = "text-center">
					<div id="page-pagination"></div>
				</div>
	</div>
	
	</div>
</body>
</html>