$(document).ready(function(){	// 깃 메뉴를 숨김

	gitInit();
	pull();
	$(document).on('expand', '.ui-collapsible', function() {  // 에니메이션 효과 
	    $(this).children().next().hide();
	    $(this).children().next().slideDown(200);
	    $('.etcButton').hide();
	});

	$(document).on('collapse', '.ui-collapsible', function() { // 에니메이션 효과
	    $(this).children().next().slideUp(200);
	    $('.etcButton').show();
	});

    
	$('#selectmenuBranch').change(function() {
		changeBranch($("#selectmenuBranch option:selected").val());
		showCommitList(eval(loadCommitLogSimpleList())); // 커밋 로그를 가져와서 보여줌
	});
	
	$('#selectmenuCommitLog').change(function() {
		createCommitLogWindow($("#selectmenuCommitLog option:selected").val());
	});
	
	$(".title").append(getSelectProjectName());
	showBranchList(eval(loadBranchList())); // 브랜치를 가져와서 보여줌
	showCommitList(eval(loadCommitLogSimpleList())); // 커밋 로그를 가져와서 보여줌
});

function showBranchList(list){ // 프로젝트 내에 있는 브랜치 리스트를 브랜치 리스트 메뉴버튼에 추가함
	var branchList = list[0];
	var checkOutBranch = list[1];
	
		$("#selectmenuBranch > option").remove();
		
		if(branchList.length == 0){ // 지역 브랜치가 존재하지 않은 경우
			$("#selectmenuBranch").append("<option value=''>브랜치가 없습니다.</option>");
		}else if(checkOutBranch == ""){ // 지역 브랜치가 존재하지만 선택된 브랜치가 없는 경우
			$("#selectmenuBranch").append("<option value=''>브랜치를 선택하세요.</option>");
		}
		
		$.each(branchList, function(index, value) {
			$("#selectmenuBranch").append("<option value="+value+">"+value+"</option>");
		});
		$("#selectmenuBranch option[value=\'"+checkOutBranch+"\']").attr("selected","selected") ;
		$('#selectmenuBranch').selectmenu('refresh'); 

}


function showCommitList(commitList){ // 프로젝트 내에 있는 커밋 로그를 보여줌
	

		$("#selectmenuCommitLog > option").remove();
		
		if(commitList.length == 0){ // 지역 브랜치가 존재하지 않은 경우
			$("#selectmenuCommitLog").append("<option value=''>커밋 내역이 없습니다.</option>");
		}
		$("#selectmenuCommitLog").append("<option>커밋 로그 목록</option>");
		$.each(commitList, function(index, value) {
			$("#selectmenuCommitLog").append("<option value="+value[1]+">"+value[0]+"</option>");
		});
		$('#selectmenuCommitLog').selectmenu('refresh'); 
}

function createDeleteBranchWindow(){
	var branchName = $('#selectmenuBranch option:selected').val();
	createAlertWindow("브렌치 삭제하기",
			branchName+"를 삭제하시겠습니까?",//표시 내용
			"deleteBranch(\""+branchName+"\"); " +
			"showBranchList(eval(loadBranchList()));"); // 실행할 스크립트 브랜치 삭제하고 다시 보여줌
}

function createPullWindow(){
	createAlertWindow("프로젝트 업데이트",
			"원격 저장소의 프로젝트를 다시 가져 오시겠습니까?",//표시 내용
			"pull(); " +
			"showBranchList(eval(loadBranchList()));" +
			"showCommitList(eval(loadCommitLogSimpleList()));"); 
}

function createPushWindow(){
	createAlertWindow("작업내역 올리기",
			"현재까지 작업한 내역을 원격 저장소에 올리시겠습니까?",//표시 내용
			"push();"); 
}