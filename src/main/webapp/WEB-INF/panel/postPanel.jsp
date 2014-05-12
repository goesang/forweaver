<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {
		var url = document.location.href;
		
		$('.long').hide();
		
		
		if($("#tagsdisplay").val().length == 0){
			$(".short").hide();
		}else if(!url.indexOf("/m/project") || !url.indexOf("/m/lecture")){
			$(".short").hide();
		}
		else{
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
		
		$('#shortButton').click(function(){
			$(".long").show();
			$("#shortButton").hide();
			
		});
		
		$('#longButton').click(function(){
			$(".long").hide();
			$("#shortButton").show();
		});
		
		$('#post-ok').click(function(){
			var title;
			var content;
			if($('#post-title').val().length == 0){
				title = encodeURI(spacialSignEncoder($('#post-content').val()));
			}
			else{
				title = encodeURI(spacialSignEncoder($('#post-title').val()));
				content = encodeURI(spacialSignEncoder($('#post-content').val()));
			}
			var tags = $('#tagsdisplay').val();
			
			var projectName = "@${project.name}"
			var lectureName = "@${lecture.name}"
			
				if(projectName.length != 1){
					tags = projectName+","+tags;}
				else if(lectureName.length != 1){
					tags =lectureName+","+tags;}
				tags = encodeURI(spacialSignEncoder(tags));
				$.ajax({
		               type: "POST",
		               url: "/m/community/add",
		               data: 'title='+title+'&content='+content+'&tags='+tags,
		               success: function(msg){	
		               		window.location.replace(document.location.href);
		            	   }
		         });
		});
		
	});
</script>

<div style="width: 85%;" data-role="panel" id="postPanel"
	data-position="left" data-theme="a" data-display="overlay">
	
	<sec:authorize ifNotGranted="ROLE_USER">	
		<%@ include file="/WEB-INF/panel/common/login.jsp"%>
	</sec:authorize>

	<sec:authorize ifAnyGranted="ROLE_USER">
		<div class="alert">
		<p style="font-size:12px;text-align:center;" ><b><i class="fa fa-tag"></i> 태그를 먼저 입력해주세요!<b></p>
		<p style="font-size:11px;">forWeaver.com은 태그 중심의 커뮤니티입니다. 그렇기 때문에 글을 쓸 때는 꼭 태그를 달아주셔야 합니다!</p>
		</div>
		<input id="tagsinput2" placeholder="태그를 먼저 입력해주세요!" value="" type="text">
		<textarea class="long" id="post-title" placeholder="제목을 입력해주세요!"></textarea>
		<textarea class="short" id="post-content" placeholder="내용을 입력해주세요!"></textarea>
		<a id = "post-ok" class="short" data-theme="b" data-role="button" data-icon="pencil" data-iconpos="left"> 작성 완료 </a>
		<a id = "shortButton" class="short" data-theme="b" data-role="button" data-icon="repeat" data-iconpos="left"> 장문으로 전환 </a>
		<a id = "longButton" class="long" data-theme="b" data-role="button" data-icon="repeat" data-iconpos="left"> 단문으로 전환 </a>
	</sec:authorize>
</div>