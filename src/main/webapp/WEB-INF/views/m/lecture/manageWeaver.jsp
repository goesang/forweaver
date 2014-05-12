<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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

		<div data-tap-toggle="false" data-position="fixed" data-theme="a"
			data-role="header">
			<div class="ui-btn-left" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#lecturePanel" data-role="button" data-iconpos="notext"
					data-icon="gear"></a>
			</div>
			<h1>${lecture.name}</h1>
			<div class="ui-btn-right" data-role="controlgroup"
				data-type="horizontal" data-mini="true">
				<a href="#mainPanel" data-role="button" data-iconpos="notext"
					data-icon="reorder"></a>
			</div>

		</div>
		<div data-role="content">

			<table id="weaverTable" class="table table-hover">

				<c:forEach items="${lecture.adminWeavers}" var="adminWeaver">
					<tr>
						<td class='td-post-writer-img'><img
							src='${adminWeaver.getImgSrc()}'></td>
						<td class='vertical-middle'>${adminWeaver.nickName}</td>
						<td class='vertical-middle'>${adminWeaver.email}</td>
						<td style='width: 42px;' class='td-button'><span
							class='span-button'><i class='icon-user icon-white'></i>
								<p class='p-button-small'>괸리자</p></span></td>
					</tr>
				</c:forEach>

				<c:forEach items="${lecture.joinWeavers}" var="joinWeaver">
					<tr>
						<td class='td-post-writer-img'><img
							src='${joinWeaver.getImgSrc()}'></td>
						<td class='vertical-middle'>${joinWeaver.nickName}</td>
						<td class='vertical-middle'>${joinWeaver.email}</td>
						<td style='width: 42px;' class='td-button'><a
							href='/lecture/${lecture.name}/weaver:${joinWeaver.nickName}/delete'>
								<span class='span-button'><i class='icon-user icon-white'></i>
									<p class='p-button'>탈퇴</p></span>
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
</body>
</html>