<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${lecture.name}~${lecture.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
		function validateForm() {
			var objPattern = /^[a-zA-Z0-9]+$/;
			var name = $('#repo-name-input').val();
			var description = $('#repo-description').val();

			if (name.length == 0) {
				$("alert")
						.append(
								"<div class='alert alert-error'>"
										+ "<button type='button' class='close' data-dismiss='alert'>&times;</button>"
										+ "<strong>경고!</strong> 숙제 저장소의 이름을 입력하시지 않았습니다. 제대로 입력해주세요!"
										+ "</div>");
				return false;
			} else if (!objPattern.test(name)) {
				$("alert")
						.append(
								"<div class='alert alert-error'>"
										+ "<button type='button' class='close' data-dismiss='alert'>&times;</button>"
										+ "<strong>경고!</strong> 숙제 저장소의 이름은 영문 숙자 조합이어야 합니다. 다시 입력해주세요!"
										+ "</div>");
				return false;
			} else if (description.lenght == 0) {
				$("alert")
						.append(
								"<div class='alert alert-error'>"
										+ "<button type='button' class='close' data-dismiss='alert'>&times;</button>"
										+ "<strong>경고!</strong> 숙제 저장소를 소개하시지 않았습니다. 제대로 입력해주세요!"
										+ "</div>");
				return false;
			}
			return true;
		}

		var editorMode = false;
		$(document)
				.ready(
						function() {

							$('#tags-input').textext()[0]
									.tags()
									.addTags(
											getTagList("/tags:<c:forEach items='${lecture.tags}' var='tag'>	${tag},</c:forEach>"));

							$("select").selectpicker({
								style : 'btn-inverse',
								menuStyle : 'dropdown-inverse'
							});

						});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<alert></alert>
			<h5>
				<big><big><i class="fa fa-university"></i>
						${lecture.name}</big></big> <small>${lecture.description}</small>
			</h5>
		</div>

		<div class="row">

			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/lecture/${lecture.name}/">예제소스</a></li>
					<li><a href="/lecture/${lecture.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);"
						onclick="openWindow('/lecture/${lecture.name}/chat', 400, 500);">채팅</a></li>
					<li class="active"><a href="/lecture/${lecture.name}/repo">숙제
							저장소</a></li>
					<li><a href="/lecture/${lecture.name}/weaver">수강생</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://forweaver.com/${lecture.name}/example.git"
						type="text" class="input-block-level">
				</div>
			</div>

			<form onsubmit="return validateForm()" method="post" action="/lecture/${lecture.name}/add"
				class="title-write-span">
				<div style="width: 0px;" class="span1"></div>
				<div class="span3">
					<input class="span3" name = "name" placeholder="숙제명을 입력해주세요!"
						id="repo-name-input" type="text" />
				</div>
				
				<div class="span7" style="padding-top:10px;">
				<label style="display:inline" class="checkbox" for="checkbox"> 
						
						<input id="project-category-select" name=category type="checkbox" data-toggle="checkbox"> 팀 프로젝트
						</label>
					<label class="radio radio-period"> 일주일 <input type="radio"
						name="period" value="0" data-toggle="radio" checked="checked">
					</label> <label class="radio radio-period"> <input type="radio"
						name="period" value="1" data-toggle="radio"> 한달
					</label> <label class="radio radio-period"> <input type="radio"
						name="period" value="2" data-toggle="radio"> 한학기
					</label> <label class="radio radio-period"> <input type="radio"
						name="period" value="3" data-toggle="radio"> 영원히
					</label>
				</div>
				<div style="margin-left: 13px" class="span1">
					<button id="repo-ok-button"
						class="post-button btn btn-primary create-lecture">
						<i class="fa fa-check"></i>
					</button>
				</div>
				<div style="padding-left: 20px;" class="span11">
					<input class="title span11" placeholder="숙재를 소개해주세요!"
						id="repo-description"  name = "description" type="text" value="" />
				</div>
			</form>
			<div class="span12">
				<table id="repoTable" class="table table-hover">
					<c:forEach items="${lecture.repos}" var="repo">
						<c:if test="${repo.name !='example'}">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><i
									class="fa fa-bomb fa-3x"></i></td>
								<td class="post-top-title"><b><a class="a-post-title"
										href="/lecture/${lecture.name}/${repo.name}/">
											${repo.name} </a></b><small>${repo.getOpeningDateFormat()}</small></td>
								<td class="td-button" rowspan="2"><c:if
										test="${repo.getDDay() == -2}">
										<span class="span-button"><i class="fa fa-recycle"></i>
											<p class="p-button">무제한</p> </span>
									</c:if> <c:if test="${repo.getDDay() == -1}">
										<span class="span-button"><i class="fa fa-clock-o"></i>
											<p class="p-button">종료</p> </span>
									</c:if> <c:if test="${repo.getDDay() >= 0}">
										<span class="span-button">${repo.getDDay()}<p
												class="p-button">마감</p>
										</span>
									</c:if></td>
							</tr>
							<tr>
								<td class="post-bottom">${repo.description}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>

			</div>
		</div>
		<div class="span12">
			<%@ include file="/WEB-INF/common/footer.jsp"%>
		</div>
	</div>

</body>
</html>