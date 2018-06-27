(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoDocumentoDetailController', TipoDocumentoDetailController);

    TipoDocumentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoDocumento', 'Paciente', 'Vacunador', 'Acudiente'];

    function TipoDocumentoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoDocumento, Paciente, Vacunador, Acudiente) {
        var vm = this;

        vm.tipoDocumento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:tipoDocumentoUpdate', function(event, result) {
            vm.tipoDocumento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
