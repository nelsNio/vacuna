(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('MunicipioDetailController', MunicipioDetailController);

    MunicipioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Municipio', 'Paciente', 'Departamento', 'Ips'];

    function MunicipioDetailController($scope, $rootScope, $stateParams, previousState, entity, Municipio, Paciente, Departamento, Ips) {
        var vm = this;

        vm.municipio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:municipioUpdate', function(event, result) {
            vm.municipio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
