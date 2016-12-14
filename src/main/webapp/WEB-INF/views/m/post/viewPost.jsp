<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<title>Forweaver! : ${cov:htmlEscape(post.title)}</title>
<meta property='og:image' content='<c:if test="${post.getFirstImageURL() == ''}">/resources/forweaver/img/preview.png</c:if>
										<c:if test="${post.getFirstImageURL() != ''}"> ${post.getFirstImageURL()} </c:if>' />
<meta property='og:title' content='${cov:htmlEscape(post.title)}' />
<meta property='og:description' content='<c:if test="${post.isLong()}">${fn:substring(cov:htmlEscape(post.content),0,100)}</c:if><c:if test="${!post.isLong()}">모두를 위한 소셜 코딩!</c:if>' />			
</head>
<script>

$(document).ready(function() {
	
	$('.tag-name').click(
			function() {
				var tagname = $(this).text();
				var exist = false;
				var tagNames = getTagList(document.location.href);
				
				if (tagNames.length == 0 || tagNames == "")
					movePage(tagname,"");
				
				$.each(tagNames.split(","), function(index, value) {
					if (value == tagname)
						exist = true;
				});
				if (!exist){
					movePage(tagNames+ ","+ tagname+" ","");
				}
			});
	
	 	if ($(window).width() > 500) {
		 	$("iframe").css("width","500px");
			$(".td-button").css("width","80px");
			$(".repost-button").css("width","44px");
		 	$( ".short-content" ).hide();
	     	$( ".full-content" ).show();
	     
	    }else{
	    	$("iframe").css("width","280px");
	    	$(".td-button").css("width","36px");
	    	$(".repost-button").css("width","36px");
	    	$( ".short-content" ).show();
	    	$( ".full-content" ).hide();
	    	
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
	    	 	$("iframe").css("width","280px");
		    	$(".td-button").css("width","36px");
		    	$( ".short-content" ).show();
		    	$( ".full-content" ).hide();
	     } else{
	    	 	$("iframe").css("width","500px");
				$(".td-button").css("width","80px");
			 	$( ".short-content" ).hide();
		     	$( ".full-content" ).show();
	      }
	 });
	 
	 
		$( "#"+getSort(document.location.href) ).css("background-color", "#16a085");
});


</script>

