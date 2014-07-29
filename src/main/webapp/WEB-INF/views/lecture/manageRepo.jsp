<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${lecture.name}~ ${lecture.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/repoBrowser.js"></script>
</head>
<body>
	<script>
var repoList = new Array();
var lectureName = "${lecture.name}";

<c:forEach items="${lecture.repos}" var="repo">
repoList.push({
	"name": "${repo.name}",
	"openDate": "${repo.getOpeningDateFormat()}",
	"description": "${repo.description}",
	"d-day": "${repo.getDDay()}"
});
</c:forEach>


var editorMode = false;
		$(document).ready(function() {

			$('#tags-input').textext()[0].tags().addTags(
					getTagList("/tags:<c:forEach items='${lecture.tags}' var='tag'>	${tag},</c:forEach>"));

			
			makeNavigationInManageRepo(repoList.length,10);
			
			showRepoList(1);
			
			$("select").selectpicker({
				style : 'btn-inverse',
				menuStyle : 'dropdown-inverse'
			});
			
			$('#repo-ok-button').click(function(){
				var objPattern = /^[a-zA-Z0-9]+$/;
				var name = $('#repo-name-input').val();
				var description = $('#repo-description').val();
				var period = $('input:radio[name="period"]:checked').val();

				if(name.length == 0){
					$("alert").append("<div class='alert alert-error'>"+
							  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
							  "<strong>경고!</strong> 숙제 저장소의 이름을 입력하시지 않았습니다. 제대로 입력해주세요!"+
							"</div>");
					return;
				}else if(!objPattern.test(name)){
					$("alert").append("<div class='alert alert-error'>"+
							  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
							  "<strong>경고!</strong> 숙제 저장소의 이름은 영문 숙자 조합이어야 합니다. 다시 입력해주세요!"+
							"</div>");
					return;
				}
				else if(description.lenght == 0){
					$("alert").append("<div class='alert alert-error'>"+
							  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
							  "<strong>경고!</strong> 숙제 저장소를 소개하시지 않았습니다. 제대로 입력해주세요!"+
							"</div>");
					return;
				}
				$.ajax({
		               type: "POST",
		               url: "/lecture/${lecture.name}/repo/add",
		               data: 'name='+name+'&description='+description+'&period='+period,
		               success: function(msg){
		            	   window.location = "/lecture/${lecture.name}/repo/"+name+"/browser";
		               }
		         });
			});

			$("li.page").click(function(){
				var allSize = $("li.page").size();
				var selectIndex = Number($(this).text());
				
				  $('li.page').removeClass('active'); 
				  $(this).addClass('active');
				  $("a.page-link").hide();
				  if(allSize <= 5){
					  for(var i = 0 ; i<5; i++){
					  	$("a.page-link:eq("+i+")").show();
					  }
				  }
				  else if(allSize - selectIndex < 4){
					  for(var i = 0 ; i<5; i++){
						  var j = allSize -i -1;
					  	$("a.page-link:eq("+j+")").show();
					  }
				 }
				  else{
					  for(var i = 0 ; i<5; i++){
						  var j = selectIndex + i -2;
						  if(j == -1)
							  j=4;
					  	$("a.page-link:eq("+j+")").show();
					  }
				  }
			});

			$("a.fui-arrow-left").click(function(){
				$('#pageNavigation > li,li.page').removeClass('active'); 
				$('li.page:first').addClass('active');
				$("a.page-link").hide();
				  for(var i = 0 ; i<5; i++)
				  	$("a.page-link:eq("+i+")").show();
				showWeaverList($("li.page:first").text());

			});

			$("a.fui-arrow-right").click(function(){
				var allSize = $("li.page").size();	
				$('#pageNavigation > li,li.page').removeClass('active'); 
				$('li.page:last').addClass('active');
				$("a.page-link").hide();
				for(var i = allSize-5; i < allSize ; i++)
					  	$("a.page-link:eq("+i+")").show();	
				showWeaverList($("li.page:last").text());
			
			});
			
		});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<alert></alert>
			<h5>
				<big><big><i class="fa fa-book"></i> ${lecture.name}</big></big> <small>${lecture.description}</small>
			</h5>
		</div>

		<div class="row">

			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/lecture/${lecture.name}/">예제소스</a></li>
					<li><a href="/lecture/${lecture.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);" onclick="openWindow('/lecture/${lecture.name}/chat', 400, 500);">채팅</a></li>
					<li class="active"><a href="/lecture/${lecture.name}/repo">숙제
							저장소</a></li>
					<li><a href="/lecture/${lecture.name}/weaver">수강생</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-link"></i></span> <input
						value="http://forweaver.com/${lecture.name}/example.git"
						type="text" class="input-block-level">
				</div>
			</div>

			<div class="title-write-span">
				<div style="width: 0px;" class="span1"></div>
				<div class="span4">
					<input class="span4" placeholder="숙제명을 입력해주세요!"
						id="repo-name-input" type="text" />
				</div>
				<div class="span6">
					<p title="말풍선입니다" class="btn btn-danger">
						<span class="fui-time"></span>
					</p>
					<label class="radio radio-period"> 일주일 <input type="radio"
						name="period" value="0" data-toggle="radio"
						checked="checked">

					</label> <label class="radio radio-period"> <input type="radio"
						name="period" value="1" data-toggle="radio">
						한달
					</label> <label class="radio radio-period"> <input type="radio"
						name="period" value="2" data-toggle="radio">
						한학기
					</label> <label class="radio radio-period"> <input type="radio"
						name="period" value="3" data-toggle="radio">
						영원히
					</label>
				</div>
				<div style="margin-left: 13px" class="span1">
					<button id="repo-ok-button"
						class="post-button btn btn-primary create-lecture">
						<i class="icon-ok icon-white"></i>
					</button>
				</div>
				<div style="padding-left: 20px;" class="span11">
					<input class="title span11" placeholder="숙재를 소개해주세요!"
						id="repo-description" type="text" value="" />
				</div>

				<div class="span12">
					<table id="repoTable" class="table table-hover"></table>
					<div id="pageNavigation" class="text-center pagination"></div>
				</div>
			</div>

		</div>
		<div class="span12">
			<%@ include file="/WEB-INF/common/footer.jsp"%>
		</div>
	</div>

</body>
</html>