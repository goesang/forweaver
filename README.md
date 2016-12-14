모두를 위한 소셜 코딩 포위버!
=======

이 프로젝트는 모두를 위한 소셜 코딩을 목표로 Spring MVC + MongoDB를 활용하여 만든 깃 저장소 관리 사이트입니다. 

저희의 목표는 학생들이 어려울 수 있는 버젼 관리 시스템이나 개발자용 커뮤니티(위키 & 이슈트래커 등)를 학생 시각에서 다룰 수 있게 하는 것이며,
또한 GIT 저장소를 보다 손쉽게 수업에 활용하게 하는 것을 목표로 하고 있습니다.

현재 d2fest를 급히 진행하면서 여러 가지 자잘한 버그나 주석을 달지 못한 부분이 많지만 빠른 시일 내에 부족한 부분을 채울 것을 약속드립니다.

실행 데모는 실행 화면이나 설명은 아래 스크린샷과 링크를 참조해주시면 감사하겠습니다.

배포 라이센스는 MIT License하의 배포되며 그외 저희가 사용한 라이브러리는 NOTICE.txt를 참조하시면 됩니다.

## 실행방법

우선 몽고디비(/src/main/resources/spring/applicationContext.xml에서 설정)를 실행하시고 

/src/main/webapp/WEB-INF/web.xml에서 git 저장소 (기본 /home/git/)를 설정합니다.

설정이 끝나면 war파일을 만들어 실행하시거나 메이븐에서 다음 명령어를 실행합니다.

> mvn tomcat7:run

그리고 나서 

> http://127.0.0.1:8080

에 접속해주시면 됩니다.

## 주요 스크린샷
### 로그인 화면
![](http://i.imgur.com/E6BfGLE.png)

### 프로젝트 관리 화면
![](http://i.imgur.com/PVMS0Rp.png)

### 코드뷰어 화면 
![](http://i.imgur.com/qK6aybj.png)

### 프로젝트 시각화 화면
![](http://i.imgur.com/p82fvHH.png)
![](http://i.imgur.com/LZ0AbW0.png)

### 태그 커뮤니티 화면 
![](http://i.imgur.com/KbYStPA.png)
