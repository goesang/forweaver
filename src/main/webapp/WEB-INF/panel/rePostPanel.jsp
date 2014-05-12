<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {
		$('.long').hide();
		$('#repost-ok').click(function(){
			var title = encodeURI($('#repost-title').val());
			
			if(name.length == 0)
				return;
		$.ajax({
            type: "POST",
            url: "/m/community/${post.postID}/add-repost",
            data: 'title='+title,
            success: function(msg){
            		window.location.replace(msg);
            }
       });
	});
		
		$('#shortButton').click(function(){
			$(".long").show();
			$("#longButton").hide();
			
		});
		
		$('#longButton').click(function(){
			$(".long").hide();
			$("#shortButton").show();
		});
		
	});
</script>

<div style="width: 85%;" data-role="panel" id="rePostPanel"
	data-position="left" data-theme="a" data-display="overlay">

	<sec:authorize ifNotGranted="ROLE_USER">	
		<%@ include file="/WEB-INF/panel/common/login.jsp"%>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER">
		<textarea class="long" id="repost-title" placeholder="제목을 입력해주세요!"></textarea>
		<textarea class="short" id="repost-content" placeholder="내용을 입력해주세요!"></textarea>
		<a id = "repost-ok" class="short" data-theme="b" data-role="button" data-icon="pencil" data-iconpos="left"> 작성 완료 </a>
		<a id = "shortButton" class="short" data-theme="b" data-role="button" data-icon="repeat" data-iconpos="left"> 장문으로 전환 </a>
		<a id = "longButton" class="long" data-theme="b" data-role="button" data-icon="repeat" data-iconpos="left"> 단문으로 전환 </a>
	</sec:authorize>
</div>