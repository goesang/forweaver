<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 소통해보세요!</title>

<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">
	editorMode = true;
	var fileCount = 1;
	var fileArray = [];
	<c:forEach items="${post.dataNames}" var="dataName">
		fileArray.push('${dataName}');
	</c:forEach>
	function checkRePost(){

		for(var i=0;i<fileCount;i++){
			var fileName = $("#file"+(i+1)).val();
			
			if(fileName.indexOf("C:\\fakepath\\") != -1)
				fileName = fileName.substring(12);
			
			if(containsObject(fileArray,fileName)){
				alert("중복되는 파일이 있습니다!");
				return false;
			}
			else
				fileArray[i] = fileName;
			
		}
		
		var tags = $("input[name='tags']").val();
		if(tags.length == 2){
			return false;
		}else if($('#post-title-input').val() == ""){
			alert("아무것도 입력하시지 않았습니다!");
			return false;
		}else{
			return true;
		}
	}
	
		function removeFile(fileInputNumber,fileName){
			if(!confirm("정말 파일을 삭제하시겠습니까?"))
				return;
			$('#fileInputDiv'+fileInputNumber).remove();
			$('#fileRemoveInput').val($('#fileRemoveInput').val()+'@#@'+fileName);
			for(var i =0 ; i< fileArray.size;i++){
				if(fileArray[i] == fileName)
					fileArray.remove(i);
			}
		}
	
		function showPostContent() {
			$('#page-pagination').hide();
			$('#post-table').hide();
			$('#post-content-textarea').fadeIn('slow');
			$('#show-content-button').hide();
			$('#hide-content-button').show();
		}

		function hidePostContent() {
			$('#page-pagination').show();
			$('#post-table').show();
			$('#post-content-textarea').hide();
			$('#post-content-textarea').val("");
			$('#show-content-button').show();
			$('#hide-content-button').hide();

		}
		
		
		$(document).ready(function() {
					
			<c:forEach items="${post.dataNames}" var="dataName" varStatus="status">
			$(".file-div").append("<div id = 'fileInputDiv${status.count}' class='fileinput fileinput-exists' data-provides='fileinput'>"+
					"<div class='input-group'><div class='form-control'><i class='icon-file '></i> "+
					"<span class='fileinput-filename'>${dataName}</span></div>"+
					"<a href='javascript:removeFile(${status.count},\"${dataName}\")' class='input-group-addon btn btn-primary fileinput-exists' ><i class='icon-remove icon-white'></i></a></div></div>");
			</c:forEach>
			
			$(".file-div").append("<div class='fileinput fileinput-new' data-provides='fileinput'>"+
					  "<div class='input-group'>"+
					    "<div class='form-control' data-trigger='fileinput'><i class='icon-file '></i> <span class='fileinput-filename'></span></div>"+
					    "<span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>"+
					    "<i class='fa fa-arrow-circle-o-up icon-white'></i></span><span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>"+
						"<input onchange ='fileUploadChange(this);' type='file' id='file1' multiple='true' name='files[0]'></span>"+
					   "<a href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>"+
					  "</div>"+
					"</div>");
			
			<c:if test="${post.isLong()}">
			showPostContent();
			</c:if>
			$('#tags-input').textext()[0].tags().addTags(
					getTagList("/tags:<c:forEach items='${post.tags}' var='tag'>	${tag},</c:forEach>"));
				});
		
		function fileUploadChange(fileUploader){
			var fileName = $(fileUploader).val();
			
			$(function (){
			if(fileName !=""){ // 파일을 업로드하거나 수정함
				if(fileName.indexOf("C:\\fakepath\\") != -1)
					fileName = fileName.substring(12);
				
				if(imgCheck(fileName))
					$("#post-content-textarea").val($("#post-content-textarea").val()+"[tmpimg "+fileName+"]");
					
				if(fileUploader.id == "file"+fileCount){ // 업로더의 마지막 부분을 수정함
			fileCount++;
			$(".file-div").append("<div class='fileinput fileinput-new' data-provides='fileinput'>"+
					  "<div class='input-group'>"+
					    "<div class='form-control' data-trigger='fileinput'><i class='icon-file '></i> <span class='fileinput-filename'></span></div>"+
					    "<span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>"+
					    "<i class='fa fa-arrow-circle-o-up icon-white'></i></span><span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>"+
						"<input onchange ='fileUploadChange(this);' type='file' multiple='true' id='file"+fileCount+"' name='files["+(fileCount-1)+"]'></span>"+
					   "<a id='remove-file' href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>"+
					  "</div>"+
					"</div>");
				}
			}else{
				if(fileUploader.id == "file"+(fileCount-1)){ // 업로더의 마지막 부분을 수정함
					
				$("#file"+fileCount).parent().parent().remove();

					--fileCount;
			}}});
		}
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
			<form id="postForm" onsubmit="return checkRePost()" 
					action="/community/${post.postID}/update"
				enctype="multipart/form-data" method="post">
					<div class="span9">
						<input id="post-title-input" class="title span9" placeholder="쓰고 싶은 단문의 내용을 입력하거나 글의 제목을 입력해주세요!"
							type="text" value="${post.title}" />
					</div>
					<div class="span2">
						<span> 							
						<a id="show-content-button"
							href="javascript:showPostContent();"
							class="post-button btn btn-primary"> <i
								class="icon-edit"></i>
						</a> <a style="display: none;" id="hide-content-button"
							href="javascript:hidePostContent();"
							class="post-button btn btn-primary"> <i
								class="icon-edit"></i>
						</a>
							<button id='post-ok' class="post-button btn btn-primary">
								<i class="fa fa-check"></i>
							</button>
							
						</span>
					</div>
					
					<div class="span11">
						<textarea style="display: none;" 
							id="post-content-textarea" class="span11 post-content"
							onkeyup="textAreaResize(this)" placeholder="글 내용을 입력해주세요!">${post.content}</textarea>
					</div>
					<div class = "file-div"></div>
					<input id ='fileRemoveInput' style="display: none;" name ="fileRemoveList" type="text" ></input>
					</form>
				<div id="page-pagination" class="text-center pagination"></div>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>