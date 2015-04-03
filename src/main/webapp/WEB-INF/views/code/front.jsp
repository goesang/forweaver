<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html><head>
<title>Forweaver : 공유해보세요!</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
	<script type="text/javascript">
	
	function checkCode(){
		var objPattern = /^[a-z0-9_]+$/;
		var fileName = $("#file").val();
		var tags = $("#tags-input").val();
		
		if(fileName == ""){
			alert("파일을 업로드해 주세요!");
			return false;
		}else if(!objPattern.test($('#code-name').val())){
			alert("코드명은 영문 숫자 조합이어야 합니다. 다시 입력해주세요!");
			return false;
		}else if(tags.length == 0){
			alert("태그를 하나라도 입력해주세요!");
			return false;
		}else if($('#code-name').val().length <5 ){
			alert("코드명을 5자 이상 입력하지 않았습니다!");
			return false;
		}else if($('#code-content').val().length <5 ){
			alert("코드 설명을 5자 이상 입력하지 않았습니다!");
			return false;
		}else{
			$("form:first").append($("input[name='tags']"));
			return true;
		}
	}
	
		function showCodeContent() {
			var tags = $("#tags-input").val();
			if(tags.length == 0){
				alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
				return;
			}
			$('#page-pagination').hide();
			$('#post-table').hide();
			$('#code-content-textarea').fadeIn('slow');
			$('#post-ok').show();
			$('#search-button').hide();
			$('#search-div').hide();
			$('#post-div').fadeIn('slow');
			$('#show-content-button').hide();
			$('#hide-content-button').show();
			$('#file-div').fadeIn('slow');
			editorMode = true;
		}

		function hideCodeContent() {
			$('#page-pagination').show();
			$('#post-table').show();
			$('#search-div').show();
			$('#post-div').hide();
			$('#code-content-textarea').hide();
			$('#post-ok').hide();
			$('#search-button').show();
			$('#show-content-button').show();
			$('#hide-content-button').hide();
			$('#file-div').hide();
			editorMode = false;
		}

		$(function() {
			
			hideCodeContent();
			
			$( "#post-search-input" ).focus(function() {
				var tags = $("#tags-input").val();
				if(tags.length == 0){
					$( "#post-search-input" ).val('');
					alert("태그가 하나도 입력되지 않았습니다. 태그를 먼저 입력해주세요!");
					return;
				}
			});
			
			
			$( "#"+getSort(document.location.href) ).addClass( "active" );
			
					$('.tag-name').click(
							function() {
								var tagname = $(this).text();
								var exist = false;
								var tagNames = $("#tags-input").val();
								if (tagNames.length == 0 || tagNames == "")
									movePage(tagname,"");
								$.each(tagNames.split(","), function(index, value) {
									if (value == tagname)
										exist = true;
								});
								if (!exist){
									movePage(tagNames+ ","+ tagname+" ","");
								}
							});
					
					$('#search-button').click(
							function() {
									var tagNames = $("#tags-input").val();
									movePage(tagNames,$('#post-search-input').val());							
							});
					
					$('#post-search-input').val(getSearchWord(document.location.href));
					
					$('#post-search-input').keyup(
							function(e) {
								if(!editorMode && e.keyCode == 13){
									var tagNames = $("#tags-input").val();
									movePage(tagNames,$('#post-search-input').val());
								}
							});
					var pageCount = ${codeCount+1}/${number};
					pageCount = Math.ceil(pageCount);					
					var options = {
				            currentPage: ${pageIndex},
				            totalPages: pageCount,
				            pageUrl: function(type, page, current){

				                return "${pageUrl}"+page;

				            }
				        }

				        $('#page-pagination').bootstrapPaginator(options);$('a').attr('rel', 'external');
		});

		
	</script>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>
		<div class="page-header page-header-none">
			<alert></alert>
			<h5>
				<big><big><i class="fa fa-rocket"></i> 공유해보세요!</big></big> <small>자신의
					소스코드를 다른 사람들에게 배포해보세요!</small>
				<div style="margin-top: -10px" class="pull-right" title='전체 코드 갯수&#13;${codeCount}개'>

					<button class="btn btn-warning">
						<b><i class="fa fa-database"></i> ${codeCount}</b>
					</button>

				</div>
			</h5>
		</div>
		<div class="row">
			<div class="span12">
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
			<div id="search-div" class="span10">
				<input id="post-search-input" class="title span10"
					placeholder="검색어를 입력하여 코드를 찾아보세요!" type="text" />
			</div>
			<form onsubmit="return checkCode()" id="codeForm" action="/code/add"
				enctype="multipart/form-data" method="post">

				<div id="post-div" class="span10">
					<input maxlength="15" name="name" id="code-name" class="title span3"
						placeholder="코드명 (영문 숫자 조합)" type="text" /> <input name="content"
						id="code-content" class="title span7" maxlength="200"
						placeholder="소스 코드에 대해 소개해주세요!" type="text" />
				</div>

				<div class="span2">


					<span> 
					<sec:authorize access="isAuthenticated()">
					<a id="show-content-button" title='코드 게시하기'
						href="javascript:showCodeContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> 
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<button disabled="disabled" title="로그인을 하셔야 코드를 업로드 할 수 있습니다!" class="post-button btn btn-primary">
							<i class="fa fa-times"></i>
						</button>
					</sec:authorize>
					
					<a id='search-button' title='코드 검색하기' class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> 
					
					<a id="hide-content-button" title='작성 취소하기'
						href="javascript:hideCodeContent();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a>
						
						<button id='post-ok' title='코드 올리기' class="post-button btn btn-primary">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
				<div id="file-div" style="padding-left: 20px;">
					<div class='fileinput fileinput-new' data-provides='fileinput'>
						<div class='input-group'>
							<div class='form-control' data-trigger='fileinput' title='업로드할 파일을 선택하세요!'>
								<i class='icon-file '></i> <span class='fileinput-filename'></span>
							</div>
							<span class='input-group-addon btn btn-primary btn-file'><span
								class='fileinput-new'> <i class='fa fa-arrow-circle-o-up icon-white'></i></span>
								<span class='fileinput-exists'><i
									class='icon-repeat icon-white'></i></span><input pl type='file'
								id='file' multiple='true' name='file'></span> <a href='#'
								class='input-group-addon btn btn-primary fileinput-exists'
								data-dismiss='fileinput'><i class='icon-remove icon-white'></i></a>
						</div>
					</div>
				</div>
				<!--<div class="pull-right"><a class="btn btn-inverse">
					<i class="fa fa-pencil"></i> 코드 직접 입력하기</a></div>
				</div>
				  <div class="span12">
					<textarea name="content" id="code-content-textarea"
						class="code-content span12" 
						placeholder="여기에 글을 작성하시면 파일 배포시 자동으로 readme.md 파일이 생성됩니다. 만약 코드 소개에 충분히 설명하셨다면 이부분을 비워두셔도 상관없습니다!"></textarea>
					<div class="file-div"></div>

				</div>
				
				<div class="span12">
				<input id="post-search-input" class="title span6"
						placeholder="파일명을 입력해주세요. 예시 hello.java 또는 folder/hello.java" type="text" />

					<textarea name="content" id="code-content-textarea"
						class="code-content span12" 
						placeholder="소스 코드를 입력해주세요!"></textarea>
					<div class="file-div"></div>

				</div>

