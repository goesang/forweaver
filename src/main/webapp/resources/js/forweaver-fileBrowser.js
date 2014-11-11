
function IndexOf(Val, Str, x) {
	if (x <= (Str.split(Val).length - 1)) {
		Ot = Str.indexOf(Val);
		if (x > 1) {
			for (var i = 1; i < x; i++) {
				var Ot = Str.indexOf(Val, Ot + 1)
			}
		}
		return Ot;
	} else {
		alert(Val + " Occurs less than " + x + " times");
		return 0
	}
}

function fileListTransform(list) {
	var fileBrowserTree = new Object();
	fileBrowserTree["/"] = new Array();
	$.each(list, function(index, value) {
		if (value["depth"] == 0) { // 최상위 디렉토리 파일의 경우
			fileBrowserTree["/"].push(value);
		} else {
			var parentDirectoryList = getParentDirectory(value["path"], value["depth"]);
			//부모 디렉토리 path를 다 가져옴 ["/","/aaa","/aaa/bbb"] 이런식의 문자열 배열
			$.each(parentDirectoryList, function(index, value) {
				if (!$.isArray(fileBrowserTree[value]))
					fileBrowserTree[value] = new Array();
			});
			for (var i = 0; i < parentDirectoryList.length - 1; i++) {//디렉토리를 만들어 트리에 추가함
				
				if (!existDirectory(fileBrowserTree[parentDirectoryList[i]], parentDirectoryList[i + 1]))
					fileBrowserTree[parentDirectoryList[i]].unshift(makeDirectory(parentDirectoryList[i + 1], value));
				else
					modifyDirectory(fileBrowserTree[parentDirectoryList[i]], parentDirectoryList[i + 1],value);
			}
			fileBrowserTree[parentDirectoryList[parentDirectoryList.length - 1]].push(value); // 파일들을 생성된 디렉토리에 추가
		}
	});
	return fileBrowserTree;
}

function showFileBrowser(directoryPath) {
	
	var parentDirectoryPath = makeParentDirectoryPath(directoryPath);
	
	$(document).ready(function() {
		if(directoryPath == "/"){
			$(".readme").show();
			$(".readme-header").show();
		}else{
			$(".readme").hide();
			$(".readme-header").hide();
		}
		$('#labelPath').empty();
		$('#labelPath').append(directoryPath);
		$("#fileBrowserTable").empty();
		if(directoryPath!="/")
			$("#fileBrowserTable").append("<tr><td style='border-top:0px;'>"+
					"<img src ='/resources/forweaver/img/directory.png'>"+
					"</td><td colspan = '3' style='border-top:0px;' class= 'td-filename'>" +
					"<a href='javascript:showFileBrowser(\"" + parentDirectoryPath + "\")'>상위 디렉토리</a>" + 
				"</td></tr>");
		
		$.each(fileBrowserTree[directoryPath], function(index, value) {
			var appendHTML = "";
			if (value["directory"]) {
				appendHTML = "<tr>" +
						"<td class='td-icon'><a href='javascript:showFileBrowser(\"" + value["path"] + "\")'><img src ='/resources/forweaver/img/directory.png'></a></td>" + 
				"<td class = 'td-filename'><a href='javascript:showFileBrowser(\"" + value["path"] + "\")'>" + 
				value["name"] + 
				"</a></td>" + 
				"<td class = 'td-commitlog'>";

			} else {
				appendHTML = "<tr>" +
				"<td class='td-icon'>" +
				"<a rel='external' href='"+
				fileBrowserURL+
				value["commitID"]+
				"/filepath:"+
				filePathTransform(value["path"].substring(1,value["path"].length))+"'>" + 
				"<img src ='/resources/forweaver/img/file.png'></a></td>" + 
				"<td class = 'td-filename'>" +
				"<a rel='external' href='"+
				fileBrowserURL+
				value["commitID"]+
				"/filepath:"+
				filePathTransform(value["path"].substring(1,value["path"].length))+"'>" + 
				value["name"] + 
				"</a></td><td class = 'td-commitlog'>";
			}
			
			//이미지를 추가함
			appendHTML+="<a href='/"+value["commiterEmail"].replace('.',',')+"'><img class='td-commitlog-img' src='/"
				+value["commiterEmail"].replace('.',',')+"/img' title='"+value["commiterName"]+"<"+value["commiterEmail"]+">'></a>&nbsp;&nbsp;";
				
			if(commitlogHref.length == 0){
				appendHTML = appendHTML + value["commitLog"] + "</td>" + 
				"<td class = 'td-time'>" + value["date"] + "</td></tr>"; 
			}else{
				appendHTML = appendHTML + "<a rel='external'  class='none-color' href ="+ commitlogHref+value["commitID"]+">"+
				value["commitLog"] + 
				"</a></td>" + 
				"<td class = 'td-time'>" + 
				"<a rel='external'  class='none-color' href ="+ commitlogHref+value["commitID"]+">"+
				value["date"] + 
				"</a></td></tr>"; 
			}
				
			$("#fileBrowserTable").append(appendHTML);
			//화면 크기에 따라 다르게 출력
			 if ($(window).width() > 500) {
			     	$( ".td-commitlog" ).show();
			    }else{
			    	$( ".td-commitlog" ).hide();
			    }
		});
	});
}

function existDirectory(fileList, directoryPath) {
	var exist = false;
	var drectoryName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1, directoryPath.length);
	
	$.each(fileList, function(index, value) {
		if (value["name"] == drectoryName)
			exist = true;
	});
	return exist;
}

function modifyDirectory(fileList, directoryPath,file) {
	var drectoryName = directoryPath.substring(directoryPath.lastIndexOf("/") + 1, directoryPath.length);
	$.each(fileList, function(index, value) {
		if (value["name"] == drectoryName && value["directory"]){
			if(value["dateInt"]<file["dateInt"]){
				value["commitLog"] = file["commitLog"];
				value["dateInt"] = file["dateInt"];
				value["date"] = file["date"];
				value["commitID"] = file["commitID"];
				value["commiterName"] = file["commiterName"];
				value["commiterEmail"] = file["commiterEmail"];
			}
		}
	});

}

function makeParentDirectoryPath(path) {
	path = path.substring(0,path.lastIndexOf("/"));
	if(path == 0)
		path="/";
	return path;
}

function makeDirectory(path, value) {
	var directory = new Object();
	directory["name"] = path.substring(path.lastIndexOf("/") + 1, path.length);
	directory["path"] = path;
	directory["directory"] = true;
	directory["commitLog"] = value["commitLog"];
	directory["date"] = value["date"];
	directory["dateInt"] = value["dateInt"];
	directory["commitID"] = value["commitID"];
	directory["commiterName"] = value["commiterName"];
	directory["commiterEmail"] = value["commiterEmail"];
	return directory;
}

function getParentDirectory(path, depth) {

	var parentDirectoryList = new Array();

	parentDirectoryList.push("/");

	for (var i = 0; i < depth; i++) {
		parentDirectoryList.push(path.substr(0, IndexOf("/", path, i + 2)));
	}

	return parentDirectoryList;
}


