<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>
<script>

$(document).ready(function() {
	 if ($(window).width() > 500) {
			$(".td-button").css("width","80px");
			$(".repost-button").css("width","44px");
		 	$( ".short-content" ).hide();
	     	$( ".full-content" ).show();
	     
	    }else{
	    	$(".td-button").css("width","36px");
	    	$(".repost-button").css("width","36px");
	    	$( ".short-content" ).show();
	    	$( ".full-content" ).hide();
	    	
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
		    	$(".td-button").css("width","36px");
		    	$( ".short-content" ).show();
		    	$( ".full-content" ).hide();
	     } else{
				$(".td-button").css("width","80px");
			 	$( ".short-content" ).hide();
		     	$( ".full-content" ).show();
	      }
	 });
	 
		$.each("<c:forEach items='${post.tags}' var='tag'>${tag},</c:forEach>".split(","), function(index, value) {
			if (!$('#tagsdisplay').tagExist(value)) {
				$('#tagsdisplay').addTag(value);
			}
		});

		$('.tag-name').click(
				function() {
					var tagname = $(this).text();
					mobileMovePage(tagname,"");	
		});
	 
});

</script>

</head>
<body>
<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/rePostPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#rePostPanel" data-role="button" data-iconpos="notext" data-icon="pencil"></a>
			</div>
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
									<img src="${post.getImgSrc()}">
								</td>
								
								<td colspan="2" class="none-top-border post-top-title">
								<a rel="external" class="a-post-title"	href="/community/tags:<c:forEach items='${post.tags}' var='tag'>${tag},</c:forEach>"> 
									${post.title}
								</a></td>
								
								<td class="none-top-border td-button" rowspan="2"> <c:if
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
						<!-- 글내용 시작 -->
						<c:if test="${post.isLong()}">
							<tr>
								<td style="border-top: 0px"></td>
								<td class= "post-top-title" colspan="3">${post.content}</td>
							</tr>
						</c:if>
						<!-- 글내용 끝 -->
					</tbody>
				</table>
				<!-- 답변에 관련된 테이블 시작-->
				<c:if test="${rePosts.size()==0}">
					<div style="text-align:center" class="alert">
			   			<h5><i class=" fa fa-comment"></i> 답변 없음!</h5> <p style="font-size:11px;">이 글에는 아직 답변이 없습니다. 답변을 올려주세요!</p>
			   		</div>
			    </c:if>
				
				<table id="repost-table" class="table table-hover">
					<tbody>
						<c:forEach items="${rePosts}" var="rePost">
							<tr>
								<td class="td-post-writer-img" rowspan="2">
								<img src="${rePost.getImgSrc()}">
								</td>
								<td colspan="2"  class="repost-top-title">${rePost.content}</td>
								<td class="repost-button" rowspan="2"><span class="span-button">${rePost.push}
										<p class="p-button">추천</p>
								</span></td>
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${rePost.writerName}</b>
								<date> ${rePost.getFormatCreated()}</date> 
								</td>
								<td class="post-bottom-tag"></td>
									
							</tr>
							<!-- 글내용 시작 -->
								<tr>
									<td style="border-top: 0px"></td>
									<td colspan="3">${rePost.content}</td>
								</tr>
							<!-- 글내용 끝 -->
						</c:forEach>

					</tbody>
				</table>
				<!-- 답변에 관련된 테이블 끝-->
			
			</div>
		</div>
</body>


</html>