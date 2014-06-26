<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>Forweaver : 프로젝트 페이지!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
	
	function checkProject(){
		var objPattern = /^[a-zA-Z0-9]+$/;
		var name = $('#project-name').val();
		var description = $('#project-description').val();
		var tags = $("input[name='tags']").val();
		tags = tagInputValueConverter(eval(tags));
		if(tags.length == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!"+
					"</div>");
			return false;
		}else if(!objPattern.test(name)){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 프로젝트명은 영문 숙자 조합이어야 합니다. 다시 입력해주세요!"+
					"</div>");
			return false;
		}
		else if(name.length == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 프로젝트명을 입력하시지 않았습니다. 프로젝트명을 입력해주세요!"+
					"</div>");
			return false;
		}else if(description.lenght == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 프로젝트 소개를 입력하시지 않았습니다. 프로젝트 소개를 입력해주세요!"+
					"</div>");
			return false;
		}else{
			$("form:first").append($("input[name='tags']"));
			return true;
		}
	}
	
	function showProjectContent() {
		var tags = $("input[name='tags']").val();
		if(tags.length == 2){
			alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
			return;
		}
		$('#page-pagination').hide();
		$('#post-table').hide();
		$('#post-content-textarea').fadeIn('slow');
		$('#project-ok').show();
		$('#search-button').hide();
		$('#search-div').hide();
		$('#project-div').fadeIn('slow');
		$('#show-content-button').hide();
		$('#hide-content-button').show();
		editorMode = true;
	}

	function hideProjectContent() {
		$('#page-pagination').show();
		$('#post-table').show();
		$('#search-div').show();
		$('#project-div').hide();
		$('#post-content-textarea').hide();
		$('#project-ok').hide();
		$('#search-button').show();
		$('#show-content-button').show();
		$('#hide-content-button').hide();
		editorMode = false;
	}
		$(document).ready(function() {
			
			hideProjectContent();
			
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
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
								if (!exist)
									movePage(tagNames.substring(0,
											tagNames.length - 1)
											+ ",\"" + tagname + "\"]","");

							});

					$('#search-button').click(
							function() {
									var tagNames = $("input[name='tags']").val();
									movePage(tagNames,$('#post-search-input').val());							
							});
					
							
						var pageCount = ${projectCount}/10;
						pageCount = Math.ceil(pageCount);
						var options = {
					            currentPage: ${pageIndex},
					            totalPages: pageCount,
					            pageUrl: function(type, page, current){

					                return "${pageUrl}"+page;

					            }
					        }

					        $('#page-pagination').bootstrapPaginator(options);
				});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header">
			<alert></alert>
			<h5>
				<big><big><i class=" fa fa-bookmark"></i> 참여해보세요!</big></big> <small>같이
					진행할 동료를 구하거나 흥미로운 프로젝트에 참여해보세요!</small>
				<div style="margin-top: -10px" class="pull-right">

					<button class="btn btn-warning">
						<b><i class="fa fa-database"></i> ${projectCount}</b>
					</button>

				</div>
			</h5>
		</div>
		<div class="row">
			<div class="span12">
				<ul class="nav nav-tabs" id="myTab">
					<li id="age-desc"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<c:if test="${massage == null }">
						<li id="solo"><a
							href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:solo/page:1">외톨이</a></li>
					</c:if>
					<li id="push-many"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-many/page:1">추천순</a></li>
					<li id="push-null"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-null/page:1">추천
							없음</a></li>
					<li id="age-asc"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>
					<li id="repost-null"></li>
				</ul>
			</div>
			<div id="search-div" class="span10">
				<input id="post-search-input" class="title span10"
					placeholder="검색어를 입력하여 프로젝트를 찾아보세요!" type="text" />
			</div>
			
			<form onsubmit="return checkProject()" action="/project/add" method="post">

				<div id="project-div" class="span10">
					<input id ="project-name" class="title span5"
						placeholder="프로젝트명을 입력해주세요!" name="name" type="text" /> 
						<label style="display:inline" class="checkbox" for="checkbox"> 
						
						<input id="project-category-select" name=category type="checkbox" data-toggle="checkbox"> 공개 프로젝트
						</label>
						<input name ="description"class="title span12" type="text" id="project-description"
						placeholder="프로젝트에 대해 설명해주세요!"></input>
				</div>

				<div class="span2">


					<span> <a id="show-content-button"
						href="javascript:showProjectContent();"
						class="post-button btn btn-primary"> <i
							class="icon-edit icon-white"></i>
					</a> <a id='search-button' class="post-button btn btn-primary"> <i
							class="icon-search icon-white"></i>
					</a> <a id="hide-content-button" href="javascript:hideProjectContent();"
						class="post-button btn btn-primary"> <i
							class="icon-edit icon-white"></i>
					</a>
						<button id='project-ok' class="post-button btn btn-primary">
							<i class="icon-ok icon-white"></i>
						</button>

					</span>
				</div>
				
			</form>

				<table id="project-table" class="table table-hover">
					<tbody>
						<c:forEach items="${projects}" var="project">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${project.getImgSrc()}"></td>
								<td colspan="2" class="post-top-title"><a
									class="a-post-title" href="/project/${project.name}/"> <i
										class="fa fa-bookmark"></i> &nbsp;${project.name} ~
										&nbsp;${fn:substring(project.description,0,100-fn:length(project.name))}
								</a></td>
								<td class="td-button" rowspan="2"><c:if
										test="${project.category != 0}">
										<a href="/project/${project.name}/join"> <span
											class="span-button"><i class="fa fa-key"></i>
												<p class="p-button">비공개</p> </span>
										</a>
									</c:if> <c:if test="${project.category == 0}">
										<a href="/project/${project.name}/"> <span
											class="span-button">${project.push}<p class="p-button">추천</p>
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
											<a href="/project/${project.name}"> <span
												class='span-button'><i class="fa fa-user"></i>
													<p class='p-button'>관리자</p>" </span></a>
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

				<div class="text-center">
					<div id="page-pagination"></div>

			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>