<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {				
		$('#post-ok').click(function(){
			var name = $('#repo-name-input').val();
			var description = $('#repo-description').val();
			var period = $('#repo-period-select').val();
			var category = $('#repo-category-select').val();

			if(name.length == 0 || description.lenght == 0)
				return;
			$.ajax({
	               type: "POST",
	               url: "/m/lecture/${lecture.name}/repo/add",
	               data: 'name='+name+'&description='+description+'&period='+period+'&category='+category,
	               success: function(msg){
	            	   window.location = "/m/lecture/${lecture.name}/repo/"+name+"/browser";
	               }
	         });
		});
		
	});
</script>

<div style="width: 85%;" data-role="panel" id="repoCreatePanel"
	data-position="left" data-theme="a" data-display="overlay">
	
	<sec:authorize ifNotGranted="ROLE_USER">	
		<%@ include file="/WEB-INF/panel/common/login.jsp"%>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_USER">
				<select data-overlay-theme="f" data-theme="f" data-native-menu="false" id="repo-category-select">
							<option disabled="disabled">이메일 알림여부</option>
							<option value="1">승락</option>
							<option value="2" selected="selected">거부</option>
						</select>
		
					<select data-overlay-theme="f" data-theme="f" data-native-menu="false" id="repo-period-select">
							<option disabled="disabled">마감 기간</option>
							<option value="0" selected="selected">일주일 뒤</option>
							<option value="1">한 달 뒤</option>
							<option value="2">한 학기 뒤</option>
							<option value="3">영원히</option>
						</select>
						
		<input id="repo-name-input" placeholder="숙제명을 입력해주세요!"></input>
		<textarea id="repo-description" placeholder="숙제에 대해 설명해주세요!"></textarea>
		<a id = "post-ok" data-theme="b" data-role="button" data-icon="briefcase" data-iconpos="left"> 작성 완료 </a>
	</sec:authorize>
</div>