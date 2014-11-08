/**
 * @description Handles the login-click and stores the token in local storage in case of success
 *
 * @author Thorsten Spaeth <info@conserata.com>
 *
 *
 */
(function () {
    'use strict';

    angular.module('springbeer').controller('LoginCtrl', ['$scope', '$http', 'RESTBASE', '$translate', 'UserService',
        'localStorageService',
        function ($scope, $http, RESTBASE, $translate, UserService, localStorageService) {

            $scope.userdata = {};

            $scope.doLogin = function () {
                UserService.userLogin($scope.userdata.username, $scope.userdata.password).then(function(response) {
                    $scope.message = 'User logged in';
                    localStorageService.set('authtoken', response.data);
                    console.log(response);
                });
            }

        }]);
})();
