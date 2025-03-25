
//created an svg with the width, height, and spacing of where the axes and points will lie
var width = 500;
var height = 500;
var spacing = 120;
var svg = d3.select("body").append("svg")
  .attr("width", 500)
  .attr("height", 500)
  .append("g")
  .attr("transform", "translate(" + spacing / 2 + "," + spacing / 2 + ")");


//created the x axis by scaling the data to fit the width of the svg
var xScale = d3.scaleLinear()
  .domain([d3.min(scores, function(d) { return d.SATM; }), d3.max(scores, function(d) { return d.SATM; })])
  .range([0, width - spacing]);

//created the x axis by scaling the data to fit the width of the svg
var yScale = d3.scaleLinear()
  .domain([d3.min(scores, function(d) { return d.SATV; }), d3.max(scores, function(d) { return d.SATV; })])
  .range([height - spacing, 0]);

//created the axes by calling the data and appending and transform/translating it to its position
var xAxis = d3.axisBottom(xScale);
var yAxis = d3.axisLeft(yScale);

svg.append("g").attr("transform", "translate(0," + (height - spacing) + ")").call(xAxis);
svg.append("g").call(yAxis);

//this created the scale of the sizes of points by using the scaleSqrt function to scale the ACT data to have a size radius ranging from 2-12
var pointScale = d3.scaleSqrt()
  .domain([d3.min(scores, function(d) { return d.ACT; }), d3.max(scores, function(d) { return d.ACT; })])
  .range([2, 12]);

//plotting the points using the scales created above
var points = svg.append("g")
  .selectAll("circle")
  .data(scores)
  .enter()
  .append("circle")
  .join("circle")
  .attr("cx", function(d) { return xScale(d.SATM); })
  .attr("cy", function(d) { return yScale(d.SATV); })
  .attr("r", function(d) { return pointScale(d.ACT) })


//these three functiins are used to create the color scales for each points. Each point is assigned a color based on the GPA data and there are 3 seperate functions for 3 seperate colormaps
var cScale = d3.scaleOrdinal()
  .domain([d3.min(scores, function(d) { return d.GPA; }), d3.max(scores, function(d) { return d.GPA; })])
  .range(["#d7191c", "#2c7bb6"])

var cScale2 = d3.scaleOrdinal()
  .domain([d3.min(scores, function(d) { return d.GPA; }), d3.max(scores, function(d) { return d.GPA; })])
  .range(["#d7191c", "#ffffbf", "#2c7bb6"])

var cScale3 = d3.scaleOrdinal()
  .domain([d3.min(scores, function(d) { return d.GPA; }), d3.max(scores, function(d) { return d.GPA; })])
  .range(["#d7191c", "#fdae61", "#ffffbf", "#abd9e9", "#2c7bb6"])


//these functions are called when the button is clicked. Each function is assigned to a specific colormap where it will call its correct scale.
function cMap1() {
  d3.selectAll("circle")
    .transition()
    .duration(1200)
    .style("fill", d => cScale(d));
}

function cMap2() {
  d3.selectAll("circle")
    .transition()
    .duration(1200)
    .style("fill", d => cScale2(d));
}

function cMap3() {
  d3.selectAll("circle")
    .transition()
    .duration(1200)
    .style("fill", d => cScale3(d));
}



