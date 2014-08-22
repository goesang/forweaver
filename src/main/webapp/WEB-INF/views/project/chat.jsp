<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${project.name} 채팅방</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link
	href="http://maxcdn.bootstrapcdn.com/bootswatch/3.2.0/flatly/bootstrap.min.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js">
	
</script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js">
	
</script>
<script src="/resources/forweaver/js/sockjs-0.3.4.js"></script>
<script src="/resources/forweaver/js/stomp.js"></script>
<style>
.chat-img {
	width: 50px;
}

p {
	font-size: 13px;
	word-break: break-all;
}

.none-border {
	border-radius: 0px;
	border-width: 0px;
}

.chat {
	list-style: none;
	margin: 0;
	padding: 0;
}

.chat li {
	margin-bottom: 10px;
	padding-bottom: 5px;
	border-bottom: 1px dotted #B3A9A9;
}

.chat li.left .chat-body {
	margin-left: 60px;
}

.chat li.right .chat-body {
	margin-right: 60px;
}

.chat li .chat-body p {
	margin: 0;
	color: #777777;
}

.panel .slidedown .glyphicon, .chat .glyphicon {
	margin-right: 5px;
}

.panel-body {
	overflow-y: scroll;
	height: 250px;
}
</style>
</head>
<body>
	<script>
		var userName = "${weaver.id}";
		var imgSrc = "${weaver.imgSrc}";
		
		var socket = new SockJS('/chat/pub/${project.getChatRoomName()}');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			stompClient.subscribe('/chat/sub/${project.getChatRoomName()}', function(greeting) {
				var json = JSON.parse(greeting.body);
				
				if(userName == json.weaver){
				
					$(".chat").append(inputMessageMe("/"+json.weaver+"/img",
							json.weaver,json.date,json.message));
				}else{
					$(".chat").append(inputMessageYou("/"+json.weaver+"/img",
							json.weaver,json.date,json.message));
				}
				$(".panel-body").animate({scrollTop : $(".chat").height()}, 900);
			});
		});


		function inputMessageYou(imgSrc, userName, chatDate, chatContent) {
			return "<li class='left clearfix'><span class='chat-img pull-left'><img src='"+imgSrc+"' class='chat-img img-rounded' /></span>"
					+ "<div class='chat-body clearfix'><div class='header'><strong class='primary-font'>"
					+ userName
					+ "</strong> <small class='pull-right text-muted'>"
					+ "<span class='glyphicon glyphicon-time'></span>"
					+ chatDate
					+ "</small></div><p>"
					+ chatContent
					+ "</p></div></li>";
		}

		function inputMessageMe(imgSrc, userName, chatDate, chatContent) {
			return "<li class='right clearfix'><span class='chat-img pull-right'><img src='"+imgSrc+"' class='chat-img img-rounded' />"
					+ "</span><div class='chat-body clearfix'><div class='header'>"
					+ "<small class=' text-muted'><span class='glyphicon glyphicon-time'></span>"
					+ chatDate
					+ "</small><strong class='pull-right primary-font'>"
					+ userName
					+ "</strong></div><p>"
					+ chatContent
					+ "</p></div></li>";
		}

		$(function() {
			$(".panel-body").animate({scrollTop : $(".chat").height()}, 300);
			$('#btn-input').keydown(
							function(e) {
								if (e.keyCode == 13) {
									stompClient.send("/chat/pub/${project.getChatRoomName()}", {"content-type": "text/plain"}, $("#btn-input").val());
									$("#btn-input").val("");
								}
							});

			$("#btn-chat").click(
					function() {
						stompClient.send("/chat/pub/${project.getChatRoomName()}", {"content-type": "text/plain"}, $("#btn-input").val());
						$("#btn-input").val("");
						
					});
			


			$(window).unload( function () { stompClient.disconnect(); } );


		});
	</script>

	<div class="container" style="width: 100%">
		<div class="row">
			<div>
				<div class="panel panel-primary none-border">
					<div class="panel-heading none-border">
						<span class="glyphicon glyphicon-comment"></span> ${project.name}
						채팅방
					</div>
					<div class="panel-collapse" id="collapseOne">
						<div class="panel-body" style="height: calc(100vh - 100px)">
							<ul class="chat">
								<c:forEach items="${chatRoom.getChatMessages()}" var="chatMessage">
									<c:if test="${chatMessage.weaver == weaver.id}">
										<li class='right clearfix'><span class='chat-img pull-right'><img src='/${chatMessage.weaver}/img' class='chat-img img-rounded' />
										 </span><div class='chat-body clearfix'><div class='header'>
										 <small class=' text-muted'><span class='glyphicon glyphicon-time'></span>
										 ${chatMessage.date}
										 </small><strong class='pull-right primary-font'>
										${chatMessage.weaver}
										 </strong></div><p>
										 ${chatMessage.message}
										 </p></div></li>
									</c:if>
									<c:if test="${chatMessage.weaver != weaver.id}">
										<li class='left clearfix'><span
											class='chat-img pull-left'> <img src='/${chatMessage.weaver}/img'
												class='chat-img img-rounded' /></span>
											<div class='chat-body clearfix'>
												<div class='header'>
													<strong class='primary-font'>${chatMessage.weaver}</strong> <small
														class='pull-right text-muted'> <span
														class='glyphicon glyphicon-time'></span> ${chatMessage.date}
													</small>
												</div>
												<p>${chatMessage.message}</p>
											</div></li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<div class="panel-footer">
							<div class="input-group">
								<input id="btn-input" type="text" class="form-control input-sm"
									placeholder="채팅 내용을 입력해주세요..." /> <span
									class="input-group-btn">
									<button class="btn btn-warning btn-sm" id="btn-chat">
										보내기</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
