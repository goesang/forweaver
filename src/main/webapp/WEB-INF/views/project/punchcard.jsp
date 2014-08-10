<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<title>${project.name}~${project.description}</title>
<%@ include file="/WEB-INF/includes/src.jsp"%>
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
<script src="/resources/forweaver/js/d3.v3.min.js"></script>
<script src="/resources/forweaver/js/d3-tip.min.js"></script>
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
		<c:forEach var="i" begin="0" end="6">
    		<c:forEach var="j" begin="0" end="23">
    			[${i}, ${j}, ${dayAndHour[i][j]}],
    		</c:forEach>
    	</c:forEach>
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
            var m = (d > 12) ? "pm" : "am";
            return (d%12 == 0) ? 12+m :  d%12+m;
        }),
    yAxis = d3.svg.axis().scale(y).orient("left")
        .ticks(7)
        .tickFormat(function (d, i) {
            return ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'][d];
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
    .attr("r", function (d) {
    	if(d[2]!=0) return 3+d[2]/3.3;
    	else return d[2];
    	}) // 원크기 맞게 조정해 보았음
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