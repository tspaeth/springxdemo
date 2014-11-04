(function () {
    'use strict';

    angular.module('springbeer').factory('UserService', ['$http', 'RESTBASE', '$q',
        function ($http, RESTBASE, $q) {
            return {
                userLogin: function (username, password) {
                    console.log('Login ', username, ' with ', password);
                    var data = {},
                        authCall = $q.defer();
                    var httpConfig = {

                    }
                    var transformRequest = function(obj) {
                        var str = [];
                        for (var key in obj) {
                            if (obj[key] instanceof Array) {
                                for(var idx in obj[key]){
                                    var subObj = obj[key][idx];
                                    for(var subKey in subObj){
                                        str.push(encodeURIComponent(key) + "[" + idx + "][" + encodeURIComponent(subKey) + "]=" + encodeURIComponent(subObj[subKey]));
                                    }
                                }
                            }
                            else {
                                str.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
                            }
                        }
                        return str.join("&");
                    }

                    data.username = username;
                    data.password = password;
                    var request = $http({
                        url: RESTBASE.URL + '/auth',
                        method: 'POST',
                        transformRequest: transformRequest,
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        data: data,
                        config: httpConfig
                    });
                    request.then(function (response) {
                        authCall.resolve(response);
                        console.log('--> ', response);
                    });
                    return authCall.promise;
                }
            };
        }]);
})();
