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
var WifiEndpoint = function (processOnmessage) {

    var connect = function() {
        var socket;
        var host = "ws://" + location.host + "/server/wifiendpoint";

        try {
            var socket = new WebSocket(host);

            message('Socket Status: ' + socket.readyState);

            socket.onopen = function() {
                message('Socket Status: ' + socket.readyState + ' (open) ' + host);
            };

            socket.onmessage = function (msg) {
                message('Received: ' + msg.data);

                processOnmessage(msg);
            };

            socket.onclose = function() {
                message('Socket Status: ' + socket.readyState + ' (Closed)');
            };

        } catch (exception) {
            message('Error' + exception);
        }

        function message(msg) {
            console.log(msg);
        }
    };//End connect    

    this.setupConnection = function () {
        if (!("WebSocket" in window)) {
            $('<p>Oh no, you need a browser that supports WebSockets. How about <a href="http://www.google.com/chrome">Google Chrome</a>?</p>').appendTo('body');
        } else {
            //The user has WebSockets
            connect();
        }
    };
};


