<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 프로젝트 페이지!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
	function showProjectContent() {
		$('#page-selection').hide();
		$('#post-table').hide();
		$('#project-ok').show();
		$('#search-button').hide();
		$('#search-div').hide();
		$('#project-div').fadeIn('slow');
		$('#show-write-button').hide();
		$('#hide-write-button').show();
		$('#forweaver-table').hide();
		//editorMode = true;
	}

	function hideProjectContent() {
		$('#page-selection').show();
		$('#post-table').show();
		$('#search-div').show();
		$('#project-div').hide();
		$('#project-ok').hide();
		$('#search-button').show();
		$('#show-write-button').show();
		$('#hide-write-button').hide();
		$('#forweaver-table').show();
		//editorMode = false;
	}
	$(function(){
		$( "#"+getSort(document.location.href) ).addClass( "active" );
		
		var pageCount = ${postCount+1}/${number}; //총 페이지 갯수를 계산함
		pageCount = Math.ceil(pageCount);
		
		$('#page-selection').twbsPagination({ // 페이지 네비게이터
	        totalPages: pageCount,
	        first:"<<",
	        prev:"<",
	        next:">",
	        last:">>",
	        visiblePages: 10,
	        startPage : ${pageIndex},
	        href: "${pageUrl}"+'{{number}}'
	    });
	});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/views/common/nav.jsp"%>
		<div class="page-header">
			<alert></alert>
			<h6>
				<i class=" fa fa-bookmark"></i> 참여해보세요! <small class="hidden-xs">  <small>같이
					진행할 동료를 구하거나 흥미로운 프로젝트에 참여해보세요!</small></small>
				<div style="margin-top: -10px" class="pull-right">

					<button class="btn btn-warning">
						<b><i class="fa fa-database"></i> ${projectCount}</b>
					</button>

				</div>
			</h6>
		</div>
		<div class="row">
			<div class="col-md-12">
				<ul class="nav nav-tabs" id="myTab">
					<li id="age-desc"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<li id="solo"><a
							href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:solo/page:1">외톨이</a></li>
					<li id="fork"><a
							href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:fork/page:1">포크</a></li>
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
			
			<div id="search-div" class="col-md-10 col-xs-9">
				<input id="post-search-input" class="form-control col-md-10"
					placeholder="검색어를 입력하여 프로젝트를 찾아보세요!" type="text" style="margin-bottom: 10px;"/>
			</div>
			
			<form id="post-form" onsubmit="return checkProject()" action="/project/add" enctype="multipart/form-data" method="post">
				<div id="project-div" class="form-group col-md-10 col-xs-9" style="display: none;">
					<input id ="project-name" class="form-control col-md-5 col-xs-4" style="width: 41.66666667%;"
						placeholder="프로젝트명을 입력해주세요!" name="name" type="text" />
						<label class="checkbox col-md-2 col-xs-5" for="checkbox" style="margin-left: 10px;">
							<input type="checkbox" value="" id="project-category-select" name=category data-toggle="checkbox" class="custom-checkbox">
							<span class="icons"><span class="icon-unchecked"></span><span class="icon-checked"></span></span>
							 공개 프로젝트   <!-- JS 써서 Flat UI CheckBox 토글해야함. -->
						</label>
						<hr class="col-md-10 col-xs-9" style="border: none; margin-top: 5px;" />
						<input name ="description"class="form-control col-md-12 " type="text" id="project-description"
						placeholder="프로젝트에 대해 설명해주세요!"></input>
				</div>

				<div class="col-md-2 col-xs-3">
					<span> <a id="show-write-button"
						href="javascript:showProjectContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> <a id='search-button' class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> <a id="hide-write-button" href="javascript:hideProjectContent();"
						class="post-button btn btn-primary" style="display: none;"> <i class="fa fa-pencil"></i>
					</a>
						<button id='project-ok' class="post-button btn btn-primary" style="display: none;">
							<i class="fa fa-check"></i>
						</button>
					</span>
				</div>
				
			</form>
			<div class="col-md-12">
				<table id="forweaver-table" class="table table-hover">
					<tbody>
						<c:forEach items="${projects}" var="project">
							<tr>
								<td class="forweaver-td-avatar" rowspan="2"><a
									href="/${project.getCreatorName()}"><img class="forweaver-avatar"
									src="${project.getImgSrc()}"></a></td>
								<td colspan="2" class="post-top-title"><a
									class="none-color" href="/project/${project.name}/">
									<c:if test="${!project.isForkProject()}">
										 <i class="fa fa-bookmark"></i>
									 </c:if>
									 <c:if test="${project.isForkProject()}">
										 <i class="fa fa-code-fork"></i>
									 </c:if>
									 &nbsp;${project.name} ~
										&nbsp;${fn:substring(project.description,0,100-fn:length(project.name))}
								</a></td>
								<td class="forweaver-td-span" rowspan="2"><c:if
										test="${project.category != 0}">
										<a href="/project/${project.name}/join"> <span
											class="forweaver-span"><i class="fa fa-lock"></i>
												<p class="p-button">비공개</p> </span>
										</a>
									</c:if> <c:if test="${project.category == 0}">
										<a href="/project/${project.name}/push"> <span
											class="forweaver-span">${project.push}<p class="p-button">추천</p>
										</span>
										</a>
									</c:if></td>
								<td class="forweaver-td-span" rowspan="2"><sec:authorize
										ifNotGranted="ROLE_USER">
										<a href="/project/${project.name}/join"> <span
											class="forweaver-span"><i class="fa fa-times"></i>
												<p class="p-button">가입</p></span>
										</a>
									</sec:authorize> <sec:authorize ifAnyGranted="ROLE_USER">
										<c:if
											test="${!project.isJoin() && project.creatorName != currentUser.username}">
											<a href="/project/${project.name}/join"> <span
												class="forweaver-span"><i class="fa fa-times"></i>
													<p class="p-button">미가입</p></span>
											</a>
										</c:if>
										<c:if
											test="${project.isJoin() && project.creatorName != currentUser.username}">
											<a href="/project/${project.name}"> <span
												class="forweaver-span"><i class="fa fa-user"></i>
													<p class="p-button">회원</p></span>
											</a>
										</c:if>
										<c:if test="${project.creatorName == currentUser.username}">
											<a href="/project/${project.name}"> <span
												class='forweaver-span'><i class="fa fa-user"></i>
													<p class='p-button'>관리자</p></span></a>
										</c:if>
									</sec:authorize></td>
							</tr>
							<tr>
								<td class="forweaver-td-info none-boder-top"><b>${project.creatorName}</b>
									${project.getOpeningDateFormat()}</td>
								<td class="forweaver-td-tags none-boder-top"><c:forEach
										items="${project.tags}" var="tag">
										<span
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										">${tag}</span>
									</c:forEach></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				<div class="text-center">
					<div id="page-selection"></div>

			</div>
		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>

</body>
</html>