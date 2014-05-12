<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script>
	$(document).ready(function() {

		$( ".ui-icon-custom-icon" ).css( "background-image", "url(http://forweaver.com/resources/forweaver/img/weaver.jpg)");
		$( ".ui-icon-custom-icon" ).css( "background-size", "28px 28px");
	});
</script>

<div data-tap-toggle="false" data-role="panel" id="chatPanel" data-position="left" data-theme="a" data-display="overlay">

        <div data-tap-toggle="false" data-role="collapsible-set" data-theme="f" data-content-theme="c">
            <div data-role="collapsible">
            <h3>채팅방 소개</h3>
                  <p style= "font-size:10px">${chat.description}</p>
            </div>
        </div>
        <div id = "user-group" data-role="controlgroup">
		</div>
<a rel="external" data-icon="chevron-sign-up" data-role="button" data-theme="e" href="/m/chat/">채팅방 나가기</a>

</div>