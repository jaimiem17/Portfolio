
// Specify the chart’s dimensions.
const width = 928;
const height = 600;
const marginTop = 20;
const marginRight = 30;
const marginBottom = 30;
const marginLeft = 40;

// brush1 and brush2 will store the extents of the brushes,
// if brushes exist respectively on scatterplot 1 and 2.
//
// if either brush does not exist, brush1 and brush2 will be null
let brush1 = null;
let brush2 = null;

let data   = scores;

let scatterplot1 = d3.select("#scatterplot_1");
let scatterplot2 = d3.select("#scatterplot_2");
// Create the horizontal (x) scale, positioning N/A values on the left margin.

function makeScatterplot(plot, xData, yData, xLabel, yLabel) {
  var xScale = d3.scaleLinear()
    .domain([d3.min(scores, d => d[xData]), d3.max(scores, d => d[xData])]).nice()
    .range([marginLeft, width - marginRight])
    .unknown(marginLeft);

  // Create the vertical (y) scale, positioning N/A values on the bottom margin.
  var yScale = d3.scaleLinear()
    .domain([d3.min(scores, d => d[yData]), d3.max(scores, d => d[yData])]).nice()
    .range([height - marginBottom, marginTop])
    .unknown(height - marginBottom);

  // Create the SVG container.
  let svg = plot.append("svg")
    .attr("viewBox", [0, 0, width, height])
    .property("value", []);

  // Append the axes.
  svg.append("g")
    .attr("transform", `translate(0,${height - marginBottom})`)
    .call(d3.axisBottom(xScale))
    .call(g => g.select(".domain").remove())
    .call(g => g.append("text")
      .attr("x", width - marginRight)
      .attr("y", -4)
      .attr("fill", "#000")
      .attr("font-weight", "bold")
      .attr("text-anchor", "end")
      .text(xLabel));

  svg.append("g")
    .attr("transform", `translate(${marginLeft},0)`)
    .call(d3.axisLeft(yScale))
    .call(g => g.select(".domain").remove())
    .call(g => g.select(".tick:last-of-type text").clone()
      .attr("x", 4)
      .attr("text-anchor", "start")
      .attr("font-weight", "bold")
      .text(yLabel));
  
  let brush = d3.brush()
    .extent([[0, 0], [width, height]])
    .on("start brush end", function(event) 
    {
        if (plot === "scatterplot1") {
            brush1 = event.selection;
        } 
        else if (plot === "scatterplot2") {
            brush2 = event.selection;
        }
        onBrush(); 
  });
  // create a group in the svg that will hold this brush. 
  // Note, D3 draws and styles the brush for you, you only need to worry about 
  // updating the selected points. 
  //**IMPORTANT** this brush **must** come before adding our circles!
  //otherwise, we won't be able to click on our dots

  svg.append("g")
    .attr("class", "brush")
    .call(brush);

  // store selected data points
  let selectedData = [];
  
  // Append the dots.
  const dot = svg.append("g")
    .attr("fill", "steelblue")
  .selectAll("circle")
  .data(data)
  .join("circle")
    .attr("transform", d => `translate(${xScale(d[xData])},${yScale(d[yData])})`)
    .attr("r", 5)
    .on("click", function(event, d) {
      // d3.selectAll("circle").style('fill', 'gray').attr('r', 5)
      // d3.select(this).style('fill', 'red').attr('r', 10)
      selectedData.push(d);
      svg.selectAll("circle")
        .attr("fill", d => (selectedData.includes(d) && true) ? "red" : "gray")
        .attr("r", d => (selectedData.includes(d) && true) ? 8 : 5)
    

    // Update the table
 
      d3.select("#table-SATM").text(d.SATM);
      d3.select("#table-SATV").text(d.SATV);
      d3.select("#table-ACT").text(d.ACT);
      d3.select("#table-GPA").text(d.GPA);  



    });





  // Add event listeners for brushing
  //svg.call(d3.brush().on("start brush end", brushFunction));

  // Add event listeners for clicking on circles
  // svg.selectAll("circle")
  //   .on("click", clickFunction);


  // Function to handle click events
  function clickFunction(event, d) {
    // Remove all selections
    selectedData = [];

    // Add the clicked point to the selection
    selectedData.push(d);

    // Emphasize selected point
    updateCircles(selectedData, true);
  }

  function updateCircles(points, emphasize) {
    svg.selectAll("circle")
      .attr("fill", d => (points.includes(d) && emphasize) ? "red" : "gray")
      .attr("r", d => (points.includes(d) && emphasize) ? 8 : 5);
  }

////////


  // // Create the brush behavior
  // svg.call(d3.brush().on("start brush end", ({ selection }) => {
  //   let value = [];
  //   if (selection) {
  //     var [[x0, y0], [x1, y1]] = selection;
  //     value = dot
  //       .style("fill", "gray")
  //       .filter(d => x0 <= xScale(d[xData]) && xScale(d[xData]) < x1
  //         && y0 <= yScale(d[yData]) && yScale(d[yData]) < y1)
  //       .style("fill", "red")
  //       .data();
  //   } else {
  //     dot.style("fill", "steelblue");
  //   }

  //   // Inform downstream cells that the selection has changed.
  //   svg.property("value", value).dispatch("input");
  // }));

  return {
    svg: svg,
    brush: brush,
    xScale: xScale,
    yScale: yScale
  };
}

