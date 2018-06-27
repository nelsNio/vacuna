(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('IpsDetailController', IpsDetailController);

    IpsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ips', 'AplicacionVacuna', 'Vacunador', 'Municipio'];

    function IpsDetailController($scope, $rootScope, $stateParams, previousState, entity, Ips, AplicacionVacuna, Vacunador, Municipio) {
        var vm = this;

        vm.ips = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:ipsUpdate', function(event, result) {
            vm.ips = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
