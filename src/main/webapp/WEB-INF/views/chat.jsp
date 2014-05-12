<html>
<head>
    <title>SockJS Test</title>
    
<%@ include file="/WEB-INF/includes/src.jsp"%>
</head>
<body>
<script type="text/javascript">
	

		$(document).ready(function() {
			prettyFileUpload();
		});
</script>
<form method="post" action="/data/upload" enctype="multipart/form-data">
<span class="prettyFile"> 
				<input style="display: none;" type="file"
					name="file" multiple="multiple">
					<div class="input-append">
						<input class="input-large" type="text"/> 
						<a href="#" class="btn">업로드</a>
					</div>
				</span>
				<input type="submit" value="전송"> 
</form> 
</body>
</html>