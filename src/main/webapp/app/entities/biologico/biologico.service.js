(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('Biologico', Biologico);

    Biologico.$inject = ['$resource'];

    function Biologico ($resource) {
        var resourceUrl =  'api/biologicos/:id';

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
