function showRepoList(index) {
	$("#repoTable").empty();
		if(index <=0)
			return;
		
		for(var i = (index-1)*10; i<=(index-1)*10+9;i++){
			var appendString = "";
			if(repoList.length == i)
				break;
			if(repoList[i]["name"] != "example"){
				appendString = "<tr><td style='width:800px;'>" +			
					"<i class='fa fa-briefcase'></i>&nbsp;" +
					"<a class='none-color' href='/lecture/"+lectureName+"/repo/"+
					repoList[i]["name"] +
					"/browser'>"+		
					"<b>" +
					repoList[i]["name"] +
					"</b></a> - <small>" +
					repoList[i]["openDate"]  +
					"&nbsp;&nbsp;"+
					repoList[i]["description"] +
					"</small></td>";
				
				if(repoList[i]["d-day"] == -2){
					appendString += "<td><span class='label label-info'>" +
					"마감일 없음" +
					"</span></td>";
				}else if(repoList[i]["d-day"] == -1){
					appendString += "<td><span class='label label-success'> " +
							"기한 지남 " +
						"</span></td>";
				}else{
					appendString += "<td><span class='label label-important'> 남은 기간 " +
					repoList[i]["d-day"] +
					"일</span></td>";
				}
				
				appendString += "<td>"+
				"<a class='none-color' href='/lecture/"+lectureName+"/repo/"+
				repoList[i]["name"] +
				"/delete'>"+	
				"<i class='icon-remove'></i></a>" +
				"</td></tr>";
			
			}
			$("#repoTable").append(appendString);
		}
}


function makeNavigationInManageRepo(size,length){ // 사이즈는 위버의 총 갯수, 랭스는 보여줄 위버 수
	size -= 1;
	if(size <= 0)
		return;
	var pageLength = parseInt((size-1) / length); // 페이지 갯수
	
		$("#pageNavigation").empty();
		
		var html = "<ul>" +
		"<li class='previous'><a class='fui-arrow-left'></a></li>" +
		"<li class='page active'><a class ='page-link' href='javascript:showRepoList(1);'>1</a></li>";
		
		for(var i =0 ; i < pageLength ; i++){
			var j = i+2;
			html+="<li class='page'><a class ='page-link' href='javascript:showRepoList("+j+");'>"+j+"</a></li>";
		}
				
		$("#pageNavigation").append(html+
				"<li class='next'><a class='fui-arrow-right'></a></li>" +
				"</ul>");	
		
		if(pageLength>=5){
			for(var i = 5;i<pageLength+1;i++)
				$("a.page-link:eq("+i+")").hide();
		}	
}