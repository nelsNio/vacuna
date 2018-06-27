(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('DosisDetailController', DosisDetailController);

    DosisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Dosis', 'AplicacionVacuna', 'Biologico'];

    function DosisDetailController($scope, $rootScope, $stateParams, previousState, entity, Dosis, AplicacionVacuna, Biologico) {
        var vm = this;

        vm.dosis = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:dosisUpdate', function(event, result) {
            vm.dosis = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
