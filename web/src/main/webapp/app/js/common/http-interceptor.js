/**
 * This is the custom http interceptor for the project that adds some headers (token) for processing the
 * AJAX requests
 *
 * $q is the async base implementation that is doing the voodoo magic for us :)
 */
(function () {
    'use strict';
    angular.module('springbeer').factory('beerHttpInterceptor', ['$q', '$rootScope', 'localStorageService',
        function ($q, $rootScope, localStorageService) {

            return {
                request: function (config) {
                    // if a token is "persisted" in local storage - add this as the request header as
                    // X-Rest-Authentication
                    if (localStorageService.get('authtoken')) {
                        config.headers['X-Rest-Authentication'] = localStorageService.get('authtoken');
                    }
                    // proceed with the normal request
                    return config || $q.when(config);
                },
                requestError: function (rejection) {
                    return $q.reject(rejection);
                },
                // intercept the response object - currently does no custom stuff
                response: function (response) {
                    return response || $q.when(response);
                },
                // in case the response is somehow not in the 200er HTTP state area, do some custom stuff...
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