(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AplicacionVacunaDetailController', AplicacionVacunaDetailController);

    AplicacionVacunaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AplicacionVacuna', 'Vacunador', 'Paciente', 'Dosis', 'Ips'];

    function AplicacionVacunaDetailController($scope, $rootScope, $stateParams, previousState, entity, AplicacionVacuna, Vacunador, Paciente, Dosis, Ips) {
        var vm = this;

        vm.aplicacionVacuna = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:aplicacionVacunaUpdate', function(event, result) {
            vm.aplicacionVacuna = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
