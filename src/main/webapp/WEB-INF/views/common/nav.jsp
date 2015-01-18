<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<sec:authentication property="principal" var="currentUser" />

<script>
$(function(){
	$('#forweaver-tags').tagsinput({
		  confirmKeys: [13, 32, 44]
	});
});	
</script>
<!-- Static navbar -->
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">ForWeaver</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/lecture/"><i class="fa fa-university"></i>&nbsp;강의</a>
                </li>
                <li><a href="/project/"><i class="fa fa-bookmark"></i>&nbsp;프로젝트</a>
                </li>
                <li><a href="/code/"><i class="fa fa-rocket"></i>&nbsp;코드</a>
                </li>
                <li><a href="/community/"><i class="fa fa-comments"></i>&nbsp;커뮤니티</a>
                </li>
                <li><a href="/forweaver/"><i class="fa fa-flickr"></i>&nbsp;도움말</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize ifNotGranted="ROLE_USER, ROLE_ADMIN">
                    <li><a href="<c:url value=" /login " />"><i class="fa fa-user"></i>&nbsp;로그인</a>
                    </li>
                </sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_USER, ROLE_ADMIN">

                    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
							<img style="height:28px;width:28px;" src='<c:out value="${currentUser.getImgSrc()}" escapeXml="false"></c:out>' />&nbsp;&nbsp;${currentUser.username}
								<b class="caret"></b></a>
                        <ul class="dropdown-menu">

                            <li><a href="/"><i class="icon-white icon-home"></i>&nbsp;&nbsp;개인화면</a>
                            </li>
                            <li><a href="javascript:void(0);" onclick="openWindow('/${currentUser.id}/edit', 360, 500);"><i class="icon-cog"></i>&nbsp;&nbsp;정보수정</a>
                            </li>
                            <li><a href="/community/tags:$${currentUser.username}"><i
										class="icon-envelope"></i>&nbsp;&nbsp;메세지함</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="<c:url value=" /j_spring_security_logout " />">
                                    <i class="icon-off"></i>&nbsp;&nbsp;로그아웃
                                </a>
                            </li>
                        </ul>
                    </li>

                </sec:authorize>
            </ul>

        </div>
        <!--/.nav-collapse -->
        <input id = "forweaver-tags" placeholder="태그를 입력해 보세요!" name="tagsinput" class="tagsinput" />


    </div>
    <!--/.container-fluid -->
</nav>