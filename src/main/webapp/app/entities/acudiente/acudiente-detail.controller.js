(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AcudienteDetailController', AcudienteDetailController);

    AcudienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Acudiente', 'TipoDocumento', 'Genero', 'Paciente'];

    function AcudienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Acudiente, TipoDocumento, Genero, Paciente) {
        var vm = this;

        vm.acudiente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:acudienteUpdate', function(event, result) {
            vm.acudiente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
