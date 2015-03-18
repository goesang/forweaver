<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 소통해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">	
		$(function() {
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
					$('.tag-name').click(
							function() {
								var tagname = $(this).text();
								var exist = false;
								var tagNames = $("input[name='tags']").val();
								if (tagNames.length == 2)
									moveUserPage("/${weaver.getId()}/lecture/","[\"" + tagname + "\"]","");
								var tagArray = eval(tagNames);
								$.each(tagArray, function(index, value) {
									if (value == tagname)
										exist = true;
								});
								if (!exist){
									moveUserPage("/${weaver.getId()}/lecture/",tagNames.substring(0,
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
									moveUserPage("/${weaver.getId()}/lecture/",tagNames,$('#post-title-input').val());							
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
		<div class="pull-right">
			<button class="btn btn-warning">
				<b><i class="fa fa-database"></i> ${lectureCount}</b>
			</button>
		</div>
		<div class="page-header page-header-none">
			<alert></alert>
			<h5 style="margin-left: 50px;text-align: center">
				<img style="height: 60px; width: 60px;" class="img-polaroid"
					src="${weaver.getImgSrc()}">
			</h5>
			<h5 style="text-align: center">

				<big><i class="fa fa-quote-left"></i> ${cov:htmlEscape(weaver.getSay())}
				 <i class="fa fa-quote-right"></i></big> <small>- ${weaver.getId()}</small>
			</h5>
			</div>
			<div class="row">
				<div class="span12">
					<ul class="nav nav-tabs pull-left" id="myTab">
						<li id="age-desc"><a
							href="/${weaver.getId()}/lecture<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:age-desc/page:1">전체</a></li>
						<li id="teach"><a
							href="/${weaver.getId()}/lecture<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:teach/page:1">개설한 강의</a></li>
						<li id="join"><a
							href="/${weaver.getId()}/lecture<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/sort:join/page:1">수강중인 강의</a></li>
						
					</ul>
					<div class="navbar navbar-inverse">
						<ul style="border-bottom: 0px;" class="nav pull-right">
							<li class="dropdown">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
								<span style="font-size:14px;">
								<i class=" fa fa-university"></i>&nbsp;강의</span>
								<b class="caret"></b></button>
								<ul class="dropdown-menu">
									<li><a 
										href="/${weaver.getId()}/"><i
										class=" fa fa-comments"></i>&nbsp;&nbsp;커뮤니티</a></li>
									<li><a 
										href="/${weaver.getId()}/project"><i
										class=" fa fa-bookmark"></i>&nbsp;&nbsp;프로젝트</a></li>
									<li><a
										href="/${weaver.getId()}/code"><i
										class=" fa fa-rocket"></i>&nbsp;&nbsp;코드</a></li>	
								</ul></li>
						</ul>
					</div>
				</div>
				<div class="span12">
						<table id="lecture-table" class="table table-hover">
							<tbody>
								<c:forEach items="${lectures}" var="lecture">
									<tr>
										<td class="td-post-writer-img" rowspan="2"><a href="/${lecture.creatorName}"><img
											src="${lecture.getImgSrc()}"></a></td>
										<td colspan="2" class="post-top-title"><a
											class="a-post-title" href="/lecture/${lecture.name}"> <i
												class='fa fa-university'></i> &nbsp;${lecture.name} ~
												&nbsp;${fn:substring(lecture.description,0,100-fn:length(lecture.name))}
										</a></td>
										<td class="td-button" rowspan="2"><sec:authorize access="isAnonymous()">
												<a href="/lecture/${lecture.name}/join"> <span
													class="span-button"><i class="fa fa-times-circle"></i>
														<p class="p-button">가입</p></span>
												</a>
											</sec:authorize> <sec:authorize access="isAuthenticated()">
												<c:if
													test="${lecture.isJoin() == 0 }">
													<a href="/lecture/${lecture.name}/join"> <span
														class="span-button"><i class="fa fa-times-circle"></i>
															<p class="p-button">가입</p></span>
													</a>
												</c:if>
												<c:if
													test="${lecture.isJoin() == 1}">
													<a href="/lecture/${lecture.name}"> <span
														class="span-button"><i class="fa fa-circle-o"></i>
															<p class="p-button">가입</p></span>
													</a>
												</c:if>
												<c:if test="${lecture.isJoin() == 2}">
											<a href="/lecture/${lecture.name}"> <span
												class='span-button'><i class="fa fa-user"></i>
													<p class='p-button'>관리자</p>" </span>
											</a>
										</c:if>
											</sec:authorize></td>
									</tr>
									<tr>
								<td class="post-bottom"><a href="/${lecture.creatorName}"><b>${lecture.creatorName}</b></a>
											${lecture.getOpeningDateFormat()}</td>
										<td class="post-bottom-tag"><c:forEach
												items="${lecture.tags}" var="tag">
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