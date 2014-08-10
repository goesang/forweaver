<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name}~${project.description}</title>
<style type="text/css">
.axis path,.axis line {
	fill: none;
	stroke: #eee;
	shape-rendering: crispEdges;
}

.axis text {
	font-family: sans-serif;
	font-size: 11px;
}

.loading {
	font-family: sans-serif;
	font-size: 15px;
}

.circle {
	fill: #222;
}

.circle:hover {
	fill: #CC3333;
}

.d3-tip {
	line-height: 1;
	font-weight: bold;
	padding: 12px;
	background: rgba(0, 0, 0, 0.8);
	color: #fff;
	border-radius: 10px;
}

.d3-tip:after {
	box-sizing: border-box;
	display: inline;
	font-size: 10px;
	width: 100%;
	line-height: 0.6;
	color: rgba(0, 0, 0, 0.8);
	content: "\25BC";
	position: absolute;
	text-align: center;
}

.d3-tip.n:after {
	margin: -1px 0 0 0;
	top: 100%;
	left: 0;
}
</style>
<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.6.3/d3-tip.min.js"></script>
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
					<li><a href="/project/${project.name}/chart">통계</a></li>
					<li class="active"><a
						href="/project/${project.name}/punchcard">펀치카드</a></li>
				</ul>
			</div>
			<div class="span4">
				<div class="input-block-level input-prepend">
					<span class="add-on"><i class="fa fa-link"></i></span> <input
						value="http://forweaver.com/${project.name}.git" type="text"
						class="input-block-level">
				</div>
			</div>
			<div id="punchcard"></div>
			<script type="text/javascript">
var w = 940,
    h = 300,
    pad = 20,
    left_pad = 100,
    punchcard_data = [  // day, hour, radius 순이고 d[0], d[1], d[2]로 불러옴
	[0, 0, 1],
	[0, 1, 2],
	[0, 2, 3],
	[0, 3, 4],
	[0, 4, 5],
	[0, 5, 6],
	[0, 6, 7],
	[0, 7, 8],
	[0, 8, 9],
	[0, 9, 10],
	[0, 10, 11],
	[0, 11, 12],
	[0, 12, 13],
	[0, 13, 14],
	[0, 14, 15],
	[0, 15, 16],
	[0, 16, 17],
	[0, 17, 18],
	[0, 18, 19],
	[0, 19, 20],
	[0, 20, 21],
	[0, 21, 22],
	[0, 22, 23],
	[0, 23, 24],

	[1, 0, 10],
	[1, 1, 11],
	[1, 2, 12],
	[1, 3, 13],
	[1, 4, 14],
	[1, 5, 15],
	[1, 6, 16],
	[1, 7, 17],
	[1, 8, 18],
	[1, 9, 19],
	[1, 10, 20],
	[1, 11, 21],
	[1, 12, 22],
	[1, 13, 23],
	[1, 14, 24],
	[1, 15, 1],
	[1, 16, 2],
	[1, 17, 3],
	[1, 18, 4],
	[1, 19, 5],
	[1, 20, 6],
	[1, 21, 7],
	[1, 22, 8],
	[1, 23, 9],

	[2, 0, 11],
	[2, 1, 11],
	[2, 2, 11],
	[2, 3, 11],
	[2, 4, 11],
	[2, 5, 11],
	[2, 6, 11],
	[2, 7, 11],
	[2, 8, 11],
	[2, 9, 11],
	[2, 10, 11],
	[2, 11, 11],
	[2, 12, 11],
	[2, 13, 11],
	[2, 14, 11],
	[2, 15, 11],
	[2, 16, 11],
	[2, 17, 11],
	[2, 18, 11],
	[2, 19, 11],
	[2, 20, 11],
	[2, 21, 11],
	[2, 22, 11],
	[2, 23, 11],

	[3, 0, 11],
	[3, 1, 11],
	[3, 2, 11],
	[3, 3, 11],
	[3, 4, 11],
	[3, 5, 11],
	[3, 6, 11],
	[3, 7, 11],
	[3, 8, 11],
	[3, 9, 11],
	[3, 10, 11],
	[3, 11, 11],
	[3, 12, 11],
	[3, 13, 11],
	[3, 14, 11],
	[3, 15, 11],
	[3, 16, 11],
	[3, 17, 11],
	[3, 18, 11],
	[3, 19, 11],
	[3, 20, 11],
	[3, 21, 11],
	[3, 22, 11],
	[3, 23, 11],

	[4, 0, 11],
	[4, 1, 11],
	[4, 2, 11],
	[4, 3, 11],
	[4, 4, 11],
	[4, 5, 11],
	[4, 6, 11],
	[4, 7, 11],
	[4, 8, 11],
	[4, 9, 11],
	[4, 10, 11],
	[4, 11, 11],
	[4, 12, 11],
	[4, 13, 11],
	[4, 14, 11],
	[4, 15, 11],
	[4, 16, 11],
	[4, 17, 11],
	[4, 18, 11],
	[4, 19, 11],
	[4, 20, 11],
	[4, 21, 11],
	[4, 22, 11],
	[4, 23, 11],

	[5, 0, 11],
	[5, 1, 11],
	[5, 2, 11],
	[5, 3, 11],
	[5, 4, 11],
	[5, 5, 11],
	[5, 6, 11],
	[5, 7, 11],
	[5, 8, 11],
	[5, 9, 11],
	[5, 10, 11],
	[5, 11, 11],
	[5, 12, 11],
	[5, 13, 11],
	[5, 14, 11],
	[5, 15, 11],
	[5, 16, 11],
	[5, 17, 11],
	[5, 18, 11],
	[5, 19, 11],
	[5, 20, 11],
	[5, 21, 11],
	[5, 22, 11],
	[5, 23, 11],

	[6, 0, 11],
	[6, 1, 11],
	[6, 2, 11],
	[6, 3, 11],
	[6, 4, 11],
	[6, 5, 11],
	[6, 6, 11],
	[6, 7, 11],
	[6, 8, 11],
	[6, 9, 11],
	[6, 10, 11],
	[6, 11, 11],
	[6, 12, 11],
	[6, 13, 11],
	[6, 14, 11],
	[6, 15, 11],
	[6, 16, 11],
	[6, 17, 11],
	[6, 18, 11],
	[6, 19, 11],
	[6, 20, 11],
	[6, 21, 11],
	[6, 22, 11],
	[6, 23, 11]
     ];
 
