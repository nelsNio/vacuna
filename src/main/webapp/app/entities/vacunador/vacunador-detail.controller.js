(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('VacunadorDetailController', VacunadorDetailController);

    VacunadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vacunador', 'AplicacionVacuna', 'TipoDocumento', 'Genero', 'Ips'];

    function VacunadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Vacunador, AplicacionVacuna, TipoDocumento, Genero, Ips) {
        var vm = this;

        vm.vacunador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:vacunadorUpdate', function(event, result) {
            vm.vacunador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
