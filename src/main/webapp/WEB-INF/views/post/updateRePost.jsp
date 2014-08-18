<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 소통해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">
	var fileCount = 1;
	var comment = 0;
	
	
	function fileUploadChange(fileUploader){
		$(function (){
		if($(fileUploader).val()!=""){ // 파일을 업로드하거나 수정함
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
	
			$(function() {
			
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
			$("#repost-content").focus();
			
			$(".file-div").append("<div class='fileinput fileinput-new' data-provides='fileinput'>"+
					  "<div class='input-group'>"+
					    "<div class='form-control' data-trigger='fileinput'><i class='icon-file '></i> <span class='fileinput-filename'></span></div>"+
					    "<span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>"+
					    "<i class='fa fa-arrow-circle-o-up icon-white'></i></span><span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>"+
						"<input onchange ='fileUploadChange(this);' type='file' id='file1' multiple='true' name='files[0]'></span>"+
					   "<a href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>"+
					  "</div>"+
					"</div>");

			
			$('#tags-input').textext()[0].tags().addTags(
					getTagList("/tags:<c:forEach items='${post.tags}' var='tag'>	${tag},</c:forEach>"));

			$('.tag-name').click(
					function() {
						var tagname = $(this).text();
						movePage("[\"" + tagname + "\"]","");	
			});
		});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="row">
			<div class=" span12">
				<table id="post-table" class="table table-hover">
					<tbody>
						<tr>
							<td class="td-post-writer-img none-top-border" rowspan="2">
								<img src="${post.getImgSrc()}">
							</td>
							<td  colspan="2" class="post-top-title none-top-border">
							<a rel="external" class="a-post-title"	href="/community/tags:<c:forEach items='${post.tags}' var='tag'>${tag},</c:forEach>"> 
							${post.title}</a></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${post.push}
									<p class="p-button">추천</p>
							</span></td>
							<td class="td-button none-top-border" rowspan="2"><span
								class="span-button">${post.getRePostCount()}
									<p class="p-button">답변</p>
							</span></td>
						</tr>
						<tr>
							<td class="post-bottom">
																		
								<b>${post.writerName}</b>
								${post.getFormatCreated()}
								</td>	
								<td class="post-bottom-tag">
									<c:forEach	items="${post.tags}" var="tag">
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
		
						<!-- 글내용 시작 -->
						<c:if test="${post.isLong()}">
														
							<tr>
								<td colspan="5">${post.content}</td>
							</tr>

						</c:if>
						<!-- 글내용 끝 -->
					</tbody>
				</table>
				
		
				<!-- 답변에 관련된 테이블 시작-->
				
				<form id ="repost-form" action="/community/${post.postID}/${rePost.rePostID}/update"
					method="POST">

					<div style ="margin-left:0px" class="span11">
						<textarea name="content"
							id="repost-content" class="post-content span10"
							onkeyup="textAreaResize(this)" placeholder="답변할 내용을 입력해주세요!">${rePost.content}</textarea>
					</div>
					<div class="span1">
						<span>
							<button type="submit" class="post-button btn btn-primary">
								<i class="fa fa-check"></i>
							</button>
						</span>
					</div>
					<div class = "file-div"></div>
				</form>			
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>