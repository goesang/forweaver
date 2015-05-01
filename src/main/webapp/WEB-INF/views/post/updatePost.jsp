<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 소통해보세요!</title>

<%@ include file="/WEB-INF/includes/src.jsp"%>
<link rel="stylesheet" type="text/css" href="/resources/forweaver/css/bootstrap-markdown.min.css"/>
<script src="/resources/forweaver/js/markdown/markdown.js"></script>
<script src="/resources/forweaver/js/markdown/bootstrap-markdown.js"></script>
<script src="/resources/forweaver/js/markdown/to-markdown.js"></script>
</head>
<body>
	<script type="text/javascript">
	editorMode = true;

	function checkPost(){

		var tags = $("#tags-input").val();
		if(tags.length == 0){
			return false;
		}else if($('#post-title-input').val().length < 5){
			alert("제목을 최소 5글자 이상 입력해주세요!");
			return false;
		}else if($('#post-content-textarea').val().length < 5){
			alert("내용을 최소 5글자 이상 입력해주세요!");
			return false;
		}else{
			$("form:first").append($("input[name='tags']"));
			return true;
		}
	}
	
		
		$(document).ready(function() {

			move = false;
			<c:forEach items='${post.tags}' var='tag'>
			$('#tags-input').tagsinput('add',"${tag}");
			</c:forEach>
			move = true;
				});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header page-header-none">
		<alert></alert>
			<h5>
				<big><big><i class=" fa fa-comments"></i> 소통해보세요!</big></big> 
				<small>프로젝트 진행사항이나 궁금한 점들을 올려보세요!</small>
			</h5>
		</div>
		<div class="row">

			<div class=" span12">
			<form id="postForm" onsubmit="return checkPost()" 
					action="/community/${post.postID}/update"
				enctype="multipart/form-data" method="post">
					<div class="span10">
						<input name="title" id="post-title-input" class="title span10" placeholder="쓰고 싶은 단문의 내용을 입력하거나 글의 제목을 입력해주세요!"
							type="text" value="${post.title}" />
					</div>
					<div class="span1">
						<span> 
							<button id='post-ok' class="post-button btn btn-primary">
								<i class="fa fa-check"></i>
							</button>
							
						</span>
					</div>
					
					<div class="span11">
						<textarea data-provide="markdown" style="height:250px"
							id="post-content-textarea" name ="content" class="span11 post-content"
							 placeholder="글 내용을 입력해주세요!">${post.content}</textarea>
					</div>
					</form>
				<div id="page-pagination" class="text-center pagination"></div>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>