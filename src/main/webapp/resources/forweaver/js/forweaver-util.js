var editorMode = false; 

function mongoObjectId () {
	var timestamp = (new Date().getTime() / 1000 | 0).toString(16);
	return timestamp + 'xxxxxxxxxxxxxxxx'.replace(/[x]/g, function() {
		return (Math.random() * 16 | 0).toString(16);
	}).toLowerCase();
} //https://gist.github.com/solenoid/1372386


function containsObject(list,obj ) { // 배열에 객체가 있는지 조사.
	var i;
	for (i = 0; i < list.length; i++) {
		if (list[i] === obj) {
			return true;
		}
	}

	return false;
}


function imgCheck(fileName) {
	if(!/(\.bmp|\.png|\.gif|\.jpg|\.jpeg)$/i.test(fileName)) {    
		return false;   
	}   
	return true; 
}

function deleteReply(postID,rePostID,number){
	if (confirm("정말 댓글을 삭제하시겠습니까?")){
		window.location =	"/community/"+postID+"/"+rePostID+"/"+number+"/delete";
	}else{
		return;
	}
}

function deleteCodeReply(postID,rePostID,number){
	if (confirm("정말 댓글을 삭제하시겠습니까?")){
		window.location =	"/code/"+postID+"/"+rePostID+"/"+number+"/delete";
	}else{
		return;
	}
}

function pushPost(postID){
	if (confirm("정말 추천하시겠습니까?")){
		window.location =	"/community/"+postID+"/push";
	}else{
		return;
	}
}

function deletePost(postID){
	if (confirm("정말 삭제하시겠습니까?")){
		window.location =	"/community/"+postID+"/delete";
	}else{
		return;
	}
}

function deleteCode(codeID){
	if (confirm("정말로 코드를 삭제하시겠습니까?")){
		window.location =	"/code/"+codeID+"/delete";
	}else{
		return;
	}
}

function pushRePost(postID,rePostID){
	if (confirm("정말 추천하시겠습니까?")){
		window.location =	"/community/"+postID+"/"+rePostID+"/push";
	}else{
		return;
	}
}

function deleteRePost(postID,rePostID){
	if (confirm("답글을 삭제하시겠습니까?")){
		window.location =	"/community/"+postID+"/"+rePostID+"/delete";
	}else{
		return;
	}
}

function spacialSignEncoder(str) {
	str = str.split(" ").join("@$@");
	str = str.split("+").join("@#@");
	str = str.split("%").join("@!@");
	str = str.split("&").join("@4@");
	return str;
}

function specialSignDecoder(str) {
	str = str.split("@$@").join(" ");
	str = str.split("@#@").join("+");
	str = str.split("@!@").join("%");
	str = str.split("@4@").join("&");
	return str;
}


function getSearchWord(url){
	if(url.indexOf("/search:")==-1)
		return [];
	url =  decodeURI(url);
	url = url.substring(url.indexOf("search:")+7);
	if(url.indexOf("/")!=-1)
		url = url.substring(0,url.indexOf("/"));
	return url;
}


function getTagList(url){
	if(url.indexOf("/tags:")==-1)
		return [];
	url =  decodeURI(url);
	var tagList = new Array();
	var realURL = true;
	if(url.indexOf("/tags:") == 0)
		realURL = false;
	url = url.substring(url.indexOf("tags:")+5);
	if(realURL && url.indexOf("/")!=-1)
		url = url.substring(0,url.indexOf("/"));
	$.each(url.split(","), function(index, value) {
		value = value.replace('>', '/');
		tagList.push(value);
	});
	return tagList;
}

function getSort(url){
	if(url.indexOf("/sort:")==-1)
		return "age-desc";
	var sort = url.substring(url.indexOf("sort:")+5);

	if(sort.indexOf("/")==-1)
		return sort;
	return sort.substring(0,sort.indexOf("/"));
}


function endsWith(str, suffix) {
	return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

function filePathTransform(path) {
	path = path.split(".").join(",");
	path = path.split("/").join(">");
	return path;
}

function extensionSeach(url){
	if(endsWith(url,"/blame"))
		url = url.substring(0, url.length-6); 
			
	if(endsWith(url,"java") || endsWith(url,"pde"))
		return "java";
	else if(endsWith(url,"css"))
		return "css";
	else if(endsWith(url,"xml" || endsWith(url,"html")))
		return "xml";
	else if(endsWith(url,"php"))
		return "php";
	else if(endsWith(url,"pl"))
		return "perl";
	else if(endsWith(url,"js"))
		return "jscript";
	else if(endsWith(url,"diff"))
		return "diff";
	else if(endsWith(url,"c") || endsWith(url,"h") || endsWith(url,"cpp") || endsWith(url,"ino"))
		return "cpp";
	else if(endsWith(url,"cs"))
		return "csharp";
	else if(endsWith(url,"sql"))
		return "sql";
	else if(endsWith(url,"py"))
		return "python";
	else
		return "text";
}


function movePage(tagArrayString,searchWord){
	if(editorMode)
		return;
	var tagArray = eval(tagArrayString);
	var url = document.location.href;

	if(url.indexOf("/tags:") != -1)
		url = url.substring(0,url.indexOf("/tags:"))+'/';
	else if(url.indexOf("/weaver") != -1)
		url = url.substring(0,url.indexOf("/weaver")+7)+'/';
	else if(url.indexOf("/project") != -1)
		url = url.substring(0,url.indexOf("/project")+8)+'/';
	else if(url.indexOf("/code") != -1)
		url = url.substring(0,url.indexOf("/code")+5)+'/';
	else if(url.indexOf("/lecture") != -1)
		url = url.substring(0,url.indexOf("/lecture")+8)+'/';
	else	
		url = "/community/";
	if(tagArray.length == 0){
		window.location = url;
		return;
	}
	url = url + "tags:"+	tagInputValueConverter(tagArray);
	url = url.substring(0,url.length-1);

	if(searchWord.length != 0)
		url = url +"/search:"+ searchWord;
	window.location = url;
}

function moveUserPage(userName,tagArrayString,searchWord){
	if(editorMode)
		return;
	var tagArray = eval(tagArrayString);
	var url = document.location.href;

	if(tagArray.length == 0){
		window.location = url;
		return;
	}
	url = "/"+userName +"/"+ "tags:"+	tagInputValueConverter(tagArray);
	url = url.substring(0,url.length-1);

	if(searchWord.length != 0)
		url = url +"/search:"+ searchWord;

	window.location = url;
}


function tagInputValueConverter(tagArray){
	var simpleArray="";
	$.each(tagArray, function(index, value) {
		simpleArray = simpleArray+ value.replace('/', '>')+",";
	});
	return simpleArray;
}

function textAreaResize(obj) {
	obj.style.height = "1px";
	obj.style.height = (20+obj.scrollHeight)+"px";
}

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {
			$('#preview').attr('src', e.target.result);
		}

		reader.readAsDataURL(input.files[0]);
	}

}
function openWindow(url, width, height){
	window.open(url,'','width='+width+',height='+height+',top='+((screen.height-height)/2)+',left='+((screen.width-width)/2)+',location =no,scrollbars=no, status=no;');
}


