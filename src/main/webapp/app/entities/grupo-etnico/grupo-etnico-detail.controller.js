(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('GrupoEtnicoDetailController', GrupoEtnicoDetailController);

    GrupoEtnicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GrupoEtnico', 'Paciente'];

    function GrupoEtnicoDetailController($scope, $rootScope, $stateParams, previousState, entity, GrupoEtnico, Paciente) {
        var vm = this;

        vm.grupoEtnico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:grupoEtnicoUpdate', function(event, result) {
            vm.grupoEtnico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
