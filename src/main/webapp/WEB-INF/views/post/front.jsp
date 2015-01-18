<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Forweaver : 소통해보세요!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/src.jsp" %>
</head>
<body>
<script>
var fileCount = 1;
var fileArray = [];
var fileHash = {};

function showWrite() { 
	
	$('#page-selection').hide();
	$('#forweaver-table').hide();
	$('#forweaver-content-textarea').fadeIn('slow');

	$('#show-write-button').hide();
	$('#hide-write-button').show();
	$('.file-div').fadeIn('slow');
	editorMode = true;
}

function hideWrite() {
	$('#page-selection').show();
	$('#forweaver-table').show();
	$('#forweaver-content-textarea').hide();

	$('#show-write-button').show();
	$('#hide-write-button').hide();
	$('.file-div').hide();
	editorMode = false;
}

function fileUploadChange(fileUploader){
	var fileName = $(fileUploader).val();			
	$(function (){
	if(fileName !=""){ // 파일을 업로드하거나 수정함
		if(fileName.indexOf("C:\\fakepath\\") != -1)
			fileName = fileName.substring(12);
		fileHash[fileName] = mongoObjectId();
		$.ajax({
		    url: '/data/tmp',
            type: "POST",
            contentType: false,
            processData: false,
            data: function() {
                var data = new FormData();
                data.append("objectID", fileHash[fileName]);
                data.append("file", fileUploader.files[0]);
                return data;
            }()
		});	
		$("#forweaver-content-textarea").val($("#forweaver-content-textarea").val()+' !['+fileName+'](/data/'+fileHash[fileName]+')');
	
		if(fileUploader.id == "file"+fileCount){ // 업로더의 마지막 부분을 수정함
	fileCount++;
	$(".file-div").append( //파일 업로드 추가.
			"<div class='fileinput fileinput-new input-group' data-provides='fileinput'>"+
			  "<div class='form-control' data-trigger='fileinput'><i class='glyphicon glyphicon-file fileinput-exists'></i> <span class='fileinput-filename'></span></div>"+
			  "<span class='input-group-addon btn btn-default btn-file'><span class='fileinput-new'>파일 선택</span><span class='fileinput-exists'>변경</span>"+
			  "<input id='file"+fileCount+"' name='files["+(fileCount-1)+"] multiple='true' onchange ='javascript:fileUploadChange(this);'  type='file' ></span>"+
			  "<a href='#' class='input-group-addon btn btn-default fileinput-exists' data-dismiss='fileinput'>삭제</a>"+
			"</div>");
		}
	}else{
		if(fileUploader.id == "file"+(fileCount-1)){ // 업로더의 마지막 부분을 수정함
			
		$("#file"+fileCount).parent().parent().remove();

			--fileCount;
	}}});
}


