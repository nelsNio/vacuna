(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoResidenciaDetailController', TipoResidenciaDetailController);

    TipoResidenciaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoResidencia', 'Paciente'];

    function TipoResidenciaDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoResidencia, Paciente) {
        var vm = this;

        vm.tipoResidencia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:tipoResidenciaUpdate', function(event, result) {
            vm.tipoResidencia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
