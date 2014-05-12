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

						$('#project-ok-button').click(function(){
							var objPattern = /^[a-zA-Z0-9]+$/;
							var name = $('#project-name-input').val();
							var description = $('#project-description').val();
							var period = $('#project-period-select').val();
							var category = $('#project-category-select').val();
							var tags = $("input[name='tags']").val();
							tags = tagInputValueConverter(eval(tags));
							
							if(tags.length == 0){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!"+
										"</div>");
								return;
							}else if(name.length == 0){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 프로젝트명을 입력하시지 않았습니다. 프로젝트명을 입력해주세요!"+
										"</div>");
								return;
							}else if(!objPattern.test(name)){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 프로젝트명은 영문 숙자 조합이어야 합니다. 다시 입력해주세요!"+
										"</div>");
								return;
							}
							else if(description.lenght == 0){
								$("alert").append("<div class='alert alert-error'>"+
										  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
										  "<strong>경고!</strong> 프로젝트 소개를 입력하시지 않았습니다. 강의 소개를 입력해주세요!"+
										"</div>");
								return;
							}
							$.ajax({
					               type: "POST",
					               url: "/project/add",
					               data: 'name='+name+'&description='+description+'&tags='+tags+'&period='+period+'&category='+category,
					               success: function(msg){
					            	   var tagNames = $("input[name='tags']").val();
					            	   movePage(tagNames,"");
					               }
					         });
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

					        $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
				});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header">
		<alert></alert>
			<h5>
				<big><big><i class=" fa fa-bookmark"></i> 참여해보세요!</big></big> <small>같이 진행할 동료를 구하거나 흥미로운
					프로젝트에 참여해보세요!</small>
					<div style="margin-top:-10px" class="pull-right">

				<button class="btn btn-warning">
								<b>COUNT : ${projectCount}</b>
				</button>

			</div>
			</h5>
		</div>
		<div class="row">	

			<div class="span2">
				<ul class="nav nav-pills nav-stacked">
					<li class="active" id="age-desc"><a data-toggle="tab" href="#project"><i class=" fa fa-search"></i> 프로젝트 찾기</a></li>
					<li id="push-desc"><a data-toggle="tab" href="#projectCreate"><i class=" fa fa-plus"></i> 프로젝트 생성</a></li>
				</ul>
			</div>
			<div class="tab-content span10">
				<div class="tab-pane active" id="project">
				<table id="project-table" class="table table-hover">
					<tbody>
						<c:forEach items="${projects}" var="project">
							<tr>
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
								<td class="td-button" rowspan="2"><c:if
										test="${project.tmpPermission == 0}">
										<a href="/project/${project.name}/join"> <span
											class="span-button"><i class="fa fa-times-circle"></i>
											<p class="p-button">가입</p></span>
										</a>
									</c:if> <c:if test="${project.tmpPermission == 1}">
										<a href="/project/${project.name}/"> <span
											class="span-button"><i class="fa fa-dot-circle-o"></i>
											<p class="p-button">가입</p></span>
										</a>
									</c:if> <sec:authorize ifAnyGranted="ROLE_USER">
										<c:if test="${project.creatorName == currentUser.username}">
											<a href="/project/${project.name}/"> <span
												class='span-button'><i class='icon-user icon-white'></i>
													<p class='p-button'>괸리자</p>" </span>
											</a>
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
				<div class="tab-pane" id="projectCreate">
					
					<div class="span4 create-project"
						style="margin-left: 0px;">

						<input class="title span4" placeholder="프로젝트명을 입력해주세요!"
							id="project-name-input" type="text" value="" />
					</div>

					<div class="span3 create-project">
						<select id="project-category-select">
							<option disabled="disabled">프로젝트 공개여부</option>
							<option value="0">공개</option>
							<option value="1" selected="selected">비공개</option>
							<option value="2">완전 비공개</option>
						</select>
					</div>
					<div class="span3 create-project">
						<select id="project-period-select">
							<option disabled="disabled">프로젝트 기간</option>
							<option value="0">한 달</option>
							<option value="1" selected="selected">반 년</option>
							<option value="2">일 년</option>
							<option value="3">영원히</option>
						</select>
					</div>
					
					<div style="margin-left:0px;"class="span9">	
					<input class= "span9" type="text" id="project-description" 
						placeholder="프로젝트에 대해 설명해주세요!"></input>
					</div>
					<div style="margin-left:-7px;" class="span1">
					<span>
						<button id="project-ok-button"
							type="submit" class="span1 post-button btn btn-primary create-project">
							<i class="icon-ok icon-white"></i>
						</button> 
					</span>
					</div>
					
					<div class="span9 well-white">
					<div class="span3">
					<embed src="/resources/svg/noun_project_13647.svg" width="170"
						height="170" type="image/svg+xml" />
					</div>
					<div class="span5">												
						<li>컴퓨터 프로그램을 어떠한 목적으로든지 사용할 수 있다. 다만 법으로 제한하는 행위는 할 수 없다.</li>
						<li>컴퓨터 프로그램의 실행 복사본은 언제나 프로그램의 소스 코드와 함께 판매하거나 소스코드를 무료로 배포해야 한다.</li>
						<li>컴퓨터 프로그램의 소스 코드를 용도에 따라 변경할 수 있다.</li>
						<li>변경된 컴퓨터 프로그램 역시 프로그램의 소스 코드를 반드시 공개 배포해야 한다.</li>
						<li>변경된 컴퓨터 프로그램 역시 반드시 똑같은 라이선스를 취해야 한다. 즉 GPL 라이선스를 적용해야 한다.</li>						
					</div>		
					
					</div>			
			</div>
		</div>
		<div class="span12">	
		<%@ include file="/WEB-INF/common/footer.jsp"%>
		</div>
	</div>
</div>
</body>


</html>