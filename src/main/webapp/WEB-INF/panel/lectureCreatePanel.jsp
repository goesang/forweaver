<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {
		if($("#tagsdisplay").val().length == 0){
			$(".create").hide();
		}else{
			$("div.tag").remove();
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
			var name = $('#lecture-name-input').val();
			var description = $('#lecture-description').val();
			var period = $('#lecture-period-select').val();
			var category = $('#lecture-category-select').val();
			var tags = $('#tagsdisplay');
			
			if(tags.length == 0 || name.length == 0 || description.lenght == 0)
				return;
			
			$.ajax({
	               type: "POST",
	               url: "/m/lecture/add",
	               data: 'name='+name+'&description='+description+'&tags='+tags+'&period='+period+'&category='+category,
	               success: function(msg){
	            	   if(msg.length > 4){
	               		window.location.replace("/m/login");
	               	}
	               	else if(msg){
	               		mobileMovePage(tags,"");
	            	   }
	               }
	         });
	});
		
	});
</script>

<div style="width: 85%;" data-role="panel" id="lectureCreatePanel"
	data-position="left" data-theme="a" data-display="overlay">
	
	<sec:authorize ifNotGranted="ROLE_USER">	
		<%@ include file="/WEB-INF/panel/common/login.jsp"%>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER">
		<div class="alert">
		<p style="font-size:12px;text-align:center;" ><b><i class="fa fa-tag"></i> 태그를 먼저 입력해주세요!<b></p>
		<p style="font-size:11px;">forWeaver.com은 태그 중심의 커뮤니티입니다. 그렇기 때문에 글을 쓸 때는 꼭 태그를 달아주셔야 합니다!</p>
		</div>
		<div class ="tag">
		<input id="tagsinput2" placeholder="태그를 먼저 입력해주세요!" value="" type="text">
		</div>
		<div class ="create">
					<select data-overlay-theme="f" data-theme="f" data-native-menu="false"  class="create"  id="lecture-category-select">
							<option disabled="disabled">강의 공개여부</option>
							<option value="0">공개 강의</option>
							<option value="1" selected="selected">비공개 강의</option>
							<option value="2">완전 비공개 강의</option>
						</select>
					<select data-overlay-theme="f" data-theme="f" data-native-menu="false" id="lecture-period-select">
							<option disabled="disabled">강의 기간</option>
							<option value="0">한 달 동안 진행</option>
							<option value="1" selected="selected">한 학기 동안 진행</option>
							<option value="2">일 년 동안 진행</option>
							<option value="3">영원히 진행</option>
						</select>
						
		<input id="lecture-name-input" placeholder="강의명을 입력해주세요!"></input>
		<textarea id="lecture-description" placeholder="강의에 대해 설명해주세요!"></textarea>
		<a id = "post-ok" data-theme="b" data-role="button" data-icon="book" data-iconpos="left"> 작성 완료 </a>
		</div>
	</sec:authorize>
</div>