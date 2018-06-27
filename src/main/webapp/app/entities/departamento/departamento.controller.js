(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('DepartamentoController', DepartamentoController);

    DepartamentoController.$inject = ['Departamento'];

    function DepartamentoController(Departamento) {

        var vm = this;

        vm.departamentos = [];

        loadAll();

        function loadAll() {
            Departamento.query(function(result) {
                vm.departamentos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