var svg = d3.select("#punchcard")
        .append("svg")
        .attr("width", w)
        .attr("height", h);

var x = d3.scale.linear().domain([0, 23]).range([left_pad, w-pad]),
    y = d3.scale.linear().domain([0, 6]).range([pad, h-pad*2]);

var xAxis = d3.svg.axis().scale(x).orient("bottom")
        .ticks(24)
        .tickFormat(function (d, i) {
            var m = (d > 12) ? "p" : "a";
            return (d%12 == 0) ? 12+m :  d%12+m;
        }),
    yAxis = d3.svg.axis().scale(y).orient("left")
        .ticks(7)
        .tickFormat(function (d, i) {
            return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d];
        });

var tooltip = d3.tip()
                .attr("class", "d3-tip")
                .offset([-10, 0])
                .html(function(d){
                    return "<span style='color:#ff3333'>" + d[2] + "</span><strong> Commits</strong>";
        });

svg.append("g")
    .attr("class", "axis")
    .attr("transform", "translate(0, "+(h-pad)+")")
    .call(xAxis);
 
svg.append("g")
    .attr("class", "axis")
    .attr("transform", "translate("+(left_pad-pad)+", 0)")
    .call(yAxis);

// Loading 대비. 없애도 무방함
svg.append("text")
    .attr("class", "loading")
    .text("Loading ...")
    .attr("x", function () { return w/2; })
    .attr("y", function () { return h/2-5; });
 
// d3-tip.js로 따로 불러왔기 때문에 툴팁 쓰기 전에 이렇게 불러주는 것.
svg.call(tooltip);

svg.selectAll("circle")
    .data(punchcard_data)
    .enter()
    .append("circle")
    .attr("class", "circle")
    .attr("cx", function (d) { return x(d[1]); })
    .attr("cy", function (d) { return y(d[0]); })
    .attr("r", function (d) { return 3+d[2]/3.3; }) // 원크기 맞게 조정해 보았음
    .on("mouseover", tooltip.show)
    .on("mouseout", tooltip.hide);

// 로딩끝나면 로딩메세지 삭제
svg.selectAll(".loading").remove();

</script>
		</div>
		<!-- .span9 -->

		<!-- .row-fluid -->
		<%@ include file="/WEB-INF/common/footer.jsp"%>
	</div>
</body>
</html>