<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/src.jsp" %>
    <title>ForWeaver - 학생들을 위한 소셜 코딩!</title>
</head>
<body>
    <script>
        $(document).ready(function() {
            $('#myCarousel').carousel({
                interval:   6000
            });
        });
    </script>
    <div class="container">
        <%@ include file="/WEB-INF/views/common/nav.jsp" %>
            <div class="row">
                <div class="col-md-8 hidden-xs">
                    <div id="myCarousel" class="carousel slide">
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                            <li data-target="#myCarousel" data-slide-to="2"></li>
                            <li data-target="#myCarousel" data-slide-to="3"></li>
                        </ol>
                        <!-- Carousel items -->
                        <div class="carousel-inner">
                            <div style="height: 406px;" class="active item text-center">
                                <embed src="/resources/svg/noun_project_8428.svg" width="240" height="240" type="image/svg+xml" />
                                <h5>학생들을 위한 소셜 코딩!</h5>
                                <p>소스코드 공유가 어려우셨나요?
                                <br>포위버에 오신것을 환영합니다!</p>
                            </div>
                            <div style="height: 406px;" class="item text-center">
                                <embed src="/resources/svg/noun_project_14888.svg" width="240" height="240" type="image/svg+xml" />
                                <h5>강의를 진행해보세요!</h5>
                                <p>새로운 숙제 제출 시스템을 원하시나요?
                                    <br>커밋 로그를 통해 숙제를 검사하세요!</p>
                            </div>
                            <div style="height: 406px;" class="item text-center">
                                <embed src="/resources/svg/noun_project_2174.svg" width="240" height="240" type="image/svg+xml" />
                                <h5>프로젝트를 개설해보세요!</h5>
                                <p>같이 프로젝트를 진행할 동료를 찾으시나요?
                                    <br>forweaver를 사용해보세요!</p>
                            </div>
                            <div style="height: 406px;" class="item text-center">
                                <embed src="/resources/svg/noun_project_5742.svg" width="240" height="240" type="image/svg+xml" />
                                <h5>커뮤니티에 질문해보세요!</h5>
                                <p>혹시 프로그래밍을 하다 궁금한게 있나요?
                                    <br>채팅과 커뮤니티를 이용해보세요!</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <form action="j_spring_security_check" method="post" id="loginForm" role="form">
                        <fieldset>
                            <legend>로그인</legend>
                            <hr/>
                            <div class="form-group">
                                <label for="inputUsernameEmail">닉네임 혹은 이메일</label>
                                <input type="text" class="form-control" name="j_username" id="j_username">
                            </div>
                            <div class="form-group">
                                <label for="inputPassword">비밀번호</label>
                                <input type="password" class="form-control" type="password" name="j_password" id="j_password">
                            </div>
                            <div class="form-group">
                                <label>
                                    <input type="checkbox"> 비밀번호 기억 </label>
                            </div>
                            <hr/>
                            <div class="form-group">
                                <button type="submit" style="width:49%" class="btn btn-inverse">
                                    <i class="fa fa-user"></i> 접속하기
                                </button>
                                <a href="/join" style="width:49%" class="btn btn-inverse">
                                    <i class="fa fa-pencil-square"></i> 회원가입
                                </a>
                            </div>
                        </fieldset>
                        <button class="btn btn-group-justified btn-primary">
                            <i class="fa fa-rocket"></i> ForWeaver.com을 소개합니다.
                        </button>
                    </form>
                </div>
            </div>
           
			<%@ include file="/WEB-INF/views/common/footer.jsp" %>
			 <!-- /container -->
    </div>
</body>

</html>
