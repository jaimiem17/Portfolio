
// Set dimensions and margins for the chart
var margin = { top: 70, right: 30, bottom: 40, left: 80 };
var width = 700 - margin.left - margin.right;
var height = 500 - margin.top - margin.bottom;

// Set up the x and y scales

  var x = d3.scaleTime()
  .range([0, width]);

  var y = d3.scaleLinear()
  .range([height, 0]);

// Create the SVG element and append it to the chart container

  var svg = d3.select("#plot1_div")
  .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", `translate(${margin.left},${margin.top})`);



// Define the x and y domains


y.domain([0, d3.max(gapIndexes, d => d.Health_and_Survival_rank)]);

// Add the x-axis
  var xScale = d3.scaleLinear()
  .domain([d3.min(gapIndexes, function(d) { return d.Year; }), d3.max(gapIndexes, function(d) { return d.Year; })])
  .range([0, width]);

    var yScale = d3.scaleLinear()
.domain([d3.max(gapIndexes, function(d) { return d.Health_and_Survival_rank; }), d3.min(gapIndexes, function(d) { return d.Health_and_Survival_rank; })])
.range([height, 0]);



//created the axes by calling the data and appending and transform/translating it to its position
svg.append("g")
  .attr("transform", "translate("+ 0 + "," + height+")")
  .call(d3.axisBottom(xScale).tickFormat(d3.format("d")));

// Add the y-axis
svg.append("g")
  .call(d3.axisLeft(yScale))

// Add X and Y axis labels
svg.append("text")
    .attr("transform", "translate("+ (width / 2) +"," + (height + margin.bottom) + ")")
    .style("text-anchor", "middle")
    .text("Year");

svg.append("text")
    .attr("transform", "rotate(-90)")
    .attr("y", 10 - margin.left)
    .attr("x",0 - (height / 2))
    .attr("dy", "1em")
    .style("text-anchor", "middle")
    .text("US Health and Survival Rank");

// Create the line generator
  line = d3.line()
  .x(d => xScale(d.Year))
  .y(d => yScale(d.Health_and_Survival_rank));


// Add the line path to the SVG element
svg.append("path")
  .datum(gapIndexes)
  .attr("fill", "none")
  .attr("stroke", "steelblue")
  .attr("stroke-width", 1)
  .attr("d", line);

