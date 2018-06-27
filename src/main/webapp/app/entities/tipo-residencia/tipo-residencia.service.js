(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('TipoResidencia', TipoResidencia);

    TipoResidencia.$inject = ['$resource'];

    function TipoResidencia ($resource) {
        var resourceUrl =  'api/tipo-residencias/:id';

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
