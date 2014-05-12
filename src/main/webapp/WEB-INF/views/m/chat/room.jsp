<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<script src="/resources/forweaver/js/fileBrowser.js"></script>
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
var fileBrowserURL = "/m/chat/${chat.chatID}/fileviewer/";
chatShowFileBrowser("/");

$(document).ready(function() {
	
	
	
	var version = 0;
	pushAndUpdate('');
	$("#chat-input").focus();
	
	function pushAndUpdate(content){
		
		$.ajax({
	        type: "POST",
	        url: "/m/chat/${chat.chatID}/update",
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
					"<tr><td class='chat-bottom'><b>"+contentArray[i][0]+"</b><div class='pull-right'>"+contentArray[i][4]+"</div></td></tr>";
	        	}
	        	
	        	var weaverHtml = "";
	        	for(var i = 0;i < weaverArray.length; i++)
	        	{
	        		weaverHtml += "<a class ='user-button' data-icon='user' data-role='button' data-theme='b'>"+weaverArray[i][0]+"</a>";
	        	}
	        	$('.user-button').remove();
	        	$('#user-group').append(weaverHtml);
	        	$("#user-group").trigger('create');
	        	
	        	if(contentArray.length>0){
	        		$('#chat-table').append(contentHtml);
	        		$(window).scrollTop($('#chat-table').height());
	        	}
	        }
	  });
	}
	
	$('#chat-input').keyup(
			function(e) {
				if(e.keyCode == 13){
					pushAndUpdate($('#chat-input').val());
					$('#chat-input').val('');
					$("#chat-input").focus();
				}
			});
	
	$('#chat-ok').click(function(){
		pushAndUpdate($('#chat-input').val());	
		$('#chat-input').val('');
		$("#chat-input").focus();
	});
	
	setInterval(function (){
		pushAndUpdate('');
	}, 100000);
	
});
</script>

	<div data-role="page" id = "chatPage">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/chatPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#chatPanel" data-role="button" data-iconpos="notext"	data-icon="gear"></a>
				<a href="#projectPage" data-role="button" data-iconpos="notext"	data-icon="folder-close"></a>
			</div>
			<h1>채팅</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              <table id="chat-table" class="chat-table table table-hover">
				</table>
	</div>
	
	  <div data-theme="b" data-role="footer" data-position="fixed">
           <div class="ui-grid-a">
            <div class="ui-block-a" style="padding-left:20px; width:85%;">
                    <input id="chat-input" placeholder="" value="" type="text">
            </div>
            <div class="ui-block-b" style="padding-top:5px; padding-right:5px; padding-left:5px; width:10%;">
                <a id ="chat-ok" data-role="button" data-icon="coffee" data-iconpos="notext">
                </a>
            </div>
        </div>
    </div>
	
	</div>
	
	
<div data-role="page" id = "projectPage">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/chatPanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#chatPanel" data-role="button" data-iconpos="notext"	data-icon="gear"></a>
				<a href="#chatPage" data-role="button" data-iconpos="notext"	data-icon="coffee"></a>
			</div>
			<h1>프로젝트</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              <table id="fileBrowserTable" class="table table-hover">
				</table>
	</div>
	
	  <div data-theme="b" data-role="footer" data-position="fixed">
           <div class="ui-grid-a">
            <div class="ui-block-a" style="padding-left:20px; width:85%;">
                    <input id="chat-input" placeholder="" value="" type="text">
            </div>
            <div class="ui-block-b" style="padding-top:5px; padding-right:5px; padding-left:5px; width:10%;">
                <a id ="chat-ok" data-role="button" data-icon="coffee" data-iconpos="notext">
                </a>
            </div>
        </div>
    </div>
	
	</div>
	
</body>
</html>