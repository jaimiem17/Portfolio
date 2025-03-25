let width = 800
let height = 500
let margin = { left: 50, right: 50, top: 40, bottom: 20 }

//create variable for the data
let data = iris

//Create the svg
let svg = d3.select("#pcs").append("svg")
  .attr("height", height)
  .attr("width", width)

//get the attributes in the dataset
let attrs = Object.keys(data[0]).slice(0, 4)


//create an xScale that maps each attribute to a spatial position 
let xScale = d3.scalePoint(attrs, [margin.left, width - margin.left - margin.right])


//initialize variables to store info for the axes
let axes = {}
let yScales = {}
let brushes = {}
let brushBounds = {}

// function createYscales(ydata, yName) {

//   var yscale = d3.scaleLinear()
//     .domain([d3.min(data, d => d[ydata]), d3.max(data, d => d[ydata])])
//     .range([height, 0]);
//   yScales[yName] = yscale;
// }

// createYscales(attrs[0], 'sepalLength_scale');
// createYscales(attrs[1], 'sepalWidth_scale');
// createYscales(attrs[2], 'petalLength_scale');
// createYscales(attrs[3], 'petalWidth_scale');


//creates every axis
var sepalLength = d3.scaleLinear()
  .domain([d3.min(data, d => d['sepalLength']), d3.max(data, d => d['sepalLength'])])
  .range([height - margin.top - margin.bottom, margin.top]);
yScales["sepalLength_scale"] = sepalLength;
axes["sepalLength"] = d3.axisLeft(sepalLength);

var sepalWidth = d3.scaleLinear()
  .domain([d3.min(data, d => d['sepalWidth']), d3.max(data, d => d['sepalWidth'])])
  .range([height - margin.top - margin.bottom, margin.top]);
yScales["sepalWidth_scale"] = sepalWidth;
axes["sepalWidth"] = d3.axisLeft(sepalWidth);

var petalLength = d3.scaleLinear()
  .domain([d3.min(data, d => d['petalLength']), d3.max(data, d => d['petalLength'])])
  .range([height - margin.top - margin.bottom, margin.top]);
yScales["petalLength_scale"] = petalLength;
axes["petalLength"] = d3.axisLeft(petalLength);

var petalWidth = d3.scaleLinear()
  .domain([d3.min(data, d => d['petalWidth']), d3.max(data, d => d['petalWidth'])])
  .range([height - margin.top - margin.bottom, margin.top]);
yScales["petalWidth_scale"] = petalWidth;
axes["petalWidth"] = d3.axisLeft(petalWidth);



//TODO:  Create Color scale for "species" attribute
let colorScale = d3.scaleOrdinal()
  .domain(data.map(d => d.species))
  .range(d3.schemeSet2);


function isSelected(d) {
  //TODO:: update to determine if the line is in the brushes

  // return attrs.every(a => {

  // for (const a in attrs) {
  return attrs.map(a => {
    var y = yScales[a](d[a]);
    const bounds = brushBounds[a]; //i dont get why this is null
    console.log("a: " + a);
    console.log("Brush bounds: " + brushBounds[0]);
    console.log("bounds: " + bounds);
    console.log("y: " + y);
    if (bounds) {
      if (y >= bounds[0] && y <= bounds[1]) {
        console.log("TRUE");
        return true;
      }
    }
  })

}

function onBrush() {
  //TODO:: color lines that are in ALL of the brushes
  paths.attr("opacity", d => isSelected(d) ? 0.75 : 0.1);
}


//swaps axis with rightside axis when clicked (or left one when rightmost is clicked)
function swapAxes(clicked) {
  axisIndex = attrs.indexOf(clicked)

  //swaps
  var toSwap = 2;
  if (axisIndex != 3) {
    toSwap = axisIndex + 1;
  }
  var temp = attrs[axisIndex];
  attrs[axisIndex] = attrs[toSwap];
  attrs[toSwap] = temp;

  var temp2 = axes[axisIndex];
  axes[axisIndex] = axes[toSwap];
  axes[toSwap] = temp2;

  //re sets up axes in order after swapping
  xScale = d3.scalePoint(attrs, [margin.left, width - margin.left - margin.right])

  //recreates and draws axes
  axisGroups
    .data(attrs)
    .attr("transform", d => "translate(" + xScale(d) + ",0)")
    .each(function(d) {
      d3.select(this).call(axes[d]);
    })


  //redraws labels
  labelGroups
    .attr("transform", d => "translate(" + xScale(d) + ",0)");

  //redraws lines
  paths.attr("d", d => {
    return d3.line()(attrs.map(attr => [xScale(attr), yScales[attr](d[attr])]));
  })

}


// Set up the objects for each axis 
attrs.map(function(attr) {
  //set the initial range to null 
  brushBounds[attr] = null;

  //initialize a brush for the y axis & set the extent that the brush is allowed to be drawn
  let brush = d3.brushY()
    .extent([[-15, margin.top], [15, height - margin.top - margin.bottom]])
    .on("brush end", function(e) {
      brushBounds[attr] = e.selection; //set the range to the current brush selection 
      onBrush(); //call the brush function
    });

  //store the brush for the axis
  brushes[attr] = brush;

  //create and store a scale for the current axis
  let scale = d3.scaleLinear(d3.extent(iris, (d) => d[attr]), [height - margin.top - margin.bottom, margin.top])
  yScales[attr] = scale;

  //create and store a d3 axis for the current axis
  axes[attr] = d3.axisLeft(scale);
})



//TODO:: Create a selection of path elements and bind the "data" to them. 
// Use this selection to draw one line across all axes for each item in the data

let paths = svg.selectAll(".line")
  .data(data)
  .enter()
  .append("path")
  .attr("class", "dataline") // Set class to "dataline"
  .attr("d", d => {
    return d3.line()(attrs.map(attr => [xScale(attr), yScales[attr](d[attr])]));
  })
  .style("fill", "none")
  .style("stroke", d => colorScale(d.species)) // Color by species attribute
  .style("stroke-opacity", 0.75); // Set opacity to 0.75



//TODO:: Create a selection of group elements and bind "attrs" to them.
// Use this group to add the brushes to the axes. 

let brushGroups = svg.selectAll(".brush")
  .data(attrs)
  .enter()
  .append("g")
  .attr("class", "brush")
  .attr("transform", d => "translate(" + xScale(d) + ",0)")
  .each(function(d) {
    d3.select(this)
      .call(brushes[d]);
  })



//TODO:: Create a selection of group elements and bind "attrs" to them.
// Use this group to draw your axes.

let axisGroups = svg.selectAll(".axis")
  .data(attrs)
  .enter()
  .append("g")
  .attr("class", "axis")
  .attr("transform", d => "translate(" + xScale(d) + ",0)")
  .each(function(d) {
    //console.log(d)
    d3.select(this).call(axes[d]);

  })


//TODO:: Create a selection of group elements and bind "attrs" to them.
// Use this group to add the labels to the axes. You will need to add 
// an event handler to handle click events (i.e. .on("click",...)) 

let labelGroups = svg.selectAll(".label")
  .data(attrs)
  .enter()
  .append("g")
  .attr("transform", d => "translate(" + xScale(d) + ",0)")
  .each(function(d) {
    //console.log(d)
    d3.select(this)
      .call(g => g.append("text")
        .attr("class", "label")
        .attr("x", 0)
        .attr("y", margin.top - 15)
        .attr("fill", "#000")
        .attr("text-anchor", "middle")
        .text(d)
        .on("click", function(f) {
          console.log('d ' + (d));
          swapAxes(d);
        }));
  })


//note: finished on swapping axes by swapping attrs edit dat
