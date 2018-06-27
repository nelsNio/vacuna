(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('estadovacunas', {
            parent: 'reporte',
            url: '/reporte_vacunas',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Reporte Vacunas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/reportes/vacunados/vacunados.html',
                    controller: 'VacunadosController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
    }

})();
