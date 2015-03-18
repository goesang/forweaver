<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
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
		<div class="page-header page-header-none">
			<h1 style="text-align: center; font-weight: 500;">
				<span style="font-size:55px; color:#999;"><i class="fa fa-wrench"></i></span>
				 관리자 페이지입니다! 
			</h1><br/>
			<div class="pull-right">권한 : <span class="label btn-danger">삭제</span> <span class="label label-warning">관리</span></div>
			<br/><br/>
			<div class="row">
				<div class="span12">
					<ul class="nav nav-tabs pull-left" id="myTab">
						
					</ul>
					<div class="navbar navbar-inverse">
						<ul style="border-bottom: 0px;" class="nav pull-right">
							<li class="dropdown"><button
								class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
								<i class=" fa fa-twitter"></i> 위버<b class="caret"></b></button>
							 
								<ul class="dropdown-menu">
									<li><a
										href="/admin"><i
										class=" fa fa-comments"></i> 커뮤니티</a></li>
									<li><a
										href="/admin/project"><i
										class=" fa fa-bookmark"></i> 프로젝트</a></li>
									<li><a
										href="/admin/lecture"><i
										class=" fa fa-university"></i> 강의</a></li>
									<li><a
										href="/admin/code"><i
										class=" fa fa-rocket"></i> 코드</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<c:if test="${search == null}">
					<div class="span11">
						<input name="title" id="post-title-input" class="title span11"
							placeholder="찾고 싶은 검색어를 입력해주세요!" type="text" />
					</div>
					<div class="span1">
						<span> <a id='search-button'
							class="post-button btn btn-primary"> <i
								class="icon-search"></i>
						</a>
						</span>
					</div>
				</c:if>
				<div class="span12">

					<c:if test="${posts != null}">
						<table id="post-table" class="table table-hover">
							<tbody>
								<c:forEach items="${posts}" var="post">
									<tr>
										<td class="td-post-writer-img" rowspan="2"><a
											href="/${post.writerName}"> <img
												src="${post.getImgSrc()}"></a></td>
										<td colspan="2" class="post-top-title"><a
											class="a-post-title" href="/community/${post.postID}"> <c:if
													test="${post.isLong()}">
													<i class=" icon-align-justify"></i>
												</c:if> <c:if test="${!post.isLong()}">
													<i class="fa fa-comment"></i>
												</c:if> &nbsp;${cov:htmlEscape(post.title)}
										</a></td>
										<td class="td-button" rowspan="2"><c:if
												test="${post.kind == 2}">
												<a href="/community/${post.postID}/delete"> <span
													class="span-button"> <i class="fa fa-trash-o"></i>
														<p class="p-button">삭제</p>
												</span>
												</a>
											</c:if> <c:if test="${post.kind != 2}">
												<a href="/community/${post.postID}/push"> <span
													class="span-button"> ${post.push}
														<p class="p-button">추천</p>
												</span>
												</a>
											</c:if></td>
										<td class="td-button" rowspan="2"><a
											href="/community/${post.postID}"> <span
												class="span-button">${post.rePostCount}
													<p class="p-button">답변</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="post-bottom"><a href="/${post.writerName}"><b>${post.writerName}</b></a>
											${post.getFormatCreated()}</td>
										<td class="post-bottom-tag"><c:forEach
												items="${post.tags}" var="tag">
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
					</c:if>
					<c:if test="${lectures != null}">
						<table id="lecture-table" class="table table-hover">
							<tbody>
								<c:forEach items="${lectures}" var="lecture">
									<tr>
										<td class="td-post-writer-img" rowspan="2"><img
											src="${lecture.getImgSrc()}"></td>
										<td colspan="2" class="post-top-title"><a
											class="a-post-title" href="/lecture/${lecture.name}"> <i
												class='fa fa-university'></i> &nbsp;${lecture.name} ~
												&nbsp;${fn:substring(lecture.description,0,100-fn:length(lecture.name))}
										</a></td>
										<td class="td-button" rowspan="2"><sec:authorize
												ifNotGranted="ROLE_USER">
												<a href="/lecture/${lecture.name}/join"> <span
													class="span-button"><i class="fa fa-times-circle"></i>
														<p class="p-button">가입</p></span>
												</a>
											</sec:authorize> <sec:authorize ifAnyGranted="ROLE_USER">
												<c:if
													test="${!lecture.isJoin() && lecture.creatorName != currentUser.username}">
													<a href="/lecture/${lecture.name}/join"> <span
														class="span-button"><i class="fa fa-times-circle"></i>
															<p class="p-button">가입</p></span>
													</a>
												</c:if>
												<c:if
													test="${lecture.isJoin() && lecture.creatorName != currentUser.username}">
													<a href="/lecture/${lecture.name}"> <span
														class="span-button"><i class="fa fa-circle-o"></i>
															<p class="p-button">가입</p></span>
													</a>
												</c:if>
												<c:if test="${lecture.creatorName == currentUser.username}">
													<a onclick="return confirm('정말로 삭제하시겠습니다?')"
														href="/lecture/${lecture.name}/delete"> <span
														class="span-button"> <i class="fa fa-trash-o"></i>
															<p class="p-button">삭제</p>
													</span>
													</a>
												</c:if>
											</sec:authorize></td>
									</tr>
									<tr>
										<td class="post-bottom"><b>${lecture.creatorName}</b>
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
					</c:if>
					<c:if test="${codes != null}">
						<table id="post-table" class="table table-hover">
							<tbody>
								<c:forEach items="${codes}" var="code">
									<tr>
										<td class="td-post-writer-img" rowspan="2"><img
											src="${code.getImgSrc()}"></td>
										<td colspan="2" class="post-top-title"><a
											class="a-post-title" href="/code/${code.codeID}"> <i
												class="fa fa-download"></i>&nbsp;${cov:htmlEscape(code.name)} -
												${cov:htmlEscape(code.content)}
										</a></td>
										<td class="td-button" rowspan="2"><a
											href="/code/${code.codeID}/${cov:htmlEscape(code.name)}.zip"> <span
												class="span-button"> ${code.downCount}
													<p class="p-button">다운</p>
											</span>
										</a></td>
										<td class="td-button" rowspan="2"><a
											href="/code/${code.codeID}"> <span class="span-button">${code.rePostCount}
													<p class="p-button">답변</p>
											</span></a></td>
									</tr>
									<tr>
										<td class="post-bottom"><b>${code.writerName}</b>
											${code.getFormatCreated()}</td>
										<td class="post-bottom-tag"><c:forEach
												items="${code.tags}" var="tag">
												<span class="tag-name">${tag}</span>
											</c:forEach></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>

					</c:if>
					<c:if test="${projects != null}">
						<table id="project-table" class="table table-hover">
							<tbody>
								<c:forEach items="${projects}" var="project">
									<tr>
										<td class="td-post-writer-img" rowspan="2"><img
											src="${project.getImgSrc()}"></td>
										<td colspan="2" class="post-top-title"><a
											class="a-post-title" href="/project/${project.name}/"> <i
												class="fa fa-bookmark"></i> &nbsp;${project.name} ~
												&nbsp;${fn:substring(cov:htmlEscape(project.description),0,100-fn:length(project.name))}
										</a></td>
										<td class="td-button" rowspan="2"><c:if
												test="${project.category != 0}">
												<a href="/project/${project.name}/join"> <span
													class="span-button"><i class="fa fa-lock"></i>
														<p class="p-button">비공개</p> </span>
												</a>
											</c:if> <c:if test="${project.category == 0}">
												<a href="/project/${project.name}/"> <span
													class="span-button">${project.push}<p
															class="p-button">추천</p>
												</span>
												</a>
											</c:if></td>
										<td class="td-button" rowspan="2"><sec:authorize
												ifNotGranted="ROLE_USER">
												<a href="/project/${project.name}/join"> <span
													class="span-button"><i class="fa fa-times-circle"></i>
														<p class="p-button">가입</p></span>
												</a>
											</sec:authorize> <sec:authorize ifAnyGranted="ROLE_USER">
												<c:if
													test="${!project.isJoin() && project.creatorName != currentUser.username}">
													<a href="/project/${project.name}/join"> <span
														class="span-button"><i class="fa fa-times-circle"></i>
															<p class="p-button">가입</p></span>
													</a>
												</c:if>
												<c:if
													test="${project.isJoin() && project.creatorName != currentUser.username}">
													<a href="/project/${project.name}"> <span
														class="span-button"><i class="fa fa-circle-o"></i>
															<p class="p-button">가입</p></span>
													</a>
												</c:if>
												<c:if test="${project.creatorName == currentUser.username}">
													<a onclick="return confirm('정말로 삭제하시겠습니다?')"
														href="/project/${project.name}/delete"> <span
														class="span-button"> <i class="fa fa-trash-o"></i>
															<p class="p-button">삭제</p>
													</span></a>
												</c:if>
											</sec:authorize></td>
									</tr>
									<tr>
										<td class="post-bottom"><b>${project.creatorName}</b>
											${project.getOpeningDateFormat()}</td>
										<td class="post-bottom-tag"><c:forEach
												items="${project.tags}" var="tag">
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
					</c:if>
					<div class="text-center">
						<div id="page-pagination"></div>
					</div>
				</div>
			</div>
			<%@ include file="/WEB-INF/common/footer.jsp"%>
		</div>
	</div>
</body>


</html>