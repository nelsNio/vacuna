(function() {
    'use strict';
    angular
        .module('vacunasApp')
        .factory('GrupoEtnico', GrupoEtnico);

    GrupoEtnico.$inject = ['$resource'];

    function GrupoEtnico ($resource) {
        var resourceUrl =  'api/grupo-etnicos/:id';

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
