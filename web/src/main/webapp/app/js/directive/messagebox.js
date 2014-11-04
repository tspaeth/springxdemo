(function () {
    'use strict';
    angular.module('springbeer').directive('messagebox', ['$rootScope', '$timeout', function ($rootScope, $timeout) {



        return {
            restrict: 'E',
            replace: true,

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
            template: '<div><ul><li ng-repeat="alertmsg in alerts">{{alertmsg}}</li></ul>'
        };
    }]);
})();


