<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>Forweaver : 대화해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
<script>
$(document).ready(function() {
		
	$("select").selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});
	
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
		var description = encodeURI(spacialSignEncoder($("#chat-description").val()));
		var category = 0 ;
		var tags = $("input[name='tags']").val();
		var projectName = $("#selectProject option:selected").val();
		
		if(tags.length == 2){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!"+
					"</div>");
			return;
		}else if(description.length == 0){
			$("alert").append("<div class='alert alert-error'>"+
					  "<button type='button' class='close' data-dismiss='alert'>&times;</button>"+
					  "<strong>경고!</strong> 채팅방 소개를 입력하시지 않았습니다. 채팅방 대해 소개해주세요!"+
					"</div>");
			return;
		}
		
		tags = encodeURI(spacialSignEncoder(tagInputValueConverter(eval(tags))));
		
			$.ajax({
	               type: "POST",
	               url: "/chat/add",
	               data: 'description='+description+'&category='+category+'&tags='+tags+'&projectName='+projectName,
	               success: function(msg){
	            	   if(msg != -1)
	            	   	window.location = "/chat/"+msg;
	               }
	         });
	});

	var pageCount = ${count}/10;
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
		<div class="page-header">
		<alert></alert>
			<h5>
				<big><big><i class=" fa fa-coffee"></i> 대화해보세요!</big></big> 
				<small>급하게 질문할 내용이나 간단하게 대화해보세요!</small>
			<div style="margin-top:-10px" class="pull-right">

				<button class="btn btn-warning">
								<b>COUNT : ${count}</b>
				</button>

			</div>
			</h5>
			
		</div>
		<div class="row">

			<div class=" span12">
					<div class="span7">
						<input id="chat-description" class="title" placeholder="채팅방을 개설하기 위해서는 소개문을 입력해야 합니다!"
							type="text" style="width:95%" />
					</div>
					
					<div class="span3">
					
					<select id="selectProject">
					<option value="" >프로젝트를 선택해주세요!</option>
						<c:forEach items="${projects}" var="project">
							<option value="${project.name}">${project.name}</option>
						</c:forEach>
					</select>
					</div>
					<div class="span1">
						<span> 
							<button id='post-ok' class="post-button btn btn-primary">
								<i class="icon-ok icon-white"></i>
							</button>
							
						</span>
					</div>
				<table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items="${chats}" var="chat">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><img
									src="${chat.getChatAdmin().getImgSrc()}">
								</td>
								<td colspan="2"  class="post-top-title"><a class="a-post-title"
									href="/chat/${chat.chatID}">
											<i class="fa fa-coffee"></i>
										&nbsp;${chat.description}
								</a></td>
								<td class="td-button" rowspan="2">
									<span	class="span-button">${chat.weavers.size()}
											<p class="p-button">인원</p>
									</span></td>
							</tr>
							<tr>
								<td class="post-bottom">
																		
								<b>${chat.getChatAdmin().getName()}</b>
								${chat.getFormatCreated()}
								</td>	
								<td class="post-bottom-tag">
									<c:forEach	items="${chat.tags}" var="tag">
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
						</c:forEach>
						
					</tbody>
				</table>

				<div class = "text-center">
					<div id="page-pagination"></div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>