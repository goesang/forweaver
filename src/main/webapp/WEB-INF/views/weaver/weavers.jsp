<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 찾아보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">	
		$(function() {
					
					$('#search-button').click(
							function() {
									var tagNames = $("input[name='tags']").val();
									
									movePage(tagNames,$('#post-search-input').val());								
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
				<div style="margin-top: -10px" class="pull-right">
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
								<td class="td-post-writer-img"><a
									href="/${weaver.getId()}"> <img
										src="/${weaver.getId()}/img"></a></td>
								<td>
									<p>
										<i class="fa fa-quote-left"></i> ${weaver.say} <i
											class="fa fa-quote-right"></i><small> -
											${weaver.getId()}</small>
									</p>
								</td>
								
								<td class="td-button"><span class="span-button"><i
										class="fa fa-comments"></i>
										<p title="글 갯수/답변 달린 갯수" class="p-button-mini">${weaver.getInfo('postCount')}/${weaver.getInfo('rePostCount')}</p> </span></td>
								<td class="td-button"><span class="span-button"><i
										class="fa fa-comments-o"></i>
											<p title="답변 갯수/답변의 추천수" class="p-button-mini">${weaver.getInfo('myRePostCount')}/${weaver.getInfo('rePostPush')}</p> </span></td>	
								<td class="td-button"><span class="span-button"><i
										class="fa fa-rocket"></i>
										<p title="코드 업로드 갯수/다운로드 갯수" class="p-button-mini">${weaver.getInfo('codeCount')}/${weaver.getInfo('downCount')}</p> </span></td>	
								<td class="td-button"><span class="span-button"><i
										class="fa fa-university"></i>
										<p title="강의 갯수/수강중인 학생수" class="p-button-mini">${weaver.getInfo('lectureCount')}/${weaver.getInfo('joinWeavers','weaverID')}</p> </span></td>	
								<td class="td-button"><span class="span-button"><i
										class="fa fa-bookmark"></i>
										<p title="프로젝트 갯수/포크 프로젝트 갯수" class="p-button-mini">${weaver.getInfo('projectCount')}/${weaver.getInfo('childProjects','_id')}</p> </span></td>	
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