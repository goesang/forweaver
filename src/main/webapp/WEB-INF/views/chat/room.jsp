<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${chat.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
<script src="/resources/forweaver/js/weaverBrowser.js"></script>
</head>
<body>
<script>
var fileBrowser = Array();
<c:forEach items="${chat.getGitSimpleFileInfo()}" var="gitFileInfo">
fileBrowser.push({
	"name" : "${fn:substring(gitFileInfo.name,0,20)}",
	"path" : "${gitFileInfo.path}",
	"directory" : ${gitFileInfo.isDirectory},
	"depth" : ${gitFileInfo.depth},
	"commitLog" :  "${fn:substring(gitFileInfo.simpleCommitLog,0,35)}",
	"dateInt" :  ${gitFileInfo.commitDateInt},
	"commitID" :  "${fn:substring(gitFileInfo.commitID,0,8)}",
	"date": "${gitFileInfo.getCommitDate()}"
});
</c:forEach>
var fileBrowserTree = fileListTransform(fileBrowser);
var fileBrowserURL = "/chat/${chat.chatID}/fileviewer/";
chatShowFileBrowser("/");
var weaverList = new Array();
$(document).ready(function() {
	var version = 0;
	pushAndUpdate('');
	$('#chat-input').focus();
	
	function pushAndUpdate(content){
		
		$.ajax({
	        type: "POST",
	        url: "/chat/${chat.chatID}/update",
	        data: 'content='+spacialSignEncoder(content)+'&version='+version,
	        success: function(msg){
	        	if(msg.length <= 0)
	        		return;
	        	
	        	var chat = jQuery.parseJSON(msg);
	        	version = chat["version"];
	    		
	        	var contentArray = chat["content"];
	        	var weaverArray = chat["weaver"];
	        	
	        	var contentHtml = "";
	        	for(var i = 0;i < contentArray.length; i++)
	        	{
	        		contentHtml += "<tr><td class='chat-user' rowspan='2'>	<img src='"+contentArray[i][1]+"'>"+
					"</td><td class='chat-top-title'>"+specialSignDecoder(contentArray[i][3])+"</td></tr>"+
					"<tr><td class='chat-bottom'><b>"+contentArray[i][0]+" </b>"+contentArray[i][4]+"</td></tr>";
	        	}
	        	weaverList = new Array();
	        	
	        	for(var i = 0;i < weaverArray.length; i++)
	        	{
	        		weaverList.push({
		        		"admin": false,
		        		"nickName": weaverArray[i][0],
		        		"email": weaverArray[i][1],
		        		"img": weaverArray[i][2],
		        		"removeLink": "/pe"
		        	});
	        	}
	        	
	        	$('#weaverTable').empty();		    
	        	makeNavigationInManageWeaver(weaverList.length,10);
	        	showWeaverList(1);
	        	
	        	
	        	
	        	if(contentArray.length>0){
	        		$('#chat-table').append(contentHtml);
	        		$('#chat-div').scrollTop($('#chat-table').height());
	        	}
	        }
	  });
	}
		
	$('#chat-input').keyup(
			function(e) {
				if(e.keyCode == 13){
					pushAndUpdate($('#chat-input').val());
					$('#chat-input').val('');
				}
			});
	
	$('#chat-ok').click(function(){
		pushAndUpdate($('#chat-input').val());	
		$('#chat-input').val('');
	});
	
	setInterval(function (){
		pushAndUpdate('');
	}, 1000);
	
	$('#tags-input').textext()[0].tags().addTags(
			getTagList("/tags:<c:forEach	items='${chat.tags}' var='tag'>	${tag},</c:forEach>"));
});
</script>
<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<h5>
				<big><i class=" fa fa-coffee"></i> No. ${chat.chatID}</big> <small>${chat.description}</small>
			</h5>
		</div>
		<div class="row">
			
			<div class="span12">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#chat" data-toggle="tab">채팅방</a></li>
					<li><a href="#weaver" data-toggle="tab">참여자들</a></li>
					<c:if test ='${chat.getProjectName() != ""}'><li><a href="#reference" data-toggle="tab">참조 자료</a></li></c:if>
					<li><a href="/">나가기</a></li>
			</div>
		<div class="span12 tab-content">
						<!-- chat -->
						<div class="tab-pane active" id="chat">
							<div id="chat-div" class="chat-white">
					<table id="chat-table" class="chat-table table table-hover">
				
					</table>
				</div>
				<div>
						<div style="margin-left:30px"class="span10">
							<input class="title span10" placeholder="채팅 내용을 입력해주세요!" id="chat-input" type="text" />
						</div>
						<div class="span1">
							<button id="chat-ok" class="post-button btn btn-primary">
									<i class="icon-ok icon-white"></i>
								</button>
						</div>
				</div>
						</div>
						<!-- weaver -->
						<div class="tab-pane" id="weaver">
							<table id="weaverTable" class="table table-hover">
							</table>
						</div>
					
						<div class="tab-pane" id="reference">
							<table id="fileBrowserTable" class="table table-hover">
							</table>
						</div> 
		</div>
				
		</div>

		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>
</html>