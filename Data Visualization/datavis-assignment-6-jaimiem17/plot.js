margin = ({ top: 20, right: 20, bottom: 20, left: 20 })
// HINT: Consider starting with a smaller, hand-written version of a tree, instead
// of the one in flare.js.


let data = flare;

//////////////////////////////////////////////////////////////////////////////

//function to calculate the cummulative size of all leaf nodes for each internal node
function setTreeSize(tree) {
  //recursively calculate the size of each child node
  if (tree.children !== undefined) {
    let size = 0;
    for (let i = 0; i < tree.children.length; ++i) {
      size += setTreeSize(tree.children[i]);
    }
    tree.size = size;
  }
  //leaf nodes already have size attribute in their definition
  if (tree.children === undefined) {
    // do nothing, tree.size is already defined for leaves
  }
  return tree.size;
};


//function to calculate & set the number of leaf nodes that exist within the sub-tree for each internal node
function setTreeCount(tree) {
  //recursively calculate the number of leaves
  if (tree.children !== undefined) {
    let count = 0;
    for (let i = 0; i < tree.children.length; ++i) {
      count += setTreeCount(tree.children[i]);
    }
    tree.count = count;
  }
  //if it's a leaf, the count is 1 (itself)
  if (tree.children === undefined) {
    tree.count = 1;
  }
  return tree.count;
}

//function to calculate & set the depth of each node in the tree
function setTreeDepth(tree, depth) {
  //recursively calculate the depth of each node in the tree

  var subMax;

  tree.depth = depth;

  if (tree.children !== undefined) {

    subMax = 0;

    for (let i = 0; i < tree.children.length; ++i)
      subMax = Math.max(subMax, setTreeDepth(tree.children[i], depth + 1));

    return Math.max(subMax, depth);

  }
  else {

    return depth;

  }
};




//set the attributes for each node
setTreeSize(data);
setTreeCount(data);
let maxDepth = setTreeDepth(data, 0);




//////////////////////////////////////////////////////////////////////////////
// THIS IS THE MAIN CODE FOR THE TREEMAPPING TECHNIQUE

// function to calculate the rectangle coordinates for each node in the tree
//Params: rect -> contains the rectangle coordinates of the current root node 
//tree -> the tree we are operating on
// attrFun -> a getter function that specifies which attribute we are encoding with size

//* if width greater than height cut vertically
//* make sure to redefine width and scales after adding cuts
function setRectangles(rect, tree, attrFun, button) {
  let i;
  tree.rect = rect;
  let vertCut = false;
  let horzCut = false;
  console.log(" ");


  //if the tree has children, calculate their rectangle sizes and recurse 
  if (tree.children !== undefined) {
    // Sort children nodes in descending order based on size
    tree.children.sort((a, b) => b.size - a.size);

    //create an array to store the cummulative values that size will encode
    let cumulativeSizes = [0];

    // calculate the cummulative values of the specified attribute 
    // for a set of nodes with sizes: 3, 3, and 8, the final array will be [0, 3, 6, 14]
    // and the pairwise differences give the individual node values
    for (i = 0; i < tree.children.length; ++i) {
      cumulativeSizes.push(cumulativeSizes[i] + attrFun(tree.children[i]));
    }


  
    //set the height and width of the current rectangle
    let height = rect.y2 - rect.y1
    let width = rect.x2 - rect.x1;




    // initialize a linear scale to range from 0 to the cummulative value of all nodes
    let scale = d3.scaleLinear()
      .domain([0, cumulativeSizes[cumulativeSizes.length - 1]]);



    let border = 5;

    // TODO:: WRITE THIS PART.
    // hint: set the range of the "scale" variable above appropriately,
    // depending on the shape of the current rectangle.




    var x1 = 0;
    var x2 = 0;
    var y1 = 0;
    var y2 = 0;



    //depending on which button is clicked compare width/height or do every other depth
    if (button == "sizebest" || button == "countbest" || button == "sizeadapt" || button == "countadapt") {
      if (width > height)
        vertCut = true
      else
        horzCut = true
    }
    else {
      if (tree.depth % 2 == 0)
        vertCut = true;
      else
        horzCut = true;
    }




    let curX = 0;
    let curY = 0;


    //loop start
    for (i = 0; i < tree.children.length; ++i) {


      //if adaptive then start process after first iteration because first will be the same as others
      if ((button == "sizeadapt" || button == "countadapt") && i != 0) {



          if (vertCut) {
            
             width = width - (tree.children[i - 1].rect.x2 - tree.children[i - 1].rect.x1);

            scale.range([rect.x1 + 5, rect.x2 - 5]);

            x1 = scale(cumulativeSizes[i]);
            x2 = scale(cumulativeSizes[i + 1]);
            y1 = rect.y1 + border; //check
            y2 = rect.y2 - border;

            console.log("X2: "+x2);

            
          }

          else {
            
            height = height - (tree.children[i - 1].rect.y2 - tree.children[i - 1].rect.y1);
            scale.range([rect.y1 + 5, rect.y2 - 5]);

            x1 = rect.x1 + border; //check
            x2 = rect.x2 - border;
            y1 = scale(cumulativeSizes[i]);
            y2 = scale(cumulativeSizes[i + 1]);

          
          }

          console.log(tree.name + " width after: " + width)
          console.log(tree.name + " height after: " + height)

        let recumulativeSizes = [0];

        let k = 0;

        //recalculated cumulative sizes cutting out previous node
        for (k = i; k < tree.children.length; ++k) {
          if(recumulativeSizes[k-1] !== undefined){
            recumulativeSizes.push(recumulativeSizes[k-1] + attrFun(tree.children[k]));
          }
          else{
            recumulativeSizes.push(recumulativeSizes[0] + attrFun(tree.children[k]));
          }
        
        }


        //recalculate scale domain
        scale = d3.scaleLinear()
        .domain([0, recumulativeSizes[recumulativeSizes.length - 1]]);
        


          if (width > height) { //vertical cut in adaptive mode
            
            vertCut = true;
            horzCut = false;

            
            scale.range([rect.x1 + 5, rect.x2 - 5]);
          
            
            x1 = scale(recumulativeSizes[0]);
            x2 = scale(recumulativeSizes[1]);
            y1 = curY; //check
            y2 = rect.y2 - border;

            

          }

          else {
            scale.range([rect.y1 + 5, rect.y2 - 5]);
            
            horzCut = true;
            vertCut = false;

            
            
            x1 = curX; //check
            x2 = rect.x2 - border;
            y1 = curY;
            y2 = scale(recumulativeSizes[1]);

            
            curY = y2;
          }
        }
      
        
      else{ //not adaptive or first node

        if (vertCut) {  //cut vertical
  
          scale.range([rect.x1 + 5, rect.x2 - 5]);
  
  
          x1 = scale(cumulativeSizes[i]);
          x2 = scale(cumulativeSizes[i + 1]);
          y1 = rect.y1 + border; //check
          y2 = rect.y2 - border;

          curX = x2;
        }
  
        else {  //cut horizontal
          scale.range([rect.y1 + 5, rect.y2 - 5]);
  
          x1 = rect.x1 + border; //check
          x2 = rect.x2 - border;
          y1 = scale(cumulativeSizes[i]);
          y2 = scale(cumulativeSizes[i + 1]);
       
          curY = y2;
        }
      }


      //replace the nulls with the correct values



      let newRect = { x1: x1, x2: x2, y1: y1, y2: y2 };
      setRectangles(newRect, tree.children[i], attrFun, button);
    }
  }
}


