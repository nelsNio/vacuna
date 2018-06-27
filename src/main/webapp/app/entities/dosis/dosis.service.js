(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('Dosis', Dosis);

    Dosis.$inject = ['$resource'];

    function Dosis ($resource) {
        var resourceUrl =  'api/doses/:id';

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
