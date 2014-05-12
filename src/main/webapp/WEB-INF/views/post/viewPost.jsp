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
	var comment = 0;
	var fileArray = [];
	function fileUploadChange(fileUploader){
		$(function (){
		if($(fileUploader).val()!=""){ // 파일을 업로드하거나 수정함
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
		function showCommentAdd(rePostID){
			$("#repost-form").hide();
			$(".comment-form").remove();
			if(comment != rePostID){
			$("#comment-form-td-"+rePostID).append("<form class='comment-form' action='/community/${post.postID}/"+rePostID+"/add-reply' method='POST'>"+
			"<div style='padding-left:20px;' class='span10'>"+
			"<input id='reply-input'  type ='text' name='content' class='reply-input span10'  placeholder='답변할 내용을 입력해주세요!'></input></div>"+
			"<div class='span1'><span><button type='submit' class='post-button btn btn-primary'>"+
			"<i class='icon-ok icon-white'></i></button></span></div></form>");
			comment = rePostID;
			$("#reply-input").focus();
			
			}else{
				$("#repost-form").show();
				comment = 0;
			}
		}
	
		
		$(function() {
			
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
			$("#repost-content").focus(function(){				
					$(".file-div").fadeIn();
					$("#repost-table").hide();
					$("#myTab").hide();
			});
			
			$("#repost-content").focusout(function(){	

				if( !this.value ) {
					$(".file-div").hide();
					$("#repost-table").fadeIn();
					$("#myTab").fadeIn();
		      }
		});
			
			$(".file-div").append("<div class='fileinput fileinput-new' data-provides='fileinput'>"+
					  "<div class='input-group'>"+
					    "<div class='form-control' data-trigger='fileinput'><i class='icon-file '></i> <span class='fileinput-filename'></span></div>"+
					    "<span class='input-group-addon btn btn-primary btn-file'><span class='fileinput-new'>"+
					    "<i class='icon-upload icon-white'></i></span><span class='fileinput-exists'><i class='icon-repeat icon-white'></i></span>"+
						"<input onchange ='fileUploadChange(this);' type='file' id='file1' multiple='true' name='files[0]'></span>"+
					   "<a href='#' class='input-group-addon btn btn-primary fileinput-exists' data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>"+
					  "</div>"+
					"</div>");
			
			$(".file-div").hide();
			
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
								class="span-button">${rePosts.size()}
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
									<div class = "function-div pull-right">
									<a href="/community/${post.postID}/update">
									<span class="function-button">수정</span></a>	
									<a href="javascript:deletePost(${post.postID})">
									<span class="function-button">삭제</span></a>									
									</div>	
								</td>

						</tr>
						<c:if	test="${post.dataNames.size() > 0}">
							<tr>
								<td colspan="5"><c:forEach var="index" begin="0"
										end="${post.dataNames.size()-1}">
										<a href='/data/${post.dataIDs.get(index)}'><span
											class="function-button function-file"><i
												class='icon-file icon-white'></i>
												${post.dataNames.get(index)}</span></a>
									</c:forEach></td>
							</tr>
						</c:if>
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
				
				<form enctype="multipart/form-data" id ="repost-form" action="/community/${post.postID}/add-repost"
					method="POST">

					<div style ="margin-left:0px" class="span11">
						<textarea name="content"
							id="repost-content" class="post-content span10"
							onkeyup="textAreaResize(this)" placeholder="답변할 내용을 입력해주세요!"></textarea>
					</div>
					<div class="span1">
						<span>
							<button type="submit" class="post-button btn btn-primary">
								<i class="icon-ok icon-white"></i>
							</button>
						</span>
					</div>
					<div class = "file-div"></div>
				</form>
				
				<c:if test="${post.rePostCount != 0}">
				
					<div class="span12"></div>
						<ul class="nav nav-tabs" id="myTab">
						<li id = "age-desc"><a href="/community/${post.postID}/sort:age-desc">최신순</a></li>
						<li id = "push-desc"><a href="/community/${post.postID}/sort:push-desc">추천순</a></li>
						<li id = "reply-desc"><a href="/community/${post.postID}/sort:reply-desc">최신 답변순</a></li>
						<li id = "reply-many"><a href="/community/${post.postID}/sort:reply-many">많은 답변순</a></li>
						<li id = "age-asc"><a href="/community/${post.postID}/sort:age-asc">오래된순</a></li>
					</ul>
				
				</c:if>
				<table id="repost-table" class="table table-hover">
					<tbody>
						<c:forEach items="${rePosts}" var="rePost">
							<tr>
								<td class=" td-post-writer-img "><img
									src="${rePost.getImgSrc()}">
								</td>
								
								<td class="font-middle"><b>${rePost.writerName}</b>
									${rePost.getFormatCreated()}</td>
								<td class="function-div font-middle">
									<div class = "pull-right">
									<a onClick='javascript:showCommentAdd(${rePost.rePostID})'><span class="function-button function-comment">댓글달기</span></a>
									<a href="/community/${post.postID}/${rePost.rePostID}/update">
									<span class="function-button">수정</span></a>	
									<a href='javascript:deleteRePost(${post.postID},${rePost.rePostID})'>
									<span class="function-button">삭제</span></a>									
									</div>	
								</td>
								<td class="td-button"><span class="span-button">${rePost.push}
										<p class="p-button">추천</p>
								</span></td>
								<td class="td-button"><span class="span-button">${rePost.replys.size()}
										<p class="p-button">댓글</p>
								</span></td>
							</tr>
							<tr>
									<td class="none-top-border repost-top-title" colspan="5">${rePost.content}</td>
							</tr>
							
							<tr> <td id = "comment-form-td-${rePost.rePostID}" class="none-top-border" colspan="5"> 
								 </td>
								 
							</tr>
							<c:forEach items="${rePost.replys}" var="reply">
								<tr> <td class="none-top-border"></td><td class="reply dot-top-border" colspan="4"> 
											<b>${reply.number}.</b> ${reply.content} - <b>${reply.writerName}</b> ${reply.getFormatCreated()}
											<div class = "function-div pull-right">
									<a href="javascript:deleteReply(${post.postID},${rePost.rePostID},${reply.number})">
									<i class='icon-remove'></i></a>	
									</div>
									 </td>
								</tr>
							</c:forEach>
						</c:forEach>

					</tbody>
				</table>
				<!-- 답변에 관련된 테이블 끝-->
			
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>