let width = window.innerWidth - 100;
let height = window.innerHeight - 100;


// call "setRectangles" to draw the treemap, using the size variable
// The rectangle for the root node is the whole  window (minus a margin)


setRectangles(
  { x1: 0, x2: width, y1: 0, y2: height }, data,
  function(t) {
    //console.log(t.name +": " + t.size)
    return t.size;
  }
);

// create a linear list of all nodes in the trees that we can bind to SVG rects
function makeTreeNodeList(tree, list) {
  list.push(tree);
  if (tree.children !== undefined) {

    for (let i = 0; i < tree.children.length; ++i) {
      makeTreeNodeList(tree.children[i], list);
    }
  }
}



let treeNodeList = [];
makeTreeNodeList(data, treeNodeList);



//set up a group for each node in the tree
let gs = d3.select("#svg")
  .attr("width", width)
  .attr("height", height)
  .selectAll("g")
  .data(treeNodeList)
  .enter()
  .append("g");


function setAttrs(sel) {
  // TODO:: WRITE THIS PART.
  sel.attr("width", function(treeNode) {
    return treeNode.rect.x2 - treeNode.rect.x1;
  }).attr("height", function(treeNode) {
    return treeNode.rect.y2 - treeNode.rect.y1;;
  }).attr("x", function(treeNode) {
    return treeNode.rect.x1;
  }).attr("y", function(treeNode) {
    return treeNode.rect.y1;
  }).attr("fill", function(treeNode) {
    return d3.scaleSequential([0, maxDepth], d3.interpolatePuRd)(treeNode.depth);
  }).attr("stroke", function(treeNode) {
    return "black";
  });
}

gs.append("rect").call(setAttrs)
  .append("title") //this will show the node name on hover
  .html(function(treeNode) {
    return treeNode.name;
  });

//when the size button is clicked, draw the treemap using the size attribute
d3.select("#size").on("click", function() {
  setRectangles(
    { x1: 0, x2: width, y1: 0, y2: height }, data,
    function(t) { return t.size; }, "size"
  );
  d3.selectAll("rect").transition().duration(1000).call(setAttrs);
});

//when the size button is clicked, draw the treemap using the leaf count
d3.select("#count").on("click", function() {
  setRectangles(
    { x1: 0, x2: width, y1: 0, y2: height }, data,
    function(t) { return t.count; }, "count"
  );
  d3.selectAll("rect").transition().duration(1000).call(setAttrs);
});

d3.select("#sizebest").on("click", function() {
  setRectangles(
    { x1: 0, x2: width, y1: 0, y2: height }, data,
    function(t) { return t.size; }, "sizebest"
  );
  d3.selectAll("rect").transition().duration(1000).call(setAttrs);
});

d3.select("#countbest").on("click", function() {
  setRectangles(
    { x1: 0, x2: width, y1: 0, y2: height }, data,
    function(t) { return t.count; }, "countbest"
  );
  d3.selectAll("rect").transition().duration(1000).call(setAttrs);
});

d3.select("#sizeadapt").on("click", function() {
  setRectangles(
    { x1: 0, x2: width, y1: 0, y2: height }, data,
    function(t) { return t.size; }, "sizeadapt"
  );
  d3.selectAll("rect").transition().duration(1000).call(setAttrs);
});

d3.select("#countadapt").on("click", function() {
  setRectangles(
    { x1: 0, x2: width, y1: 0, y2: height }, data,
    function(t) { return t.count; }, "countadapt"
  );
  d3.selectAll("rect").transition().duration(1000).call(setAttrs);
});
