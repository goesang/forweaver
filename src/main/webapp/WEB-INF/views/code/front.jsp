<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 공유해보세요!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">
		function showCodeContent() {
			$('#page-selection').hide();
			$('#post-table').hide();
			$('#post-ok').show();
			$('#search-button').hide();
			$('#search-div').hide();
			$('#post-div').fadeIn('slow');
			$('#show-write-button').hide();
			$('#hide-write-button').show();
			$('#file-div').fadeIn('slow');
			$('#forweaver-table').hide();
			//editorMode = true;
		}

		function hideCodeContent() {
			$('#page-selection').show();
			$('#post-table').show();
			$('#search-div').show();
			$('#post-div').hide();
			$('#post-ok').hide();
			$('#search-button').show();
			$('#show-write-button').show();
			$('#hide-write-button').hide();
			$('#file-div').hide();
			$('#forweaver-table').show();
			//editorMode = false;
		}
		$(function(){
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
			var pageCount = ${postCount+1}/${number}; //총 페이지 갯수를 계산함
			pageCount = Math.ceil(pageCount);
			
			$('#page-selection').twbsPagination({ // 페이지 네비게이터
		        totalPages: pageCount,
		        first:"<<",
		        prev:"<",
		        next:">",
		        last:">>",
		        visiblePages: 10,
		        startPage : ${pageIndex},
		        href: "${pageUrl}"+'{{number}}'
		    });
		});
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/views/common/nav.jsp"%>
		<div class="page-header">
			<alert></alert>
			<h6>
				<i class="fa fa-rocket"></i> 공유해보세요!<small class="hidden-xs"> <small>자신의
					소스코드를 다른 사람들에게 배포해보세요!</small></small>
				<div style="margin-top: -10px" class="pull-right">

					<button class="btn btn-warning">
						<b><i class="fa fa-database"></i> ${codeCount}</b>
					</button>

				</div>
			</h6>
		</div>
		<div class="row">
			<div class="col-md-12">
				<ul class="nav nav-tabs" id="myTab">
					<li id="age-desc"><a
						href="/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<c:if test="${massage == null }">
						<li id="download-desc"><a
							href="/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:download-desc/page:1">다운로드순</a></li>
					</c:if>
					<li id="repost-desc"><a
						href="/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1">최신
							답변순</a></li>
					<li id="repost-many"><a
						href="/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1">많은
							답변순</a></li>
					<li id="age-asc"><a
						href="/code<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>
					<li id="repost-null"></li>
				</ul>
			</div>
			<div id="search-div" class="col-md-10 col-xs-9">
				<input id="post-search-input" class="form-control" placeholder="검색어를 입력하여 코드를 찾아보세요!" style="margin-bottom: 10px;"
					type="text" />
			</div>
			<form id="post-form" onsubmit="return checkCode()" action="/code/add"
				enctype="multipart/form-data" method="post">
				<div id="post-div" class="form-group col-md-10 col-xs-9" style="display: none;">
					<input name="name" id="post-name" class="form-control col-md-3 col-xs-2"
						style="width:25%;" placeholder="코드명을 입력해주세요!" type="text" />
					<input name="content" id="post-content" class="form-control col-md-8 col-xs-7"
						style="width:66.66666667%; margin-left: 5px;" placeholder="소스 코드에 대해 소개해주세요!"
						type="text" />
				</div>
				<div class="col-md-2 col-xs-3">
					<span> <a id="show-write-button"
						href="javascript:showCodeContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> <a id='search-button' class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> <a id="hide-write-button" href="javascript:hideCodeContent();" style="display:none;"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a>
						<button id='post-ok' class="post-button btn btn-primary" style="display:none;">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
				<div id="file-div" class="form-group col-md-9 col-xs-9" style="display: none;">
						<div class='fileinput fileinput-new input-group'
							data-provides='fileinput'>
							<div class='form-control' data-trigger='fileinput'>
								<i class='glyphicon glyphicon-file fileinput-exists'></i> <span
									class='fileinput-filename'></span>
							</div>
							<span class='input-group-addon btn btn-default btn-file'><span
								class='fileinput-new'>파일 선택</span> <span class='fileinput-exists'>변경</span>
								<input type='file' id='file' multiple='true' name='file'></span>
							<a href='#'
								class='input-group-addon btn btn-default fileinput-exists'
								data-dismiss='fileinput'>삭제</a>
						</div>
					</div>
				
				
				<!--<div class="pull-right"><a class="btn btn-inverse">
					<i class="fa fa-pencil"></i> 코드 직접 입력하기</a></div>
				</div>
				  <div class="col-md-12">
					<textarea name="content" id="post-content-textarea"
						class="post-content col-md-12" onkeyup="textAreaResize(this)"
						placeholder="여기에 글을 작성하시면 파일 배포시 자동으로 readme.md 파일이 생성됩니다. 만약 코드 소개에 충분히 설명하셨다면 이부분을 비워두셔도 상관없습니다!"></textarea>
					<div class="file-div"></div>

				</div>
				
				<div class="col-md-12">
				<input id="post-search-input" class="title col-md-6"
						placeholder="파일명을 입력해주세요. 예시 hello.java 또는 folder/hello.java" type="text" />

					<textarea name="content" id="post-content-textarea"
						class="post-content col-md-12" onkeyup="textAreaResize(this)"
						placeholder="소스 코드를 입력해주세요!"></textarea>
					<div class="file-div"></div>

				</div>

-->
			</form>

			<div class="col-md-12">

				<table id="forweaver-table" class="table table-hover">
					<tbody>
						<c:forEach items="${codes}" var="code">
							<tr>
								<td class="forweaver-td-avatar" rowspan="2"><img class="forweaver-avatar"
									src="${code.getImgSrc()}"></td>
								<td colspan="2" class="post-top-title"><a
									class="none-color" href="/code/${code.codeID}"> <i
										class="fa fa-download"></i>&nbsp;${code.name} -
										${code.content}
								</a></td>
								<td class="forweaver-td-span" rowspan="2"><a
									href="/code/${code.codeID}/${code.name}.zip"> <span
										class="forweaver-span"> ${code.downCount}
											<p class="p-button">다운</p>
									</span>
								</a></td>
								<td class="forweaver-td-span" rowspan="2"><a
									href="/code/${code.codeID}"> <span class="forweaver-span">${code.rePostCount}
											<p class="p-button">답변</p>
									</span></a></td>
							</tr>
							<tr>
								<td class="forweaver-td-info none-boder-top"><b>${code.writerName}</b>
									${code.getFormatCreated()}</td>
								<td class="forweaver-td-tags none-boder-top"><c:forEach items="${code.tags}"
										var="tag">
										<span class="tag-name">${tag}</span>
									</c:forEach></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>


				<div class="text-center">
					<div id="page-selection"></div>
				</div>
			</div>

		</div>
		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>

</body>
</html>