</head>
<body>
<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>

		<div data-theme="a" data-role="header">
		<h1>글 내용 보기</h1>	
			
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">

				<table class="table table-hover">
					<tbody>
							<tr>
								<td class="none-top-border td-post-writer-img" rowspan="2">
									<a rel="external" href="/${post.writerName}"><img src="${post.getImgSrc()}"></a>
								</td>
								
								<td colspan="2" class="none-top-border post-top-title">
								<a rel="external" class="a-post-title"	href="/community/tags:<c:forEach items='${post.tags}' var='tag'>${tag},</c:forEach>"> 
									${cov:htmlEscape(post.title)}
								</a></td>
								
								<td class="none-top-border td-button" rowspan="2"> 
								<c:if test="${post.kind != 3}">
										<a rel="external" href="/community/${post.postID}/push"> <span
											class="span-button"> ${post.push}
												<p class="p-button">추천</p>
										</span>
										</a>
									</c:if>
									<a	class = "full-content" href="/community/${post.postID}"> 
									<span	class="span-button">${post.rePostCount}
											<p class="p-button">답변</p>
									</span></a>
								</td>
								
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${post.writerName}</b>
								<date class="full-content"> ${post.getFormatCreated()}</date> 
								</td>
								<td class="post-bottom-tag">	
									<c:forEach	items="${post.tags}" var="tag">
										<span	class="tag-name
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
						<!-- 글 파일 목록 -->
							<c:if test="${post.datas.size() > 0}">
								<tr>
									<td colspan="5"><c:forEach var="index" begin="0"
											end="${post.datas.size()-1}">
											<a rel="external" href='/data/${post.datas.get(index).getId()}'><span
												class="function-button function-file"><i
													class='icon-file icon-white'></i>
													${post.datas.get(index).getName()}</span></a>
										</c:forEach></td>
								</tr>
							</c:if>	
						<!-- 글 내용 시작 -->
						<c:if test="${post.isLong()}">
							<tr>
								<td colspan="5"  style="border-top: 0px" class= "post-top-title" colspan="3"><s:eval expression="T(com.forweaver.util.WebUtil).markDownEncoder(post.getContent())" /></td>
							</tr>
						</c:if>
						<!-- 글내용 끝 -->
					</tbody>
				</table>
				<!-- 답변에 관련된 테이블 시작-->
			    <div style="text-align: center;">
				<div data-theme="a" data-mini="true" data-type="horizontal"
					data-role="controlgroup">
					<a id="age-desc" data-iconpos="notext" data-theme="a"
						data-icon="sort-by-order" data-role="button" rel="external"
						href="/community/${post.postID}/sort:age-desc"></a>
					<a id="age-asc" data-iconpos="notext" data-theme="a"
						data-icon="sort-by-order-alt" data-role="button" rel="external"
						href="/community/${post.postID}/sort:age-asc"></a>
					<a id="reply-desc" data-iconpos="notext" data-theme="a"
						data-icon="bullhorn" data-role="button" rel="external"
						href="/community/${post.postID}/sort:reply-desc"></a>
						
					<a id="reply-many" data-iconpos="notext" data-theme="a"
						data-icon="sort-by-attributes-alt" data-role="button"
						rel="external"
						href="/community/${post.postID}/sort:reply-many"></a>
                  <a id="push-desc" data-iconpos="notext" data-icon="thumbs-up"
							data-theme="a" data-role="button" rel="external"
							href="/community/${post.postID}/sort:push-desc"></a>
				</div>
				<br>
			</div>
				<table id="repost-table" class="table table-hover">
					<tbody>
						<c:forEach items="${rePosts}" var="rePost">
							<tr>
								<td class="td-post-writer-img" rowspan="2">
								<a rel="external" href="/${rePost.writerName}"><img src="${rePost.getImgSrc()}"></a>
								</td>
								<td colspan="2"  class="repost-top-title"></td>
								<td class="repost-button" rowspan="2"><span class="span-button">${rePost.push}
										<p class="p-button"></p>
								</span></td>
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${rePost.writerName}</b>
								<date> ${rePost.getFormatCreated()}</date> 
								</td>
								<td class="post-bottom-tag"></td>
									
							</tr>
							<!-- 답변 파일 목록 -->
							<c:if test="${rePost.datas.size() > 0}">
								<tr>
									<td colspan="5"><c:forEach var="index" begin="0"
											end="${rePost.datas.size()-1}">
											<a rel="external" href='/data/${rePost.datas.get(index).getId()}'><span
												class="function-button function-file"><i
													class='icon-file icon-white'></i>
													${rePost.datas.get(index).getName()}</span></a>
										</c:forEach></td>
								</tr>
							</c:if>
							
							<!-- 답변 내용 시작 -->
								<tr>
									<td  colspan="5" style="border-top: 0px"  class="post-top-title" style="border-top: 0px" colspan="3"><s:eval expression="T(com.forweaver.util.WebUtil).markDownEncoder(rePost.getContent())" /></td>
								</tr>
							<!-- 답변 내용 끝 -->
							<c:forEach items="${rePost.replys}" var="reply">
								<tr class="post-top-title">
									<td class="none-top-border"></td>
									<td class="reply dot-top-border" colspan="4"><b>${reply.number}.</b>
										${reply.content} - <a rel="external" href="/${reply.writerName}"><b>${reply.writerName}</b></a>
										${reply.getFormatCreated()}
										</td>
								</tr>
							</c:forEach>
						</c:forEach>

					</tbody>
				</table>
				<c:if test="${rePosts.size()==0}">
					<div style="margin-top:-20px; text-align:center;" class="alert">
			   			<h5><i class=" fa fa-comment"></i> 답변 없음!</h5> <p style="font-size:11px;">이 글에는 아직 답변이 없습니다. 답변을 올려주세요!</p>
			   		</div>
			    </c:if>
				<!-- 답변에 관련된 테이블 끝-->
			
			</div>
		</div>
		
</body>


</html>