-->
			</form>

			<div class="span12">

				<table id="post-table" class="table table-hover">
					<tbody>
						<c:forEach items="${codes}" var="code">
							<tr>
								<td class="td-post-writer-img" rowspan="2"><a href="/${code.writerName}"><img
									src="${code.getImgSrc()}"></a></td>
								<td colspan="2" class="post-top-title"><a
									class="a-post-title" href="/code/${code.codeID}"> <i
										class="fa fa-download"></i>&nbsp;${cov:htmlEscape(code.name)} -
										${cov:htmlEscape(code.content)}
								</a></td>
								<td class="td-button" rowspan="2"><a
									href="/code/${code.codeID}/${cov:htmlEscape(code.name)}.zip"> <span
										class="span-button"> ${code.downCount}
											<p class="p-button">다운</p>
									</span>
								</a></td>
								<td class="td-button" rowspan="2"><a
									href="/code/${code.codeID}"> <span class="span-button">${code.rePostCount}
											<p class="p-button">답변</p>
									</span></a></td>
							</tr>
							<tr>
								<td class="post-bottom"><a href="/${code.writerName}"><b>${code.writerName}</b></a>
									${code.getFormatCreated()}</td>
								<td class="post-bottom-tag"><c:forEach items="${code.tags}"
										var="tag">
										<span class="tag-name">${tag}</span>
									</c:forEach></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>


				<div class="text-center">
					<div id="page-pagination"></div>
				</div>
			</div>

		</div>
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>

</body>


</html>
