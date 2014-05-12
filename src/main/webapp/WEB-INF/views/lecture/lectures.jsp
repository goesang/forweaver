<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>Forweaver : 강의페이지!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script>
		
		$(document).ready(function() {
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

						$("select").selectpicker({
							style : 'btn-inverse',
							menuStyle : 'dropdown-inverse'
						});

						$('#lecture-ok-button').click(function(){
							var objPattern = /^[a-zA-Z0-9]+$/;
							var name = $('#lecture-name-input').val();
							var description = $('#lecture-description').val();
							var period = $('#lecture-period-select').val();
							var category = $('#lecture-category-select').val();
							var tags = $("input[name='tags']").val();
							tags = tagInputValueConverter(eval(tags));
							if(tags.length == 0){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!"+
										"</div>");
								return;
							}else if(!objPattern.test(name)){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 강의명은 영문 숙자 조합이어야 합니다. 다시 입력해주세요!"+
										"</div>");
								return;
							}
							else if(name.length == 0){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 강의명을 입력하시지 않았습니다. 강의명을 입력해주세요!"+
										"</div>");
								return;
							}else if(description.lenght == 0){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 강의 소개를 입력하시지 않았습니다. 강의 소개를 입력해주세요!"+
										"</div>");
								return;
							}

							$.ajax({
					               type: "POST",
					               url: "/lecture/add",
					               data: 'name='+name+'&description='+description+'&tags='+tags+'&period='+period+'&category='+category,
					               success: function(msg){
					            	   var tagNames = $("input[name='tags']").val();
					            	   movePage(tagNames,"");
					               }
					         });
						});
							
						var pageCount = ${lectureCount}/10;
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
				<big><big><i class=" fa fa-book"></i> 수강해보세요!</big></big>
				<small>강의를 찾아 수강 신청을 해보거나 수업에 필요한 저장소를 만들어보세요!</small>
				<div style="margin-top:-10px" class="pull-right">
							<button class="btn btn-warning">
											<b>COUNT : ${lectureCount}</b>
							</button>
				</div>
			</h5>
		</div>
		<div class="row">

			<div class=" span12">
				<div class="span10">
					
					<div class="span4 create-lecture"
						style="margin-left: 0px;">

						<input class="title span4" placeholder="강의명을 입력해주세요!"
							id="lecture-name-input" type="text" value="" />
					</div>

					<div class="span3 create-lecture">
						<select id="lecture-category-select">
							<option disabled="disabled">강의 공개여부</option>
							<option value="0">공개</option>
							<option value="1" selected="selected">비공개</option>
							<option value="2">완전 비공개</option>
						</select>
					</div>
					<div class="span3 create-lecture">
						<select id="lecture-period-select">
							<option disabled="disabled">강의 기간</option>
							<option value="0">한 달</option>
							<option value="1" selected="selected">한 학기</option>
							<option value="2">일 년</option>
							<option value="3">영원히</option>
						</select>
					</div>
				</div>
				<div class="span1">
					<span>
						<button id="lecture-ok-button"
							type="submit" class="post-button btn btn-primary create-lecture">
							<i class="icon-ok icon-white"></i>
						</button>
					</span>
				</div>
				<div class="span11">
					<input class= "span11" type="text" id="lecture-description" 
						placeholder="강의에 대해 설명해주세요!"></input>
				</div>

				<table id="lecture-table" class="table table-hover">
					<tbody>
						<c:forEach items="${lectures}" var="lecture">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${lecture.getImgSrc()}">
								</td>
								<td  colspan="2" class="post-top-title"><a class="a-post-title"
									href="/lecture/${lecture.name}"> <i class='icon-book'></i>
										&nbsp;${lecture.name} ~
										&nbsp;${fn:substring(lecture.description,0,100-fn:length(lecture.name))}
								</a></td>
								<td class="td-button" rowspan="2">
									<c:if test="${lecture.category != 0}">
								<a href="/lecture/${lecture.name}/join"> 
									<span class="span-button"><i class="fa fa-key"></i><p class="p-button">비공개</p>
									</span>
									</a>
								</c:if>
									
									<c:if test="${lecture.category == 0}">
									<a href="/lecture/${lecture.name}"> 
									<span class="span-button">${lecture.push}<p class="p-button">추천</p>
									</span>
								</a>
								</c:if>
								</td>
								<td class="td-button" rowspan="2">
								<c:if test="${lecture.tmpPermission == 0}">
								<a	href="/lecture/${lecture.name}/join"> 
								<span class="span-button"><i class="fa fa-times-circle"></i><p class="p-button">가입</p></span>
								</a>
								</c:if>
								<c:if test="${lecture.tmpPermission == 1}">
								<a	href="/lecture/${lecture.name}">
								 <span class="span-button"><i class="fa fa-dot-circle-o"></i></i><p class="p-button">가입</p></span>
								 </a>
								</c:if>
								<sec:authorize ifAnyGranted="ROLE_USER">
									<c:if test="${lecture.creatorName == currentUser.username}">
									<a	href="/lecture/${lecture.name}">
									<span class='span-button'><i class="fa fa-user"></i>
									<p class='p-button'>괸리자</p>"
									</span>
									</c:if>
								</sec:authorize>
								</td>
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${lecture.creatorName}</b>
								${lecture.getOpeningDateFormat()}
								</td>	
								<td class="post-bottom-tag">
									<c:forEach	items="${lecture.tags}" var="tag">
										<span
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('$')}">
										tag-massage
										</c:if>
										">${tag}</span>
									</c:forEach>
					
									</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class = "text-center">
					<div id="page-pagination"></div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>