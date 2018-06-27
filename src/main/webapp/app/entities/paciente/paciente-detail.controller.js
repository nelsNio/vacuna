(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('PacienteDetailController', PacienteDetailController);

    PacienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Paciente', 'AplicacionVacuna', 'Acudiente', 'TipoDocumento', 'Genero', 'Aseguradora', 'GrupoEtnico', 'Regimen', 'Municipio', 'TipoResidencia'];

    function PacienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Paciente, AplicacionVacuna, Acudiente, TipoDocumento, Genero, Aseguradora, GrupoEtnico, Regimen, Municipio, TipoResidencia) {
        var vm = this;

        vm.paciente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:pacienteUpdate', function(event, result) {
            vm.paciente = result;
        });
        $scope.$on('$destroy', unsubscribe);
		
		vm.generar = function (clientID){
			/*
			aquí se hace la consulta del cliente por el id o como se le de la gana de generar el jasper report teniendo en cuenta el id del cliente
			*/
			
			alert(clientID);
		}
    }
})();
