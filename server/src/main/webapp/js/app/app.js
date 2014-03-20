/* 
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
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
var myApp = angular.module('waarIsWifiApp', ['waarIsWifiServices']);

var FLOAT_REGEXP = /^\-?\d+((\.|\,)\d+)?$/;
myApp.directive('smartFloat', function() {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (FLOAT_REGEXP.test(viewValue)) {
                    ctrl.$setValidity('float', true);
                    return parseFloat(viewValue.replace(',', '.'));
                } else {
                    ctrl.$setValidity('float', false);
                    return undefined;
                }
            });
        }
    };
});

function adminController($scope, adminEndpointsService) {
    $scope.endpointNames = [{name: "P1"},
        {name: "P2"},
        {name: "P3"}];

    $scope.endpoints = [{id: $scope.endpointNames[0]}, {id: $scope.endpointNames[1]}, {id: $scope.endpointNames[2]}];

    adminEndpointsService.get(function(data) {
        data.forEach(function(c) {
            $scope.endpoints.forEach(function(ep) {
                if (ep.id.name === c.name) {
                    ep.x = c.x;
                    ep.y = c.y;
                    ep.mac = c.mac;
                    
                }
            });

        });
    });
    $scope.save = function() {
        $scope.endpoints.forEach(function(ep) {
           ep.name = ep.id.name; 
        });
        adminEndpointsService.update($scope.endpoints);
    };

};

