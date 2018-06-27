(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AplicacionVacunaController', AplicacionVacunaController);

    AplicacionVacunaController.$inject = ['AplicacionVacuna'];

    function AplicacionVacunaController(AplicacionVacuna) {

        var vm = this;

        vm.aplicacionVacunas = [];

        loadAll();

        function loadAll() {
            AplicacionVacuna.query(function(result) {
                vm.aplicacionVacunas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
