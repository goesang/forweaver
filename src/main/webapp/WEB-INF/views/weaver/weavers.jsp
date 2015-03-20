<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 찾아보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">	
		$(function() {
					
			
			$('.tag-name').click(
					function() {
						var tagname = $(this).text();
						var exist = false;
						var tagNames = $("input[name='tags']").val();
						if (tagNames.length == 2)
							movePage("[\"" + tagname + "\"]","");
						var tagArray = eval(tagNames);
						$.each(tagArray, function(index, value) {
							if (value == tagname)
								exist = true;
						});
						if (!exist){
							movePage(tagNames.substring(0,
									tagNames.length - 1)
									+ ",\"" + tagname + "\"]","");
						}
					});
					
					var pageCount = ${weaverCount+1}/${number};
					pageCount = Math.ceil(pageCount);					
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
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header page-header-none">
			<alert></alert>
			<h5>
				<big><big><i class=" fa fa-twitter"></i> 찾아보세요!</big></big> <small>관심있는 주제를 가지고 다른 위버를 찾아보세요!</small>
				<div style="margin-top: -10px" class="pull-right" title='전체 회원수&#13;${weaverCount}명'>
					<button class="btn btn-warning">
						<b><b><i class="fa fa-database"></i> ${weaverCount}</b></b>
					</button>
				</div>
			</h5>
		</div>
		<div class="row">
			<div class="span12">

				<table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items='${weavers}' var='weaver'>
							<tr>
								<td class="td-post-writer-img" rowspan="2"><a
									href="/${weaver.getId()}"> <img
										src="/${weaver.getId()}/img"></a></td>
								<td colspan="2" class="post-top-title">
										<i class="fa fa-quote-left"></i> ${cov:htmlEscape(weaver.say)} <i
											class="fa fa-quote-right"></i><small> -
											${weaver.getId()}</small>
								</td>
								<td rowspan="2" class="td-button"><span class="span-button-inverse">
								${weaver.weaverInfo.score}<p  title="회원의 활동점수" class="p-button">점수</p> </span></td>								
								<td rowspan="2" class="td-button"><span class="span-button"><i
										class="fa fa-comments"></i>
										<p title="글 갯수 / 추천수" class="p-button-mini">${weaver.weaverInfo.postCount}/${weaver.weaverInfo.postPush}</p> </span></td>
								
								<td rowspan="2" class="td-button"><span class="span-button"><i
										class="fa fa-comment"></i>
											<p title="내가 올린 답변 갯수 / 답변의 추천수" class="p-button-mini">${weaver.weaverInfo.myRePostCount}/${weaver.weaverInfo.myRePostPush}</p> </span></td>	
								<td rowspan="2" class="td-button"><span class="span-button"><i
										class="fa fa-rocket"></i>
										<p title="코드 업로드 갯수 / 다운로드 갯수" class="p-button-mini">${weaver.weaverInfo.codeCount}/${weaver.weaverInfo.codeDownloadCount}</p> </span></td>	
								<td rowspan="2" class="td-button"><span class="span-button"><i
										class="fa fa-bookmark"></i>
										<p title="프로젝트 갯수 / 포크 프로젝트 갯수" class="p-button-mini">${weaver.weaverInfo.projectCount}/${weaver.weaverInfo.forkProjectCount}</p> </span></td>	
							</tr>
							<tr>
							<td class="post-bottom-tag"><c:forEach items="${weaver.tags}"
										var="tag">
										<span
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
				<div class="text-center">
					<div id="page-pagination"></div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>


</html>
