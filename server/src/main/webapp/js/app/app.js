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


    $scope.endpoints = [];

    $scope.get = function() {
        adminEndpointsService.get(function(data) {
            $scope.endpoints = data;
        });
    };
    $scope.get();
    
    $scope.save = function() {
        adminEndpointsService.update($scope.endpoints, function() {
                $scope.get();
        });
    };
    $scope.addNew = function() {
        $scope.endpoints.push({mac:"", name:"", x:"", y:""});
    };

};

function macNameResolverController($scope, macNameResolverService) {
    $scope.resolvers = [];
    
    $scope.get = function () {
        macNameResolverService.get(function(data) {
            $scope.resolvers = data;
        });
    };
    $scope.get();
    
    $scope.addNew = function() {
        $scope.resolvers.push({mac:"", name:""});
    };
    $scope.save = function() {
        macNameResolverService.update($scope.resolvers, function() {
                $scope.get();
        }, function(error) {
            Alert("server error "+error);
        });
    };
    
};

