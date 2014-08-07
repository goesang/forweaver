<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>Forweaver : 소통해보세요!</title>
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
									moveUserPage("${weaver.getId()}","[\"" + tagname + "\"]","");
								var tagArray = eval(tagNames);
								$.each(tagArray, function(index, value) {
									if (value == tagname)
										exist = true;
								});
								if (!exist){
									moveUserPage("${weaver.getId()}",tagNames.substring(0,
											tagNames.length - 1)
											+ ",\"" + tagname + "\"]","");
								}
							});
					
					$('#search-button').click(
							function() {
									var tagNames = $("input[name='tags']").val();
									if(tagNames.length == 2){
										alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
										return;
									}
									moveUserPage("${weaver.getId()}",tagNames,$('#post-title-input').val());							
							});
					
					var pageCount = ${postCount+1}/${number};
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
		<div class="page-header">
			<alert></alert>
			<h5>
				<big><big><i class=" fa fa-university"></i> 수강해보세요!</big></big> <small>강의를
					찾아 수강 신청을 해보거나 수업에 필요한 저장소를 만들어보세요!</small>
				<div style="margin-top: -10px" class="pull-right">
					<button class="btn btn-warning">
						<b><b><i class="fa fa-database"></i> ${lectureCount}</b></b>
					</button>
				</div>
			</h5>
		</div>
		<div class="row">


			<div id="search-div" class="span10">
				<input id="post-search-input" class="title span10"
					placeholder="검색어를 입력하여 강의를 찾아보세요!" type="text" />
			</div>
					<div class="span1">
						<span> <a id='search-button'
							class="post-button btn btn-primary"> <i
								class="icon-search icon-white"></i>
						</a>
						</span>
					</div>
				<div class="span12">

						<table id="post-table" class="table table-hover">
							<tbody>
									<tr>
										<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goesang/img"></a></td>
										<td >
						goesang</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											
											<td></td>
											<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goesang/img"></a></td>
										<td >
						goesangssssssss</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goesang/img"></a></td>
										<td >
						goesang</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											
											<td></td>
											<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goes/img"></a></td>
										<td >
						goesa</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/gog/img"></a></td>
										<td >
						goesang</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											
											<td></td>
											<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goeng/img"></a></td>
										<td >
						goesa</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goeg/img"></a></td>
										<td >
						goesang</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											
											<td></td>
											<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goesang/img"></a></td>
										<td >
						goesa</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/gong/img"></a></td>
										<td >
						goesang</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											
											<td></td>
											<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goe/img"></a></td>
										<td >
						goesa</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
									</tr>
<tr>
										<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goesang/img"></a></td>
										<td >
						goesang</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											
											<td></td>
											<td class="td-post-writer-img" ><a
											href="/goesang"> <img
												src="/goesang/img"></a></td>
										<td >
						goesa</td>
										<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-database"></i>
													<p class="p-button-mini">100/300</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-rocket"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-comments"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-book"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
											<td class="td-button" ><a
											href="/community/${post.postID}"> <span
												class="span-button-inverse"><i class="fa fa-bookmark"></i>
													<p class="p-button-mini">1/0</p>
											</span></a></td>
									</tr>
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