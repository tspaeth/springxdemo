(function () {
    'use strict';

    angular.module('springbeer').controller('MenuCtrl', ['$scope', '$http', 'RESTBASE', '$translate', 'localStorageService',
        function ($scope, $http, RESTBASE, $translate, localStorageService) {

            $scope.changeLang = function(newLang) {
                console.log('change Lang ',newLang);
                $translate.use(newLang);
            }

            $scope.logout = function() {
                localStorageService.set('authtoken', undefined);
            }
        }]);
})();
