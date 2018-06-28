(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('VacunadosController', VacunadosController);

        VacunadosController.$inject = ['ReporteVacunados']

    function VacunadosController(ReporteVacunados) {

        var vm = this;

        vm.pacientes = [];
        
         loadAll();

        function loadAll() {
            ReporteVacunados.query(function(result) {
                vm.pacientes = result;
                vm.searchQuery = null;
            });
        }

       

       
    }
})();
