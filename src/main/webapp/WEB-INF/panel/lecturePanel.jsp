<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {

		$( ".ui-icon-custom-icon" ).css( "background-image", "url(${lecture.getImgSrc()})");
		$( ".ui-icon-custom-icon" ).css( "background-size", "28px 28px");
	});
</script>

<div data-tap-toggle="false" data-role="panel" id="lecturePanel" data-position="left" data-theme="a" data-display="overlay">

        <div data-collapsed-icon="custom-icon" data-expanded-icon="custom-icon"  
        data-role="collapsible-set" data-theme="f" data-content-theme="c">
            <div data-role="collapsible">
            <h3>&nbsp;&nbsp;${lecture.name}</h3>
                  <p style= "font-size:10px">${lecture.description}</p>
            </div>
        </div>
	<div data-role="controlgroup">
				<a rel="external" data-icon="folder-close" data-role="button" data-theme="b" href="/lecture/${lecture.name}">예제 소스</a>
				<a rel="external" data-icon="comments" data-role="button" data-theme="b" href="/lecture/${lecture.name}/community">커뮤니티</a>
				<a rel="external" data-icon="briefcase" data-role="button" data-theme="b" href="/lecture/${lecture.name}/repo">저장소들</a> 
				<a rel="external" data-icon="group" data-role="button" data-theme="b" href="/lecture/${lecture.name}/weaver">수강생들</a>
				
	</div>
<a rel="external" data-icon="chevron-sign-up" data-role="button" data-theme="e" href="/manage">상위 메뉴</a>

</div>