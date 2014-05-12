<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
<script>

$(document).ready(function() {
	
	$("#selectCommit").change(function(){
		if($("#selectCommit option:selected").val() != "체크아웃한 브랜치 없음")
		setTimeout(function(){
			window.location.replace($("#selectmenuBranch option:selected").val()
					+filePathTransform("${fileName}"));
		});
	});
	
	$("#source-code").addClass("brush: "+extensionSeach(document.location.href)+";");
	SyntaxHighlighter.all();
		
	 if ($(window).width() > 500) {
	     	$( ".full-content" ).show();
	    }else{
	    	$( ".full-content" ).hide();
	    }
	
	 $(window).resize(function(){
	     var width = parseInt($(this).width());
	     if (width < 500) {
	     	$( ".full-content" ).hide();
	     } else{
	 	    $( ".full-content" ).show();
	      }
	 });
	 
});

</script>

<body>
	<div data-role="page">

<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
<%@ include file="/WEB-INF/panel/projectPanel.jsp"%>

		<div data-theme="a" data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#projectPanel" data-role="button" data-iconpos="notext"	data-icon="gear"></a>
			</div>
			<h1>커밋 뷰어</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext" data-icon="reorder"></a>
			</div>

		</div>
       <div data-role="content">
              
								<table class="table table-hover">
					<tbody>
						<tr>
							<td class="none-top-border td-post-writer-img" rowspan="2"><img
								src="${gitCommitLog.getImgSrc()}">
							</td>
							<td class="none-top-border post-top-title"><a rel='external' class="none-color" href="/project/${project.name}/commitlog-viewer/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
								<shortMassage>${fn:substring(gitCommitLog.shortMassage,0,27)}</shortMassage></a></td>
							<td class="none-top-border td-commitlog-button" rowspan="2">
							<a rel="external"	href="/project/${project.name}/browser/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
									<span class="span-button"> 
									<i class="icon-eye-open icon-white"></i>
										<p class="p-button">소스</p>
									</span>
							</a>
							<a class = "full-content" rel="external"	href="/project:${project.name}-${fn:substring(gitCommitLog.commitLogID,0,8)}.zip"">
										<span class="span-button"> 
										<i	class="icon-circle-arrow-down icon-white"></i>
											<p class="p-button">다운</p></span>
									</a>
							</td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${gitCommitLog.commiterName}</b>
								${gitCommitLog.getCommitDate()} &nbsp;&nbsp; <span
								class="tag-commit tag-name full-content">${gitCommitLog.commitLogID}</span>
							</td>
						</tr>
						<tr>
							<td style="border-top: 0px"></td>
							<td class = "td-commitlog-full-massage" colspan="3">${gitCommitLog.fullMassage}</td>
						</tr>
					</tbody>
				</table>
				<c:if test="${fn:length(gitCommitLog.diff)>0}">
					<pre id="source-code" class="span9 brush: diff"><c:out value="${gitCommitLog.diff}"></c:out></pre>
				</c:if>	</div>
	
	</div>
</body>
</html>