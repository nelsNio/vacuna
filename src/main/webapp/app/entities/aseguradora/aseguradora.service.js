(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('Aseguradora', Aseguradora);

    Aseguradora.$inject = ['$resource'];

    function Aseguradora ($resource) {
        var resourceUrl =  'api/aseguradoras/:id';

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
