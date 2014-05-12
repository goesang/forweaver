<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/mobileSrc.jsp"%>
</head>

<body>
	<div data-role="page">

		<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
		<%@ include file="/WEB-INF/panel/lecturePanel.jsp"%>
		<%@ include file="/WEB-INF/panel/repoCreatePanel.jsp"%>

		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#lecturePanel" data-role="button" data-iconpos="notext"
					data-icon="gear"></a>
				<a href="#repoCreatePanel" data-role="button" data-iconpos="notext"
					data-icon="briefcase"></a>
			</div>
			<h1>${lecture.name}</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">

			<table id="repoTable" class="table table-hover">

				<c:forEach items="${lecture.repoes}" var="repo">
				<c:if test = '${repo.name != "example"}'>
					<tr>
						<td class='repo-td'><i class='fa fa-briefcase'></i>&nbsp; <a rel="external" class='none-color'
							href='/lecture/${repo.lectureName}/repo/${repo.name}/browser'><b>${repo.name}</b></a>
							- <small>${repo.getOpeningDateFormat()}</small></td><td class='repo-td'><small>${repo.description}</small></td>
						<c:if test="${repo.getDDay() == -1}">
							<td><span class='label label-success'> 기한 지남 </span></td>
						</c:if>

						<c:if test="${repo.getDDay() == -2}">
							<td><span class='label label-info'>마감일 없음</span></td>
						</c:if>

						<c:if test="${repo.getDDay() >= 0}">
							<td><span class='label label-important'> 남은 기간
									${repo.getDDay()}일</span></td>
						</c:if>

						<td><i class='icon-remove'></i></td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>

	</div>
</body>
</html>