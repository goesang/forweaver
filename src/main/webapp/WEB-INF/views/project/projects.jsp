<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 프로젝트 페이지!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
	
	function checkProject(){
		var objPattern = /^[a-zA-Z0-9]+$/;
		var name = $('#project-name').val();
		var description = $('#project-description').val();
		var tags = $("#tags-input").val();
		
		if(tags.length == 0){
			alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
			return false;
		}else if(!objPattern.test(name)){
			alert("프로젝트명은 영문 숫자 조합이어야 합니다. 다시 입력해주세요!");
			return false;
		}
		else if(name.length == 0){
			alert("프로젝트명을 입력하시지 않았습니다. 프로젝트명을 입력해주세요!");
			return false;
		}else if(description.lenght == 0){
			alert("프로젝트 소개를 입력하시지 않았습니다. 프로젝트 소개를 입력해주세요!");
			return false;
		}else{
			$("form:first").append($("input[name='tags']"));
			return true;
		}
		return false;
	}
	
	function showProjectContent() {
		var tags = $("#tags-input").val();
		if(tags.length == 0){
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
	
	function changeValue(value){
		$('#category').val(value);
	}
		$(document).ready(function() {
			

			hideProjectContent();
			
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
					$('.tag-name').click(
							function() {
								var tagname = $(this).text();
								var exist = false;
								var tagNames = $("#tags-input").val();
								if (tagNames.length == 0 || tagNames == "")
									movePage(tagname,"");
								
								$.each(tagNames.split(","), function(index, value) {
									if (value == tagname)
										exist = true;
								});
								if (!exist)
									movePage(tagNames+ ","+ tagname+" ","");

							});

					$('#search-button').click(
							function() {
									var tagNames = $("#tags-input").val();
									movePage(tagNames,$('#post-search-input').val());							
							});
					
							
						var pageCount = ${projectCount+1}/${number};
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
		<div class="page-header page-header-none">
			<alert></alert>
			<h5>
				<big><big><i class=" fa fa-bookmark"></i> 참여해보세요!</big></big> <small>같이
					진행할 동료를 구하거나 흥미로운 프로젝트에 참여해보세요!</small>
				<div style="margin-top: -10px" class="pull-right"  title='전체 프로젝트 갯수&#13;${projectCount}개'>

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
					<li id="active"><a
							href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:active/page:1">활동순</a></li>
					<li id="push-many"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-many/page:1">추천순</a></li>
					<li id="fork"><a
							href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:fork/page:1">포크</a></li>
					<li id="homework"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:homework/page:1">과제</a></li>
					<li id="private"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:private/page:1">비공개</a></li>	
					<li id="age-asc"><a
						href="/project<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>

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
					
					
					<label  onclick="changeValue(0);"  class="radio radio-period"> 공개 <input type="radio"
						name="group"data-toggle="radio" checked="checked">
					</label> <label onclick="changeValue(1);" class="radio radio-period"> <input type="radio"
						name="group" data-toggle="radio"> 비공개
					</label> <label onclick="changeValue(3);" class="radio radio-period"> <input type="radio"
						name="group"  data-toggle="radio"> 과제
					</label> 
						<input name ="description"class="title span12" type="text" id="project-description"
						placeholder="프로젝트에 대해 설명해주세요!"></input>
				</div>

				<div class="span2">
					<span> <a id="show-content-button"
						href="javascript:showProjectContent();"
						class="post-button btn btn-primary" title="프로젝트 개설하기"> <i class="fa fa-pencil"></i>
					</a> <a id='search-button' class="post-button btn btn-primary" title="프로젝트 검색하기"> <i class="fa fa-search"></i>
					</a> <a id="hide-content-button" href="javascript:hideProjectContent();"
						class="post-button btn btn-primary"  title="개설 취소하기"> <i class="fa fa-pencil"></i>
					</a>
						<button id='project-ok' class="post-button btn btn-primary" title="프로젝트 올리기">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
				<input value="0" id ="category" name="category" type="hidden"/> 	
				<input name="tags" type="hidden" id="tag-hidden"/>
			</form>

				<table id="project-table" class="table table-hover">
					<tbody>
						<c:forEach items="${projects}" var="project">
							<tr><td class="td-post-writer-img" rowspan="2"><a
									href="/${project.creatorName}"> <img src="${project.getImgSrc()}"></a></td>
								<td colspan="2" class="post-top-title"><a
									class="a-post-title" href="/project/${project.name}/">
									<c:if test="${!project.isForkProject()}">
										 <i class="fa fa-bookmark"></i>
									 </c:if>
									 <c:if test="${project.isForkProject()}">
										 <i class="fa fa-code-fork"></i>
									 </c:if>
									 &nbsp;${project.name} ~
										&nbsp;${fn:substring(cov:htmlEscape(project.description),0,100-fn:length(project.name))}
								</a></td>
								<td class="td-button" rowspan="2">
								 <c:if test="${project.category == 0}">
										<a href="/project/${project.name}/push"> <span
											class="span-button">${project.push}<p class="p-button">추천</p>
										</span>
										</a>
									</c:if>
								<c:if test="${project.category == 1}">
										<span
											class="span-button"><i class="fa fa-lock"></i>
												<p class="p-button">비공개</p> </span>
									</c:if>
								<c:if test="${project.category == -1}">
										<a href="/project/${project.name}"> <span
											class="span-button"><i class="fa fa-code-fork"></i>
												<p class="p-button">파생</p> </span>
										</a>
									</c:if>	
								<c:if test="${project.category == 3}">
										<span
											class="span-button"><i class="fa fa-university"></i>
												<p class="p-button">과제</p> </span>
									</c:if>		
									</td>
								<td class="td-button" rowspan="2"><sec:authorize
										access="isAnonymous()">
										<a href="/project/${project.name}/join"> <span
											class="span-button"><i class="fa fa-times"></i>
												<p class="p-button">가입</p></span>
										</a>
									</sec:authorize> <sec:authorize access="isAuthenticated()">
										<c:if
											test="${project.isJoin() == 0}">
											<a href="/project/${project.name}/join"> <span
												class="span-button"><i class="fa fa-times"></i>
													<p class="p-button">미가입</p></span>
											</a>
										</c:if>
										<c:if
											test="${project.isJoin() == 1}">
											<a href="/project/${project.name}"> <span
												class="span-button"><i class="fa fa-user"></i>
													<p class="p-button">회원</p></span>
											</a>
										</c:if>
										<c:if test="${project.isJoin () == 2}">
											<a href="/project/${project.name}"> <span
												class='span-button'><i class="fa fa-user"></i>
													<p class='p-button'>관리자</p></span></a>
										</c:if>
									</sec:authorize></td>
							</tr>
							<tr>
								<td class="post-bottom"><a href="/${project.creatorName}"><b>${project.creatorName}</b></a>
									${project.getOpeningDateFormat()}</td>
								<td class="post-bottom-tag"><c:forEach
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

				<div class="text-center">
					<div id="page-pagination"></div>

			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>
