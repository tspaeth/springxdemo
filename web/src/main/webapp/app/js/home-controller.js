(function () {
    'use strict';

    angular.module('springbeer').controller('HomeCtrl', ['$scope', '$http', 'RESTBASE', '$translate',
        function ($scope, $http, RESTBASE, $translate) {

            $scope.gridConfig = {
                enableRowSelection: true,
                enableSelectAll: true,
                enableFiltering: true
            };

            $scope.changeLang = function(newLang) {
                console.log('change Lang ',newLang);
                $translate.use(newLang);
            }

            $scope.gridConfig.columnDefs = [
                {
                    field: 'brand',
                    displayName: 'Brand'
                },
                {
                    field: 'alcVol',
                    displayName: 'Alcohol %'
                }
            ];

            $http.get(RESTBASE.URL + '/beer')
                .success(function (response) {
                    $scope.gridConfig.data = $scope.beers = response._embedded.beers;
                })


        }]);
})();
