<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>Forweaver : 소통해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">
	var fileCount = 1;
	
	function checkPost(){
		var fileArray = [];
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
		
		if($('#post-title-input').val() == ""){
			alert("아무것도 입력하시지 않았습니다!");
			return false;
		}else
			return true;
	}
	
		function showPostContent() {
			$('#page-pagination').hide();
			$('#post-table').hide();
			$('#post-content-textarea').fadeIn('slow');

			$('#show-content-button').hide();
			$('#hide-content-button').show();
			$('.file-div').fadeIn('slow');
			editorMode = true;
		}

		function hidePostContent() {
			$('#page-pagination').show();
			$('#post-table').show();
			$('#post-content-textarea').hide();

			$('#show-content-button').show();
			$('#hide-content-button').hide();
			$('.file-div').hide();
			editorMode = false;
		}

		$(function() {
			
			hidePostContent();
			
			$(".file-div").append("<div class='fileinput fileinput-new' data-provides='fileinput'>"+
					  "<div class='input-group'>"+
					    "<div class='form-control' data-trigger='fileinput'><i class='icon-file '></i> <span class='fileinput-filename'></span></div>"+
					    "<span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>"+
					    "<i class='icon-upload icon-white'></i></span><span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>"+
						"<input onchange ='fileUploadChange(this);' type='file' id='file1' multiple='true' name='files[0]'></span>"+
					   "<a href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>"+
					  "</div>"+
					"</div>");
			
			
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
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
								if (!exist){
									movePage(tagNames.substring(0,
											tagNames.length - 1)
											+ ",\"" + tagname + "\"]","");
								}
							});
					
					$('#search-button').click(
							function() {
									var tagNames = $("input[name='tags']").val();
									movePage(tagNames,$('#post-title-input').val());							
							});
					
					$('#post-ok').click(function(){
						var title = encodeURI(spacialSignEncoder($('#post-title-input').val()));
						if(title.length == 0){
							alert("글 제목을 입력하시지 않았습니다. 글 제목을 입력해주세요!");
							return;
						}
						if(editorMode)
							content = encodeURI(spacialSignEncoder($('#post-content-textarea').val()));
						
					});
					
					$('#post-title-input').val(getSearchWord(document.location.href));
					
					$('#post-title-input').keyup(
							function(e) {
								if(!editorMode && e.keyCode == 13){
									var tagNames = $("input[name='tags']").val();
									movePage(tagNames,$('#post-title-input').val());
								}
							});
					var pageCount = ${postCount+1}/${number};
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
					    "<i class='icon-upload icon-white'></i></span><span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>"+
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
		<div class="page-header">
		<alert></alert>
		<h5 style="text-align: center">
			<img style="height: 50px; width: 50px;" class="img-polaroid"
				src="${weaver.getImgSrc()}">
		</h5>
		<h5 style="text-align: center">

			<big><big><b>${weaver.getId()}</b></big></big> <small>"${weaver.getSay()}"</small>
		</h5>
		<div class="row">
			<div class="span12">
				<ul class="nav nav-tabs" id="myTab">
					<li id="age-desc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<c:if test="${massage == null }">
						<li id="push-desc"><a
							href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-desc/page:1">추천순</a></li>
					</c:if>
					<li id="repost-desc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1">최신
							답변순</a></li>
					<li id="repost-many"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1">많은
							답변순</a></li>
					<li id="age-asc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>
					<li id="repost-null"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-null/page:1">답변
							없는 글</a></li>
				</ul>
			</div>

			<form id="postForm" onsubmit="return checkPost()" action="/community/add"
				enctype="multipart/form-data" method="post">

				<div class="span9">
					<input name="title" id="post-title-input" class="title span9"
						placeholder="찾고 싶은 검색어나 쓰고 싶은 단문의 내용을 입력해주세요!" type="text" />
				</div>
				<div class="span3">
					<span> <a id='search-button'
						class="post-button btn btn-primary"> <i
							class="icon-search icon-white"></i>
					</a> <a id="show-content-button" href="javascript:showPostContent();"
						class="post-button btn btn-primary"> <i
							class="icon-edit icon-white"></i>
					</a> <a id="hide-content-button" href="javascript:hidePostContent();"
						class="post-button btn btn-primary"> <i
							class="icon-edit icon-white"></i>
					</a>
						<button id='post-ok' class="post-button btn btn-primary">
							<i class="icon-ok icon-white"></i>
						</button>

					</span>
				</div>
				<div class="span12">


					<textarea name="content" id="post-content-textarea"
						class="post-content span12" onkeyup="textAreaResize(this)"
						placeholder="글 내용을 입력해주세요!"></textarea>
					<div class="file-div"></div>

				</div>


			</form>

			<div class="span12">

				<table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items="${posts}" var="post">
							<tr>
								<td colspan="2" class="post-top-title"><a
									class="a-post-title" href="/community/${post.postID}"> <c:if
											test="${post.isLong()}">
											<i class=" icon-align-justify"></i>
										</c:if> <c:if test="${!post.isLong()}">
											<i class="fa fa-comment"></i>
										</c:if> &nbsp;${post.title}
								</a></td>
								<td class="td-button" rowspan="2"><c:if
										test="${post.kind == 2}">
										<a href="/community/${post.postID}/delete"> <span
											class="span-button"> X
												<p class="p-button">삭제</p>
										</span>
										</a>
									</c:if> <c:if test="${post.kind != 2}">
										<a href="/community/${post.postID}/push"> <span
											class="span-button"> ${post.push}
												<p class="p-button">추천</p>
										</span>
										</a>
									</c:if></td>
								<td class="td-button" rowspan="2"><a
									href="/community/${post.postID}"> <span class="span-button">${post.rePostCount}
											<p class="p-button">답변</p>
									</span></a></td>
							</tr>
							<tr>
								<td class="post-bottom"><b>${post.writerName}</b>
									${post.getFormatCreated()}</td>
								<td class="post-bottom-tag"><c:forEach items="${post.tags}"
										var="tag">
										<span
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('@') && tag.contains('/')}">
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