//////////////////////////////////////////////////////////////////////////////
let attrs = Object.keys(scores[0])

// SATM = attrs[0]
// SATV = attrs[1]
// ACT = attrs[2]
// GPA = attrs[3]

//pass the appropriate parameters
plot1 = makeScatterplot(scatterplot1, attrs[0], attrs[1], "SATM", "SATV");
plot2 = makeScatterplot(scatterplot2, attrs[2], attrs[3], "ACT", "GPA");

//////////////////////////////////////////////////////////////////////////////

// return svg.node();
// details.text(pt.datum().SATM)


function onBrush() {
      // Check if a brush exists in scatterplot 1
      if (brush1 !== null) {
          // Select all circles in scatterplot 1
          plot1.svg.selectAll("circle")
              .attr("fill", d => insideBrush(brush1, d) ? "red" : "gray")
              .attr("r", d => insideBrush(brush1, d) ? 9 : 6);

          // Linking: Emphasize corresponding dots in scatterplot 2
          plot2.svg.selectAll("circle")
              .attr("r", d => corresponding(brush1, d) ? 8 : 5);
      } else {
          // If brush1 is null, reset all circles in scatterplot 1
          plot1.svg.selectAll("circle")
              .attr("fill", "steelblue")
              .attr("r", 5);
      }

      // Check if a brush exists in scatterplot 2
      if (brush2 !== null) {
          // Select all circles in scatterplot 2
          plot2.svg.selectAll("circle")
              .attr("fill", d => insideBrush(brush2, d) ? "red" : "gray")
              .attr("r", d => insideBrush(brush2, d) ? 8 : 5);

          // Linking: Emphasize corresponding dots in scatterplot 1
          plot1.svg.selectAll("circle")
              .attr("r", d => corresponding(brush2, d) ? 8 : 5);
      } else {
          // If brush2 is null, reset all circles in scatterplot 2
          plot2.svg.selectAll("circle")
              .attr("fill", "steelblue")
              .attr("r", 5);
      }
  }

  // Function to check if a point is inside the brush selection
  function insideBrush(brushSelection, point) {
      if (!brushSelection) return false; // If brushSelection is null, return false
      const [[x0, y0], [x1, y1]] = brushSelection;
      const x = plot1.xScale(point[attrs[0]]);
      const y = plot1.yScale(point[attrs[1]]);
      return x >= x0 && x <= x1 && y >= y0 && y <= y1;
  }

  // Function to check if a point corresponds to a selected point in the other scatterplot
  function corresponding(brushSelection, point) {
      if (!brushSelection) return false; // If brushSelection is null, return false
      const [[x0, y0], [x1, y1]] = brushSelection;
      const x = plot2.xScale(point[attrs[2]]);
      const y = plot2.yScale(point[attrs[3]]);
      return x >= x0 && x <= x1 && y >= y0 && y <= y1;
  }

  // // Example: Log the brush selections to the console
  // console.log("Brush 1:", brush1);
  // console.log("Brush 2:", brush2);



//////////////////////////////////////////////////////////////////////////////
//
// d3 brush selection
//
// The "selection" of a brush is the range of values in either of the
// dimensions that an existing brush corresponds to. The brush selection
// is available in the event object (e) passed during an event.
// 
// For example, in .on("brush",updateBrush1), updateBrush1 is implicitly 
// passed the event object, which we can access as a paramter
//
//   e[0][0] is the minimum value in the x axis of the brush
//   e[1][0] is the maximum value in the x axis of the brush
//   e[0][1] is the minimum value in the y axis of the brush
//   e[1][1] is the maximum value in the y axis of the brush
//
// The most important thing to know about the brush selection is that
// it stores values in *PIXEL UNITS*. 


// Functions to handle each of our brushes. 
// They are implicilty passed the event e from our event handler below.
// If there is no brush in the plot (e.g. we clicked empty space to remove it), 
// e.selection returns null. 
function updateBrush1(e) {
  brush1 = e.selection;
  onBrush();
}

function updateBrush2(e) {
  brush2 = e.selection;
  onBrush();
}

//set event handlers for "brush" and "end" for 
// the brush in each plot.  The "end" event just 
// indicates that we have stopped brushing. 
// Hint: "end" will be needed to check if you have clicked "nothing"
// Create the brush behavior
      
    

plot1.brush
  .on("brush", updateBrush1)
  .on("end", updateBrush1);

plot2.brush
  .on("brush", updateBrush2)
  .on("end", updateBrush2);
