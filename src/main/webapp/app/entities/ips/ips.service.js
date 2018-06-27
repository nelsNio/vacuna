(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('Ips', Ips);

    Ips.$inject = ['$resource'];

    function Ips ($resource) {
        var resourceUrl =  'api/ips/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
