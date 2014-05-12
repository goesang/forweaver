<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {

		$( ".ui-icon-custom-icon" ).css( "background-image", "url(${project.getImgSrc()})");
		$( ".ui-icon-custom-icon" ).css( "background-size", "28px 28px");
	});
</script>

<div data-tap-toggle="false" data-role="panel" id="projectPanel" data-position="left" data-theme="a" data-display="overlay">

        <div data-collapsed-icon="custom-icon" data-expanded-icon="custom-icon"  
        data-role="collapsible-set" data-theme="f" data-content-theme="c">
            <div data-role="collapsible">
            <h3>&nbsp;&nbsp;${project.name}</h3>
                  <p style= "font-size:10px">${project.description}</p>
            </div>
        </div>
<div data-role="controlgroup">
		<a rel="external" data-icon="folder-close" data-role="button" data-theme="b" href="/project/${project.name}">프로젝트 브라우져</a>
			<a rel="external" data-icon="info-sign" data-role="button" data-theme="b" href="/project/${project.name}/commitlog">커밋 내역</a> 
			<a rel="external" data-icon="comments" data-role="button" data-theme="b" href="/project/${project.name}/community">커뮤니티</a>
			<a rel="external" data-icon="group" data-role="button" data-theme="b" href="/project/${project.name}/weaver">참가자 목록</a>
			
</div>
<a rel="external" data-icon="chevron-sign-up" data-role="button" data-theme="e" href="/manage">상위 메뉴</a>

</div>