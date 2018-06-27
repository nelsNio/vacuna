(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('GeneroDetailController', GeneroDetailController);

    GeneroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Genero', 'Paciente', 'Vacunador', 'Acudiente'];

    function GeneroDetailController($scope, $rootScope, $stateParams, previousState, entity, Genero, Paciente, Vacunador, Acudiente) {
        var vm = this;

        vm.genero = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:generoUpdate', function(event, result) {
            vm.genero = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
