<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 강의페이지!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
		
	function checkLecture(){
		var objPattern = /^[a-zA-Z0-9]+$/;
		var name = $('#lecture-name').val();
		var description = $('#lecture-description').val();
		var tags = $("#tags-input").val();
		
		if(tags.length == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!"+
					"</div>");
			return false;
		}else if(!objPattern.test(name)){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 강의명은 영문-소문자 숙자 조합이어야 합니다. 다시 입력해주세요!"+
					"</div>");
			return false;
		}
		else if(name.length == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 강의명을 입력하시지 않았습니다. 강의명을 입력해주세요!"+
					"</div>");
			return false;
		}else if(description.lenght == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 강의 소개를 입력하시지 않았습니다. 강의 소개를 입력해주세요!"+
					"</div>");
			return false;
		}else{
			$("form:first").append($("input[name='tags']"));
			return true;
		}
	}
	
	function showPostContent() {
		var tags = $("#tags-input").val();
		if(tags.length == 0){
			alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
			return;
		}
		$('#page-pagination').hide();
		$('#post-table').hide();
		$('#post-ok').show();
		$('#search-button').hide();
		$('#search-div').hide();
		$('#post-div').fadeIn('slow');
		$('#show-content-button').hide();
		$('#hide-content-button').show();
		editorMode = true;
	}

	function hidePostContent() {
		$('#page-pagination').show();
		$('#post-table').show();
		$('#search-div').show();
		$('#post-div').hide();
		$('#post-ok').hide();
		$('#search-button').show();
		$('#show-content-button').show();
		$('#hide-content-button').hide();
		editorMode = false;
	}
	
	
	$(function() {
			hidePostContent();
			$('#search-button').click(
					function() {
							var tagNames = $("#tags-input").val();
							movePage(tagNames,$('#post-search-input').val());							
					});
			
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

						var pageCount = ${lectureCount+1}/${number};
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
			<form onsubmit="return checkLecture()" id="lectureForm"
				action="/lecture/add" method="post">

				<div id="post-div" class="span10">
					<input name="name" id="lecture-name" class="title span3"
						placeholder="강의명을 입력해주세요!" type="text" /> <input
						name="description" id="lecture-description" class="title span7"
						placeholder="강의에 대해 소개해주세요!" type="text" />
				</div>
				
				<div class="span2">
					<span> <a id="show-content-button"
						href="javascript:showPostContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> <a id='search-button' class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> <a id="hide-content-button" href="javascript:hidePostContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a>
						<button type="submit" id='post-ok' class="post-button btn btn-primary">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
			</form>

			<div class=" span12">
				<table id="lecture-table" class="table table-hover">
					<tbody>
						<c:forEach items="${lectures}" var="lecture">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><a href="/${lecture.creatorName}"><img
									src="${lecture.getImgSrc()}"></a></td>
								<td colspan="2" class="post-top-title"><a
									class="a-post-title" href="/lecture/${lecture.name}"> <i
										class='fa fa-university'></i>&nbsp;${lecture.name} ~
										&nbsp;${fn:substring(cov:htmlEscape(lecture.description),0,100-fn:length(lecture.name))}
								</a></td>
								<td class="td-button" rowspan="2"><sec:authorize
										access="isAnonymous()">
										<a href="/lecture/${lecture.name}/join"> <span
											class="span-button"><i class="fa fa-times"></i>
												<p class="p-button">미가입</p></span>
										</a>
									</sec:authorize> <sec:authorize access="isAuthenticated()">
										<c:if
											test="${lecture.isJoin() == 0}">
											<a href="/lecture/${lecture.name}/join"> <span
												class="span-button"><i class="fa fa-times"></i>
													<p class="p-button">미가입</p></span>
											</a>
										</c:if>
										<c:if
											test="${lecture.isJoin() == 1}">
											<a href="/lecture/${lecture.name}"> <span
												class="span-button"><i class="fa fa-graduation-cap"></i>
													<p class="p-button">학생</p></span>
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
										<span title="태그를 클릭해보세요. 태그가 추가됩니다!"
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