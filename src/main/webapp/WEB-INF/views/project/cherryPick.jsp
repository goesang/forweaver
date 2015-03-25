<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
<script>

function acceptCherryPick(cherryPickID){
	if(!confirm('정말 체리픽 요청을 수락하시겠습니까?'))
		return;
	if ($("#selectBranch option:selected").val() != "empty_Branch")
		window.location =  
			"/project/${project.name}/cherry-pick/branch:"
			+$("#selectBranch option:selected").val()
			+"/id:"+cherryPickID+"/accept";
}

$(document).ready(function() {
	$('#tags-input').textext()[0].tags().addTags(
			getTagList("/tags:<c:forEach items='${project.tags}' var='tag'>	${tag},</c:forEach>"));
	$("select").selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});
	
});
</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header page-header-none">
			<h5>
				<big><big>	<c:if test="${!project.isForkProject()}">
							<i class="fa fa-bookmark"></i></c:if>
							<c:if test="${project.isForkProject()}">
							<i class="fa fa-code-fork"></i></c:if> 
							${project.name}</big></big>
				<small>${project.description}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/project/${project.name}/">브라우져</a></li>
					<li><a href="/project/${project.name}/commitlog">커밋</a></li>
					<li><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="javascript:void(0);"
						onclick="openWindow('/project/${project.name}/chat', 400, 500);">채팅</a></li>
					<li><a href="/project/${project.name}/weaver">사용자</a></li>
					<li><a href="/project/${project.name}/edit">관리</a></li>
					<li><a href="/project/${project.name}/info">정보</a></li>
					<li class="active"><a href="/project/${project.name}/cherry-pick">체리 바구니</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend" title="http 주소로 저장소를 복제할 수 있습니다!&#13;복사하려면 ctrl+c 키를 누르세요.">
					<span class="add-on"><i class="fa fa-git"></i></span> <input
						value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/g/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
						<div class="span12 row">
				<div class="span8">
					<h4 style="margin: 10px 0px 0px 0px"><i class="icon-addtocart"></i> 체리 목록</h4>
				</div>
				<div style="width: 40px;" class="span1">
					<a id="show-content-button" class="btn btn-primary" title="프로젝트 포크"
						onclick="return confirm('정말 프로젝트를 포크하시겠습니까?')" href="/project/${project.name}/fork"> <i
						 class="fa fa-code-fork"> </i></a> 

				</div>
				<select id="selectBranch" class="span3">
					<option
						value="${selectBranch}">${selectBranch}</option>
					<c:forEach items="${gitBranchList}" var="gitBranchName">
						<option
							value="${gitBranchName}">${gitBranchName}</option>
					</c:forEach>
				</select>
				<table class="table table-hover">
					<tbody>
						<c:forEach items="${cherryPicks}" var="cherryPick">
							<tr class="cherry-pick-tr">
								<td><img class="cherry-pick-img"src="${cherryPick.getRequestWeaver().getImgSrc()}"> &nbsp;
								<a href="/${cherryPick.getRequestWeaver().getId()}">${cherryPick.getRequestWeaver().getId()}</a></td>
								<td><a class="none-color" href="/project/${cherryPick.getCherryPickProject().getName()}">
									<i class="fa fa-code-fork"></i> ${cherryPick.getCherryPickProject().getName()}</a> 
								</td>
								<td>
								<a class="none-color" href="/project/${cherryPick.getCherryPickProject().getName()}/commitlog-viewer/commit:${cherryPick.getCommitID()}">
								<span class = "tag-commit tag-name">
									<i class="icon-cherry"></i> &nbsp;${cherryPick.getCommitID()}</span>
								</a>	
								&nbsp;
								 <span class="label label-inverse">${cherryPick.getState()}</span> 
								</td>
								<td><a onclick="acceptCherryPick('${cherryPick.getId()}');" href="#"><i class="fa fa-check"></i></a>&nbsp;&nbsp;
								<a onclick="return confirm('정말 체리픽 요청을 삭제하시겠습니까?')"
								href="/project/${project.name}/cherry-pick/id:${cherryPick.getId()}/delete">
								<i class="fa fa-times"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- .span9 -->

		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>
