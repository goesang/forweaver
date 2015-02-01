<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 강의페이지!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
	function showPostContent() {
		$('#page-selection').hide();
		$('#post-table').hide();
		$('#post-ok').show();
		$('#search-button').hide();
		$('#search-div').hide();
		$('#post-div').fadeIn('slow');
		$('#show-write-button').hide();
		$('#hide-write-button').show();
		//editorMode = true;
	}

	function hidePostContent() {
		$('#page-selection').show();
		$('#post-table').show();
		$('#search-div').show();
		$('#post-div').hide();
		$('#post-ok').hide();
		$('#search-button').show();
		$('#show-write-button').show();
		$('#hide-write-button').hide();
		//editorMode = false;
	}
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/views/common/nav.jsp"%>
		<div class="page-header page-header-none">
			<alert></alert>
			<h6>
				<i class=" fa fa-university"></i> 수강해보세요!<small class="hidden-xs">  <small>강의를
					찾아 수강 신청을 해보거나 수업에 필요한 저장소를 만들어보세요!</small></small>
				<div style="margin-top: -10px" class="pull-right">
					<button class="btn btn-warning">
						<b><b><i class="fa fa-database"></i> ${lectureCount}</b></b>
					</button>
				</div>
			</h6>
		</div>
		<div class="row">
			<div id="search-div" class="col-md-10">
				<input id="post-search-input" class="form-control col-md-10"
					placeholder="검색어를 입력하여 강의를 찾아보세요!" type="text" />
			</div>
			<form onsubmit="return checkLecture()" id="lectureForm"
				action="/lecture/add" method="post">
				

				<div id="post-div" class="col-md-10" style="display:none;">
					<input name="name" id="lecture-name" class="form-control col-md-4" style="width: 33.33333333%;"
						placeholder="강의명을 입력해주세요!" type="text" /> <input
						name="description" id="lecture-description" class="form-control col-md-8" style="width: 66.66666667%;"
						placeholder="강의에 대해 소개해주세요!" type="text" />
				</div>
				
				<div class="col-md-2">
					<span> <a id="show-write-button"
						href="javascript:showPostContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> <a id='search-button' class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> <a id="hide-write-button" href="javascript:hidePostContent();" style="display:none;"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a>
						<button type="submit" id='post-ok' class="post-button btn btn-primary" style="display:none;">
							<i class="fa fa-check"></i>
						</button>
					</span>
				</div>
			</form>

			<div class="col-md-12">
				<table id="forweaver-table" class="table table-hover">
					<tbody>
						<c:forEach items="${lectures}" var="lecture">
							<tr>
								<td class="forweaver-td-avatar" rowspan="2"><img
									src="${lecture.getImgSrc()}"></td>
								<td colspan="2" class="post-top-title"><a
									class="none-color" href="/lecture/${lecture.name}"> <i
										class='fa fa-university'></i>&nbsp;${lecture.name} ~
										&nbsp;${fn:substring(lecture.description,0,100-fn:length(lecture.name))}
								</a></td>
								<td class="forweaver-td-span" rowspan="2"><sec:authorize
										ifNotGranted="ROLE_USER">
										<a href="/lecture/${lecture.name}/join"> <span
											class="forweaver-td-span"><i class="fa fa-times"></i>
												<p class="p-button">미가입</p></span>
										</a>
									</sec:authorize> <sec:authorize ifAnyGranted="ROLE_USER">
										<c:if
											test="${!lecture.isJoin() && lecture.creatorName != currentUser.username}">
											<a href="/lecture/${lecture.name}/join"> <span
												class="forweaver-td-span"><i class="fa fa-times"></i>
													<p class="p-button">미가입</p></span>
											</a>
										</c:if>
										<c:if
											test="${lecture.isJoin() && lecture.creatorName != currentUser.username}">
											<a href="/lecture/${lecture.name}"> <span
												class="forweaver-td-span"><i class="fa fa-graduation-cap"></i>
													<p class="p-button">학생</p></span>
											</a>
										</c:if>
										<c:if test="${lecture.creatorName == currentUser.username}">
											<a href="/lecture/${lecture.name}"> <span
												class='forweaver-td-span'><i class="fa fa-user"></i>
													<p class='p-button'>관리자</p>" </span>
											</a>
										</c:if>
									</sec:authorize></td>
							</tr>
							<tr>
								<td class="forweaver-td-info none-boder-top"><b>${lecture.creatorName}</b>
									${lecture.getOpeningDateFormat()}</td>
								<td class="forweaver-td-tags none-boder-top"><c:forEach
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
					<div id="page-selection"></div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>

</body>
</html>