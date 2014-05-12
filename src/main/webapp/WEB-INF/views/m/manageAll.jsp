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
	<div id="page1" data-role="page">
		<%@ include file="/WEB-INF/panel/mainPanel.jsp"%>
		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a rel="external" href="#postPanel" data-role="button" data-iconpos="notext"
					data-icon="user"></a>
			</div>
			<h1>관리 화면</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a rel="external" href="#mainPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">
			<table id="repoTable" class="table table-hover">
			<c:if test="${lectures.size() > 0}">
					<tr>
						<td class = "manage-title">
							강의 (${lectures.size()})
						</td>
					</tr>
			</c:if>
				<c:forEach items="${lectures}" var="lecture">
					<tr>
						<td><a rel="external" class="none-color" href='/lecture/${lecture.name}/'><b
								style="white-space: nowrap"><i class='icon-book'></i>
									${lecture.name}</b></a>&nbsp; <b><small style="color: #9A9A9A;">${lecture.creatorName}</small></b>
							<div class="pull-right">
								<a rel="external" href='/lecture/${lecture.name}/delete'> <c:if
										test="${lecture.tmpPermission == 1}">
										<i class='icon-remove'></i>
									</c:if> <c:if test="${lecture.tmpPermission == 0}">
										<i class="fa fa-eraser"></i>
									</c:if>
								</a>
							</div></td>
					</tr>

					<tr>
						<td class="none-top-border manage-description">${lecture.description}</td>
					</tr>
					</c:forEach>
					<c:if test="${repoes.size() > 0}">
						<tr>
						<td class = "manage-title">
							숙제 (${repoes.size()})
						</td>
					</tr>
					</c:if>
					
					
					<c:forEach items="${repoes}" var="repo">
					
					<tr>
						<td><a rel="external" class="none-color" href='/lecture/${repo.lectureName}/repo/${repo.name}/browser'>
						<b style="white-space: nowrap">
							<c:if test="${repo.tmpPermission == 0}" >
											<i class='icon-list-alt'></i>
							</c:if>
							<c:if test="${repo.tmpPermission == 1}" >
											<i class='icon-briefcase'></i>
							</c:if>
									${repo.lectureName}/${repo.name}</b></a>&nbsp; 
									<b><small style="color: #9A9A9A;">${repo.creatorName}</small></b>
							<div class="pull-right">
								
								<c:if test="${repo.getDDay() >= 0}" >
											<span class='label label-important'>남은 기간 ${repo.getDDay()}일</span>
										</c:if>
										<c:if test="${repo.getDDay() == -2}">
											<span class='label label-info'>마감일 없음</span>
										</c:if>
								
							</div></td>
					</tr>

					<tr>
						<td class="none-top-border manage-description">${repo.description}</td>
					</tr>
					</c:forEach>
					
					<c:if test="${projects.size() > 0}">
					<tr>
						<td class = "manage-title">
							프로젝트 (${projects.size()})
						</td>
					</tr>
					</c:if>
					
					<c:forEach  items="${projects}" var="project">
					<tr>
						<td><a rel="external" class="none-color" href='/project/${project.name}/'><b
								style="white-space: nowrap"><i class='icon-book'></i>
									${project.name}</b></a>&nbsp; <b><small style="color: #9A9A9A;">${project.creatorName}</small></b>
							<div class="pull-right">
								<a rel="external" href='/project/${project.name}/delete'> <c:if
										test="${project.tmpPermission == 1}">
										<i class='icon-remove'></i>
									</c:if> <c:if test="${project.tmpPermission == 0}">
										<i class="fa fa-eraser"></i>
									</c:if>
								</a>
							</div></td>
					</tr>

					<tr>
						<td class="none-top-border manage-description">${project.description}</td>
					</tr>
					</c:forEach>
					
			</table>
		</div>
	</div>

</body>
</html>