$(function(){
	
	hideWrite();
	
	$(".file-div").append( //파일 업로드 추가.
			"<div class='fileinput fileinput-new input-group' data-provides='fileinput'>"+
			  "<div class='form-control' data-trigger='fileinput'><i class='glyphicon glyphicon-file fileinput-exists'></i> <span class='fileinput-filename'></span></div>"+
			  "<span class='input-group-addon btn btn-default btn-file'><span class='fileinput-new'>파일 선택</span><span class='fileinput-exists'>변경</span>"+
			  "<input id='file1' multiple='true' name='files[0]' onchange ='javascript:fileUploadChange(this);'  type='file' ></span>"+
			  "<a href='#' class='input-group-addon btn btn-default fileinput-exists' data-dismiss='fileinput'>삭제</a>"+
			"</div>");
	
	
	$( "#"+getSort(document.location.href) ).addClass( "active" ); //주소를 읽어와서 순서 버튼을 활성화함.
	
	
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
		<%@ include file="/WEB-INF/views/common/nav.jsp" %>
		<div class="page-header">
			<h6>
				<i class=" fa fa-comments"></i> 소통해보세요!<small class="hidden-xs">  <small>프로젝트
					진행사항이나 궁금한 점들을 올려보세요!</small></small>
				<div style="margin-top: -10px" class="pull-right">

					<button class="btn btn-warning">
						<b><i class="fa fa-database"></i> ${postCount}</b>
					</button>

				</div>
			</h6>
		</div>
		<div class="row">
			<div class="col-md-12">
				<ul class="nav nav-tabs" id="myTab">
					<li id="age-desc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-desc/page:1">최신순</a></li>
					<c:if test="${massage == null }">
						<li id="push-desc"><a
							href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:push-desc/page:1">추천순</a></li>
					</c:if>
					<li id="repost-desc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-desc/page:1">최신
							답변순</a></li>
					<li id="repost-many"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-many/page:1">많은
							답변순</a></li>
					<li id="age-asc"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:age-asc/page:1">오래된순</a></li>
					<li id="repost-null"><a
						href="/community<c:if test="${tagNames != null }">/tags:${tagNames}</c:if><c:if test="${search != null }">/search:${search}</c:if>/sort:repost-null/page:1">답변
							없는 글</a></li>
				</ul>
			</div>

			<form id="post-form" onsubmit="return checkPost()"
				action="/community/add" enctype="multipart/form-data" METHOD="POST">

				 <div class="form-group col-md-9 col-lg-10">
				    <input class="form-control col-md-12" id="post-title-input" name="title"
										placeholder="찾고 싶은 검색어나 쓰고 싶은 단문의 내용을 입력해주세요!" type="text"/>
				  </div>
				<div class="col-md-3 col-lg-2 pull-right">
					<span> <a id='search-button'
						class="post-button btn btn-primary"> <i class="fa fa-search"></i>
					</a> <a id="show-write-button" href="javascript:showWrite();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a> <a style="display: none;" id="hide-write-button"
						href="javascript:hideWrite();"
						class="post-button btn btn-primary"> <i class="fa fa-pencil"></i>
					</a>
						<button id='post-ok' class="post-button btn btn-danger">
							<i class="fa fa-check"></i>
						</button>

					</span>
				</div>
				<div class="col-md-12">
					<textarea style="display: none;" class="form-control" 
					id="forweaver-content-textarea" name="content"
						class="col-md-12" onkeyup="textAreaResize(this)"
						placeholder="글 내용을 입력해주세요!"></textarea>
					<div class="file-div"></div>
				</div>
			</form>
			
			<div style="clear:both"></div>
			
			<div class="col-md-12">
				<table id="forweaver-table" class="table table-hover">
					<tbody>
						<c:forEach items="${posts}" var="post">
							<tr>
								<td class="forweaver-td-avatar" rowspan="2"><a
									href="/${post.writerName}"> <img class="forweaver-avatar" src="${post.getImgSrc()}"></a></td>
								<td colspan="2" class="post-top-title"><a
									class="none-color" href="/community/${post.postID}"> <c:if
											test="${post.isLong()}">
											<i class="fa fa-th-list"></i>
										</c:if> <c:if test="${!post.isLong()}">
											<i class="fa fa-comment"></i>
										</c:if> &nbsp;${post.title}
								</a></td>
								<td class="forweaver-td-span" rowspan="2"><c:if
										test="${post.kind == 3}">
										<a href="/community/${post.postID}/delete"> <span
											class="forweaver-span"> <i class="fa fa-trash-o"></i>
												<p>삭제</p>
										</span>
										</a>
									</c:if> <c:if test="${post.kind <= 2}">
										<a href="/community/${post.postID}/push"> <span
											class="forweaver-span"> ${post.push}
												<p>추천</p>
										</span>
										</a>
									</c:if></td>
								<td class="forweaver-td-span" rowspan="2"><a
									href="/community/${post.postID}"> <span class="forweaver-span">${post.rePostCount}
											<p>답변</p>
									</span></a></td>
							</tr>
							<tr>
								<td class="forweaver-td-info none-boder-top"><a href="/${post.writerName}"><b>${post.writerName}</b></a>
									${post.getFormatCreated()}</td>
								<td class="forweaver-td-tags none-boder-top"><c:forEach items="${post.tags}"
										var="tag">
										<span
											class="tag-name
										<c:if test="${tag.startsWith('@')}">
										tag-private
										</c:if>
										<c:if test="${tag.startsWith('$')}">
										tag-massage
										</c:if>
										">${tag}</span>
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
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>

</body>


</html>