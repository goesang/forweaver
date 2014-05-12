<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<jsp:useBean id="dateValue" class="java.util.Date" />
<!DOCTYPE html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
<body>
	<script>

$(document).ready(function() {
		
	$("#source-code").addClass("brush: "+extensionSeach(document.location.href)+";");
	SyntaxHighlighter.all();
		
});

</script>
			<div class="span8">
					<h4 class="file-name-title">${fileName}</h4>												
				<table class="table table-hover">
					<tbody>
						<tr>
							<td class="none-top-border td-post-writer-img" rowspan="2"><img
								src="${gitCommitLog.getImgSrc()}">
							</td>
							<td
								class="none-top-border post-top-title-short">
								${fn:substring(gitCommitLog.shortMassage,0,45)}</td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${gitCommitLog.commiterName}</b>
								${gitCommitLog.getCommitDate()} 
							</td>
						</tr>
					</tbody>
				</table>
				<pre id="source-code" class="span9">${fileContent}</pre>
			</div>

</body>
</html>