(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('DepartamentoDetailController', DepartamentoDetailController);

    DepartamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Departamento', 'Municipio', 'Pais'];

    function DepartamentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Departamento, Municipio, Pais) {
        var vm = this;

        vm.departamento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:departamentoUpdate', function(event, result) {
            vm.departamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
