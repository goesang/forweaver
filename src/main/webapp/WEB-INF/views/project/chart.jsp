<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/raphaelSrc.jsp" %>
<%@ include file="/WEB-INF/includes/src.jsp"%>
<script type="text/javascript">
					window.onload = function() {
						var paper = Raphael(200, 300, 1000, 800);
						var pie1, pie2, pie3, pie4;
						var array1 = [], array2=[], array3=[], array4=[];
						var names = [];
						<c:forEach items="${list}" var="git">
							array1.push(${git.commitCount});
							array2.push(${git.addLine});
							array3.push(${git.deleteLine});
							array4.push(${git.addLine}+${git.deleteLine});
							names.push("${git.username}");
						</c:forEach>
						paper.text(130, 10, "커밋수").attr({ "font-size": 20 });
						pie1 = paper.piechart(
								130, // pie center x coordinate
								120, // pie center y coordinate
								90, // pie radius
								array1, // values
								{
									donut : true,
									legend : names
								}
						).lighter(1.27);
						
						paper.text(600, 10, "추가라인").attr({ "font-size": 20 });
						pie2 = paper.piechart(
								600, // pie center x coordinate
								120, // pie center y coordinate
								90, // pie radius
								array2,
								{
									donut : true,
									legend : names
								}
						).lighter(1.27);
						paper.text(130, 270, "삭제라인").attr({ "font-size": 20 });
						pie3 = paper.piechart(
								130, // pie center x coordinate
								380, // pie center y coordinate
								90, // pie radius
								array3, // values
								{
									donut : true,
									legend : names
								}
						).lighter(1.27);
						paper.text(600, 270, "추가삭제라인").attr({ "font-size": 20 });
						pie4 = paper.piechart(
								700, // pie center x coordinate
								380, // pie center y coordinate
								90, // pie radius
								array4, // values
								{
									donut : true,
									legend : names
								}
						).lighter(1.27);
						// if (donut) series.push(paper.circle(cx, cy, r * donutDiameter).attr({ fill: donutFill, stroke: opts.stroke || donutFill}));
						pie1.hover(
								// mouse over
						    	function () {
						        var that = this.sector;
						        this.sector.stop();
						        this.sector.scale(1.1, 1.1, this.cx, this.cy);

						        pie1.each(function() {
						           if(this.sector.id === that.id) {
						            console.log(pie1)
						            tooltip = paper.text(130, 120, this.sector.value.value).attr({"font-size": 35, "fill":"#000", "cursor" : "default"});
						           }
						        });

						        if (this.label) {
						            this.label[0].stop();
						            this.label[0].attr({ paper: 7.5 });
						            this.label[1].attr({ "font-weight": 800 });
						        }
						    }, 

							// mouse out
						    function () {
						        this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
						        tooltip.remove();

						        if (this.label) {
						            this.label[0].animate({ paper: 5 }, 500, "bounce");
						            this.label[1].attr({ "font-weight": 400 });
						        }
						    });
						pie2.hover(
								// mouse over
						    	function () {
						        var that = this.sector;
						        this.sector.stop();
						        this.sector.scale(1.1, 1.1, this.cx, this.cy);

						        pie2.each(function() {
						           if(this.sector.id === that.id) {
						            console.log(pie2)
						               tooltip = paper.text(700, 120, this.sector.value.value).attr({"font-size": 35, "fill":"#000", "cursor" : "default"});
						           }
						        });

						        if (this.label) {
						            this.label[0].stop();
						            this.label[0].attr({ paper: 7.5 });
						            this.label[1].attr({ "font-weight": 800 });
						        }
						    }, 

							// mouse out
						    function () {
						        this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
						        tooltip.remove();

						        if (this.label) {
						            this.label[0].animate({ paper: 5 }, 500, "bounce");
						            this.label[1].attr({ "font-weight": 400 });
						        }
						    });
						pie3.hover(
								// mouse over
						    	function () {
						        var that = this.sector;
						        this.sector.stop();
						        this.sector.scale(1.1, 1.1, this.cx, this.cy);

						        pie3.each(function() {
						           if(this.sector.id === that.id) {
						            console.log(pie3)
						               tooltip = paper.text(130, 380, this.sector.value.value).attr({"font-size": 35, "fill":"#000", "cursor" : "default"});
						           }
						        });

						        if (this.label) {
						            this.label[0].stop();
						            this.label[0].attr({ paper: 7.5 });
						            this.label[1].attr({ "font-weight": 800 });
						        }
						    }, 

							// mouse out
						    function () {
						        this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
						        tooltip.remove();

						        if (this.label) {
						            this.label[0].animate({ paper: 5 }, 500, "bounce");
						            this.label[1].attr({ "font-weight": 400 });
						        }
						    });
						pie4.hover(
								// mouse over
						    	function () {
						        var that = this.sector;
						        this.sector.stop();
						        this.sector.scale(1.1, 1.1, this.cx, this.cy);

						        pie4.each(function() {
						           if(this.sector.id === that.id) {
						            console.log(pie4)
						               tooltip = paper.text(700, 380, this.sector.value.value).attr({"font-size": 35, "fill":"#000", "cursor" : "default"});
						           }
						        });

						        if (this.label) {
						            this.label[0].stop();
						            this.label[0].attr({ paper: 7.5 });
						            this.label[1].attr({ "font-weight": 800 });
						        }
						    }, 

							// mouse out
						    function () {
						        this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
						        tooltip.remove();

						        if (this.label) {
						            this.label[0].animate({ paper: 5 }, 500, "bounce");
						            this.label[1].attr({ "font-weight": 400 });
						        }
						    });
					}
				</script>
</head>
<body>
	<div class="container">
		<%@ include file="/WEB-INF/common/nav.jsp"%>

		<div class="page-header">
			<h5>
				<big><big><i class="fa fa-bookmark"></i> ${project.name}</big></big>
				<small>${project.description}</small>
			</h5>
		</div>
		<div class="row">
			<div class="span8">
				<ul class="nav nav-tabs">
					<li><a href="/project/${project.name}/">프로젝트 브라우져</a></li>
					<li><a href="/project/${project.name}/commitlog">커밋 내역</a></li>
					<li><a href="/project/${project.name}/community">커뮤니티</a></li>
					<li><a href="/project/${project.name}/weaver">참가자</a></li>
					<li class="active"><a href="/project/${project.name}/chart">통계</a>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-link"></i></span> <input
						value="http://forweaver.com/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
			<div style="height:530px;">
			</div>
			<!--
			<div class="span4">
					<table class="table table-hover">
						<c:forEach items="${list}" var="git">
							<tr>
								<td>${git.username}</td>
								<td>${git.addLine}</td>
								<td>${git.deleteLine }</td>
								<td>${git.commitCount }</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			-->
		</div>
		<!-- .span9 -->

		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>