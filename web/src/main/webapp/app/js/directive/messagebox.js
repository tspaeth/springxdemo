/**
 * @description This is a simple directive, just showing the base structure...
 *
 * @author Thorsten Spaeth <info@conserata.com>
 *
 *
 */
(function () {
    'use strict';
    angular.module('springbeer').directive('messagebox', ['$rootScope', '$timeout', function ($rootScope, $timeout) {
        return {
            // only allow the directive to be used as an element <messagebox></messagebox>
            restrict: 'E',
            // replace the existing DOM content (children)
            replace: true,
            // setup the directive to listen for :error:denied events and in case render / update the content
            link: function (scope, elem) {
                scope.alerts = [];
                $rootScope.$on(':error:denied', function(ev, data) {
                    if (data.status !== undefined && data.status === '401') {
                        var keyCount = scope.alerts.length;
                        scope.alerts[keyCount] = 'Access denied. Please login.';

                        // just for simplicity - clean up the alertmsg after 8 seconds
                        $timeout(function () {
                            scope.alerts = [];
                        }, 8000);
                    }

                });
            },
            // template definition to be used for displaying the content.

            template: '<div><ul><li ng-repeat="alertmsg in alerts">{{alertmsg}}</li></ul>'
        };
    }]);
})();


