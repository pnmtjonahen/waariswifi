/* 
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

var HexBins = function(width, heigth, MapRows, MapColumns, margin, heatMap) {
    
    this.heatMap = heatMap;
    
    
//The maximum radius the hexagons can have to still fit the screen
    var hexRadius = d3.min([width / ((MapColumns + 0.5) * Math.sqrt(3)),
        heigth / ((MapRows + 1 / 3) * 1.5)]);

//Set the new height and width of the SVG based on the max possible
    width = MapColumns * hexRadius * Math.sqrt(3);
    heigth = MapRows * 1.5 * hexRadius + 0.5 * hexRadius;

//Set the hexagon radius
    var hexbin = d3.hexbin()
            .radius(hexRadius);

//Calculate the center positions of each hexagon	
    var points = [];
    for (var i = 0; i < MapRows; i++) {
        for (var j = 0; j < MapColumns; j++) {
            points.push([hexRadius * j * 1.75, hexRadius * i * 1.5]);
        }//for j
    }//for i
    // datamap contains the number of devices for a hexbin



//Create SVG element
    var svg = d3.select("body").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", heigth + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

///////////////////////////////////////////////////////////////////////////
////////////////////// Draw hexagons and color them ///////////////////////
///////////////////////////////////////////////////////////////////////////

//Function to call when you mouseover a node
    function mover(d) {
        var el = d3.select(this)
                .transition()
                .duration(10)
                .style("fill-opacity", 0.3)
                ;
    }
    ;

//Mouseout function
    function mout(d) {
        var el = d3.select(this)
                .transition()
                .duration(1000)
                .style("fill-opacity", 1)
                ;
    }
    ;
//Start drawing the hexagons
    svg.append("g")
            .selectAll(".hexagon")
            .data(hexbin(points))
            .enter().append("path")
            .attr("class", "hexagon")
            .attr("d", function(d) {
                return "M" + d.x + "," + d.y + hexbin.hexagon();
            })
            .attr("stroke", function(d, i) {
                return "#fff";
            })
            .attr("stroke-width", "1px")
            .on("mouseover", mover)
            .on("mouseout", mout)
            ;

    this.update = function() {
        var node = d3.selectAll("path");
        node.each(function(d, i) {
            single = d3.select(this);
            single.style("fill", function() {
                return heatMap.getColor(i);
            });
        });
    };
};


