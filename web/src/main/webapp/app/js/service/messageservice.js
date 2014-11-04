(function () {
    'use strict';

    angular.module('springbeer').service('MessageService', ['$rootScope', '$state',
        function ($rootScope, $state) {
            console.log('loaded MessageService');
            $rootScope.$on(':error:denied', function (data) {
                console.log('error');
                $state.go('login');
            })
        }]);
})();
