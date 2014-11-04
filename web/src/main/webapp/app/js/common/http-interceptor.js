/**
 * This is the custom http interceptor for the project that adds some headers (token) for processing the
 * AJAX requests
 *
 */
(function () {
    'use strict';
    angular.module('springbeer').factory('beerHttpInterceptor', ['$q', '$rootScope', 'localStorageService',
        function ($q, $rootScope, localStorageService) {

            return {
                request: function (config) {
                    if (localStorageService.get('authtoken')) {
                        config.headers['X-Rest-Authentication'] = localStorageService.get('authtoken');
                    }
                    return config || $q.when(config);
                },
                requestError: function (rejection) {
                    return $q.reject(rejection);
                },
                response: function (response) {
                    return response || $q.when(response);
                },
                responseError: function (response) {
                    // do something on error
                    if (response.status === 0) {
                        // looks like the network is down?
                        // .. do something here .. like popup, alert box... whatever
                    } else if (response.status === 401) {
                        $rootScope.$broadcast(':error:denied',{'status': '401'});
//                        $state.go('login');
                    } else if (response.status === 500) {
                    } else if (response.status === 404) {
                    }
                    return $q.reject(response);
                }
            };

        }])
})();