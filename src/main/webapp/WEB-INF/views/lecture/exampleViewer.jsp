<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<jsp:useBean id="dateValue" class="java.util.Date" />
<!DOCTYPE html>
<head>
<title>${lecture.name}~${lecture.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<%@ include file="/WEB-INF/includes/syntaxhighlighterSrc.jsp"%>
</head>
<body>
	<script>

$(document).ready(function() {
	
	$('#tags-input').textext()[0].tags().addTags(
			getTagList("/tags:<c:forEach items='${lecture.tags}' var='tag'>	${tag},</c:forEach>"));

	
	$("#selectCommit").selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});
	$('#selectCommit').selectpicker('refresh');
	
	$("#selectCommit").change(function(){
		if($("#selectCommit option:selected").val() != "체크아웃한 브랜치 없음")
			window.location = $("#selectCommit option:selected").val()+filePathTransform("${fileName}");
	});
	
	$("#source-code").addClass("brush: "+extensionSeach(document.location.href)+";");
	SyntaxHighlighter.all();
		
});

</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<h5>
				<big><big><i class="fa fa-book"></i> ${lecture.name}</big></big> 
			<small>${lecture.description}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li class="active"><a href="/lecture/${lecture.name}/">예제소스</a></li>
					<li><a href="/lecture/${lecture.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);" onclick="openWindow('/lecture/${lecture.name}/chat', 400, 500);">채팅</a></li>
					<li><a href="/lecture/${lecture.name}/repo">숙제 저장소</a></li>
					<li><a href="/lecture/${lecture.name}/weaver">수강생</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-link"></i></span> <input
						value="http://forweaver.com/${lecture.name}/example.git" type="text"
						class="input-block-level">
				</div>
			</div>


			<div class="span12">
				<div class="span9">
					<h4 class="file-name-title">${fileName}</h4>
				</div>
				<select id="selectCommit" class="span3">
					<c:forEach items="${gitLogList}" varStatus="status" var="gitLog">
						<option 
						<c:if test='${status.count == selectCommitIndex + 1}'>
						selected="selected"
						</c:if >
							value="/lecture/${lecture.name}/example/commit:${fn:substring(gitLog.getName(),0,8)}/filepath:">
							<jsp:setProperty name="dateValue" property="time"
								value="${gitLog.getCommitTime()*1000}" />
							<fmt:formatDate value="${dateValue}" pattern="yy년MM월dd일 HH시mm분" />
						</option>
					</c:forEach>
				</select>
				
				<table class="table table-hover">
					<tbody>
						<tr>
							<td class="none-top-border td-post-writer-img" rowspan="2"><img
								src="${gitCommitLog.getImgSrc()}">
							</td>
							<td style="width: 800px;"
								class="none-top-border post-top-title-short">
								${fn:substring(gitCommitLog.shortMassage,0,50)}</td>
							<td class="none-top-border td-button" rowspan="2"><a
								href="/lecture/${lecture.name}/example/commit:${fn:substring(gitCommitLog.commitLogID,0,8)}">
									<span class="span-button"> <i
										style="zoom: 1.5; -moz-transform: scale(1.5);"
										class="icon-eye-open icon-white"></i>
										<p class="p-button">소스</p>
									</span>
							</a></td>
						</tr>
						<tr>
							<td class="post-bottom"><b>${gitCommitLog.commiterName}</b>
								${gitCommitLog.getCommitDate()} &nbsp;&nbsp; <span
								style="cursor: text;" class="tag-commit tag-name">${gitCommitLog.commitLogID}</span>
							</td>
						</tr>
					</tbody>
				</table>
				<pre id="source-code" class="span9">${fileContent}</pre>
			</div>

			<!-- .span9 -->
		</div>
		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>