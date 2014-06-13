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
function HeatMap(mapRows, mapColumns) {

    this.color = [];
    this.datamap = [];
    this.nodes = [];
    this.mapRows = mapRows;
    this.mapColumns = mapColumns;

    function componentToHex(c) {
        var hex = c.toString(16);
        return hex.length === 1 ? "0" + hex : hex;
    }
    function rgbToHex(r, g, b) {
        return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
    }
    var max = 200;
    var red = max, green = max, blue = max;
    for (var i = 0; i < max; i += 25) {
        blue = max - i;
        this.color.push(rgbToHex(red, green, blue));
    }
    for (var i = 0; i < max; i += 25) {
        green = max - i;
        this.color.push(rgbToHex(red, green, blue));
    }
    for (var i = 0; i < max; i += 25) {
        red = max - i;
        this.color.push(rgbToHex(red, green, blue));
    }

    for (var i = 0; i < this.mapRows; i++) {
        for (var j = 0; j < this.mapColumns; j++) {
            this.datamap.push(0);
        }
    }

    this.decrement = function(index) {
        this.datamap[index]--;

        if (this.datamap[index] < 0) {
            this.datamap[index] = 0;
        }
    };
    this.increment = function(index) {
        this.datamap[index]++;

        if (this.datamap[index] > this.color.length) {
            this.datamap[index] = this.color.length;
        }
    };

    this.findNode = function(id) {
        for (var i in this.nodes) {
            if (this.nodes[i]["id"] === id)
                return this.nodes[i];
        }
    };

    this.addNode = function(n, index) {
        this.nodes.push({"id": n.device, "index": index});
    };

    this.getColor = function(index) {
        return this.color[this.datamap[index]];
    };

    this.update = function(obj) {

        var row = Math.round(obj.x) - 1;
        var index = row * this.mapRows + Math.round(obj.y);

        var current = this.findNode(obj.device);
        if (obj.expired === true && current) {
            this.decrement(current.index);
        } else {
            if (current) {
                this.decrement(current.index);
                current.index = index;
                this.increment(current.index);
            } else {
                this.addNode(obj, index);
                this.increment(index);
            }
        }

    };

}