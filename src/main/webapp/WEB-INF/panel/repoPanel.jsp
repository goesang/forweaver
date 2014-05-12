<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div data-tap-toggle="false" data-role="panel" id="repoPanel" data-position="left" data-theme="a" data-display="overlay">

        <div data-collapsed-icon="briefcase" data-expanded-icon="briefcase"  
        data-role="collapsible-set" data-theme="f" data-content-theme="c">
            <div data-role="collapsible" data-collapsed="false">
            <h3>&nbsp;&nbsp;${repo.name}</h3>
                  <p style= "font-size:10px">${repo.description}</p>
            </div>
        </div>
	<div data-role="controlgroup">
				<a rel="external" data-icon="folder-close" data-role="button" data-theme="b" href="/lecture/${repo.lectureName}/repo/${repo.name}/browser">소스 목록</a>
				<a rel="external" data-icon="info-sign" data-role="button" data-theme="b" href="/lecture/${repo.lectureName}/repo/${repo.name}/commitlog">커밋 내역</a>
	</div>
<a rel="external" data-icon="chevron-sign-up" data-role="button" data-theme="e" href="/lecture/${repo.lectureName}">강의 페이지</a>

</div>