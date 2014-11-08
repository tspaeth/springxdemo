/**
 * @description Provides the actions for the detail view of a beer
 * It provides
 * - CREATE handling
 * - VIEW handling
 * - DELETE handling
 *
 * As it is a quick-and-dirty-implementatino, currently the Controller does the $http - calls itself
 * It is a better idea to shift the actual http-calls to a delegate service
 *
 * @author Thorsten Spaeth <info@conserata.com>
 *
 *
 */
(function () {
    'use strict';

    angular.module('springbeer').controller('DetailCtrl', ['$scope', '$http', 'RESTBASE', '$state', '$q',
        function ($scope, $http, RESTBASE, $state, $q) {
            if ($state.params.beerId) {
                // read beer mode
                var deferredBeer = $http.get(RESTBASE.URL + '/beer/'+$state.params.beerId);
                var deferredRating = $http.get(RESTBASE.URL + '/beer/'+$state.params.beerId + '/ratingsum');
                $q.all([deferredBeer, deferredRating]).then(function (results) {
                    $scope.beer = results[0].data;
                    $scope.beer.rating = results[1].data;
                })
            } else {
                // create beer Mode
                $scope.beer = {};
                var config = {
                };
                $http.get(RESTBASE.URL + '/company', config).success(function (response) {
                    $scope.breweries = response._embedded.companies;
                });
            }

            $scope.saveRating = function () {
                var data = {
                    'score': $scope.beer.rating,
                    'beer': $scope.beer._links.self.href
                };
                var config = {
                }
                $http.post(RESTBASE.URL + '/rating', data, config).success(function (response) {
                    console.log('saved');
                });
            }

            $scope.saveBeer = function() {
                $http.post(RESTBASE.URL + '/beer', $scope.beer, config).success(function (response) {
                   console.log('saved Beer');
                    $state.go('home');
                });
            }

            $scope.delete = function () {
                if ($state.params.beerId && $scope.beer) {

                    $http.delete($scope.beer._links.self.href).success(function (response) {
                        console.log('deleted Beer');
                        $state.go('home');
                    })
                }
            }
        }]);
})();
