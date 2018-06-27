(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('RegimenDetailController', RegimenDetailController);

    RegimenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Regimen', 'Paciente', 'Aseguradora'];

    function RegimenDetailController($scope, $rootScope, $stateParams, previousState, entity, Regimen, Paciente, Aseguradora) {
        var vm = this;

        vm.regimen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:regimenUpdate', function(event, result) {
            vm.regimen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
