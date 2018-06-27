(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AseguradoraDetailController', AseguradoraDetailController);

    AseguradoraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aseguradora', 'Paciente', 'Regimen'];

    function AseguradoraDetailController($scope, $rootScope, $stateParams, previousState, entity, Aseguradora, Paciente, Regimen) {
        var vm = this;

        vm.aseguradora = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:aseguradoraUpdate', function(event, result) {
            vm.aseguradora = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
