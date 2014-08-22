<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>${project.name} ~ ${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
<script>
var communityOn = false;
var editorMode = false;
		function showPostContent() {
			$('#post-pagination').hide();
			$('#post-table').hide();
			$('#post-content-textarea').fadeIn('slow');
			$('#show-content-button').hide();
			$('#hide-content-button').show();
			editorMode = true;
		}

		function hidePostContent() {
			$('#post-pagination').show();
			$('#post-table').show();
			$('#post-content-textarea').hide();
			$('#show-content-button').show();
			$('#hide-content-button').hide();
			editorMode = false;
		}
		$(document).ready(function() {
			
		$( "#"+getSort(document.location.href) ).addClass( "active" );
			
			
			$('#showCommunity').click(function() {
				if(communityOn){
					communityOn = false;
					$('#communityTab').hide();
					$('#myTab').show();
				}else{
					communityOn = true;
					$('#myTab').hide();
					$('#communityTab').show();
				}
			});
			
			
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
			$('#post-ok').click(function(){
				var title = $('#post-title-input').val();
				var content ="";
				var tags = $("input[name='tags']").val();
				tags = tagInputValueConverter(eval(tags));
				if(editorMode)
					content = $('#post-content-textarea').val();
				$.ajax({
		               type: "POST",
		               url: "/project/${project.name}/community/add",
		               data: 'title='+title+'&content='+content+' &tags='+tags,
		               success: function(msg){
		            	   window.location="/project/${project.name}/community/";
		               }
		         });
			});
			
			var pageCount = ${postCount+1}/10;
			if(pageCount < 1 ) 
				pageCount = 1;
			
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

		<div class="page-header page-header-none">
			<h5>
								<big><big>	<c:if test="${!project.isForkProject()}">
							<i class="fa fa-bookmark"></i></c:if>
							<c:if test="${project.isForkProject()}">
							<i class="fa fa-code-fork"></i></c:if> 
							${project.name}</big></big>
				<small>${project.description}</small>
				<div style="margin-top:-10px" class="pull-right">

				<button class="btn btn-warning">
								<b>COUNT : ${postCount}</b>
				</button>

				</div>
			</h5>
		</div>
		<div class="row">
		
		<div class="span7">
				<ul id="myTab" class="nav nav-tabs">
					<li><a href="/project/${project.name}/">브라우져</a></li>
					<li><a href="/project/${project.name}/commitlog">커밋</a></li>
					<li class="active"><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);" onclick="openWindow('/project/${project.name}/chat', 400, 500);">채팅</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
					<li><a href="/project/${project.name}/info">정보</a></li>
					
					<c:if test="${project.getCategory() != 2}">
						<li><a href="/project/${project.name}/cherry-pick">체리 바구니</a></li>
					</c:if>
				</ul>
				
				<ul style="display:none;" class="nav nav-tabs" id="communityTab">
					<li id = "age-desc"><a href="/project/${project.name}/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if>/<c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<li id = "push-desc"><a href="/project/${project.name}/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-desc/page:1">추천순</a></li>
					<li id = "repost-desc"><a href="/project/${project.name}/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1">최신 답변순</a></li>
					<li id = "repost-many"><a href="/project/${project.name}/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1">많은 답변순</a></li>
					<li id = "age-asc"><a href="/project/${project.name}/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>
					<li id = "repost-null"><a href="/project/${project.name}/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-null/page:1">답변 없는 글</a></li>
				</ul>
				
			</div>
			<div class="span1">	
				<a id='showCommunity'
						class="post-button btn btn-inverse"> <i class="fa fa-refresh"></i>
					</a>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/g/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
			
				<div class="span10">
					<input id="post-title-input" class="title span10" name="title"
						placeholder="찾고 싶은 검색어나 쓰고 싶은 단문의 내용을 입력해주세요!" type="text"
						value="" />
				</div>
				<div class="span2">
					<span> <a id="show-content-button" href="javascript:showPostContent();"
						class="post-button btn btn-primary"> <i class="icon-pencil"></i>
					</a> <a style="display: none;" id="hide-content-button"
						href="javascript:hidePostContent();"
						class="post-button btn btn-primary"> <i class="icon-pencil"></i>
					</a>
						<button id='post-ok' class="post-button btn btn-primary">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
				<div class="span12">
					<textarea style="display: none;" id="post-content-textarea"
						class="post-content span12" onkeyup="textAreaResize(this)"
						placeholder="글 내용을 입력해주세요!"></textarea>
				</div>
				<div class="span12">
				
					<table id="post-table" class="table table-hover">
						<tbody>
							<c:forEach items="${posts}" var="post">
							<tr>
								<td class="td-post-writer-img" rowspan="2">
										<img src="${post.getImgSrc()}">
								</td>
								<td class="post-top-title-short"><a class="a-post-title"
									href="/community/${post.postID}"> <c:if
											test="${post.isLong()}">
											<i class=" icon-align-justify"></i>
										</c:if> <c:if test="${!post.isLong()}">
											<i class=" icon-comment"></i>
										</c:if> &nbsp;${post.title}
								</a></td>
								<td class="td-button" rowspan="2">
										<span class = "span-button">${post.push}
										<p class="p-button">추천</p></span>
								</td>
								<td class="td-button" rowspan="2">
										<span class = "span-button">${post.rePostCount}
										<p class="p-button">답변</p></span>
								</td>				
							</tr>
							<tr>
								<td class="post-bottom"><b>${post.writerName}</b>
									${post.getFormatCreated()}
									&nbsp;&nbsp;
									<c:forEach items="${post.tags}" var="tag">
										<c:if test="${!fn:startsWith(tag, '@')}">
											<span class = "tag-name">${tag}</span>
										</c:if>
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

		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>
