<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {
		
		if($("#tagsdisplay").val().length == 0){
			$(".short").hide();
		}else{
			$("#tagsinput2").parent().remove();
			$(".alert").remove();
		}
		
		$('#tagsinput2').keyup(function(e) {
			if (e.keyCode == 32 || e.keyCode == 13) {
				if (!$('#tagsdisplay').tagExist($('#tagsinput2').val())) {
					$('#tagsdisplay').addTag($('#tagsinput2').val());
					mobileMovePage($('#tagsdisplay').val(),"");
					$('#tagsinput2').val('');
				}
			}
		});
		
		$('#post-ok').click(function(){
			var description = encodeURI(spacialSignEncoder($("#chat-description").val()));
			var category = 0 ;
			var tags = $('#tagsdisplay').val();
						
			tags = encodeURI(spacialSignEncoder(tags));
				$.ajax({
		               type: "POST",
		               url: "/m/chat/add",
		               data: 'description='+description+'&category='+category+'&tags='+tags,
		               success: function(msg){
		            	   if(msg != -1)
		            	   	window.location = "/m/chat/"+msg;
		               }
		         });
		});
	});
</script>

<div style="width: 85%;" data-role="panel" id="chatAddPanel"
	data-position="left" data-theme="a" data-display="overlay">
	
	
	<sec:authorize ifNotGranted="ROLE_USER">	
		<%@ include file="/WEB-INF/panel/common/login.jsp"%>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER">
		<div class="alert">
		<p style="font-size:12px;text-align:center;" ><b><i class="fa fa-tag"></i> 태그를 먼저 입력해주세요!<b></p>
		<p style="font-size:11px;">forWeaver.com은 태그 중심의 커뮤니티입니다. 그렇기 때문에 글을 쓸 때는 꼭 태그를 달아주셔야 합니다!</p>
		</div>
		<input id="tagsinput2" placeholder="태그를 먼저 입력해주세요!"  type="text">
		<textarea class="short" id="chat-description" placeholder="채팅방에 대해 소개해주세요!"></textarea>
		<a id = "post-ok" class="short" data-theme="b" data-role="button" data-icon="coffee" data-iconpos="left"> 개설 완료 </a>
	</sec:authorize>
</div>