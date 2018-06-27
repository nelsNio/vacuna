(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('Regimen', Regimen);

    Regimen.$inject = ['$resource'];

    function Regimen ($resource) {
        var resourceUrl =  'api/regimen/:id';

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
