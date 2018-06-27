(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('PaisDetailController', PaisDetailController);

    PaisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pais', 'Departamento'];

    function PaisDetailController($scope, $rootScope, $stateParams, previousState, entity, Pais, Departamento) {
        var vm = this;

        vm.pais = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:paisUpdate', function(event, result) {
            vm.pais = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
