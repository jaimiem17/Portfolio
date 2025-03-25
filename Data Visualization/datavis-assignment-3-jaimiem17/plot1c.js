
// Set dimensions and margins for the chart

  margin = { top: 70, right: 30, bottom: 40, left: 80 };
 width = 700 - margin.left - margin.right;
 height = 500 - margin.top - margin.bottom;

// Set up the x and y scales


// Create the SVG element and append it to the chart container

 svg = d3.select("#plot3_div")
  .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", `translate(${margin.left},${margin.top})`);




// Define the x and y domains

 xScale = d3.scaleBand()
    .domain([2007, 2008, 2012, 2013, 2014, 2021, 2022])
   .range([0, width])
   .padding([0.2]);

   yScale = d3.scaleLinear()
.domain([d3.min(gapIndexes, function(d) { return d.Political_Empowerment_index; }), d3.max(gapIndexes, function(d) { return d.Political_Empowerment_index; })])
.range([height, 0]);

//created the axes by calling the data and appending and transform/translating it to its position
// Add the x-axis
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
    .text("The increase of the Political Empowerment Index");



// // Add bars for Political_Empowerment_index
svg.selectAll(".bar")
.data(gapIndexes)
.enter().append("rect")
.attr("class", "bar")
.attr("x", d => xScale(d.Year))
.attr("y", d => yScale(d.Political_Empowerment_index))
.attr("width", xScale.bandwidth())
.attr("height", d => height - yScale(d.Political_Empowerment_index))
.style("fill", "blue")


