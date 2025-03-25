function make(name, attrs) {
  var element = document.createElementNS("http://www.w3.org/2000/svg", name);
  if (attrs === undefined) attrs = {};
  for (var key in attrs) {
    element.setAttributeNS(null, key, attrs[key]);
  }
  return element;
}

//function to plot data by using attributes
function plotAll(svg, data, element, attributeGetters) {
  var obj;
  for (var i = 0; i < data.length; ++i) {
    obj = {};
    for (var key in attributeGetters) {
      obj[key] = attributeGetters[key](data[i], i);
    }
    svg.appendChild(make(element, obj));
  }
}


var chart = make("svg", { width: 500, height: 500, "class": "my-chart" });
document.getElementById("scatter").appendChild(chart);

//returns attributes by getting data from scores
plotAll(chart, scores, "circle", { 
  cx: function(s) { return (s["SATM"] - 200) / (800 - 200) * 450 },
  cy: function(s) { return 500 - (s["SATV"] - 200) / (800 - 200) * 450; },
  r: function(s) { return s.ACT/8; },
  fill: function(row) {
    if (row.GPA <= 1.0) {
      return "yellow";
    } else if (row.GPA > 1.0 && row.GPA <= 2.0) {
      return "red";
    } else if (row.GPA > 2.0 && row.GPA <= 3.0) {
      return "blue";
    } else {
      return "green";
    }
  }
});



