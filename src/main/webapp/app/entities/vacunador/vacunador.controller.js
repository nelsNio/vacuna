(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('VacunadorController', VacunadorController);

    VacunadorController.$inject = ['Vacunador'];

    function VacunadorController(Vacunador) {

        var vm = this;

        vm.vacunadors = [];

        loadAll();

        function loadAll() {
            Vacunador.query(function(result) {
                vm.vacunadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
