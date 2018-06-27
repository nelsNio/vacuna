(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AseguradoraController', AseguradoraController);

    AseguradoraController.$inject = ['Aseguradora'];

    function AseguradoraController(Aseguradora) {

        var vm = this;

        vm.aseguradoras = [];

        loadAll();

        function loadAll() {
            Aseguradora.query(function(result) {
                vm.aseguradoras = result;
                vm.searchQuery = null;
            });
        }
    }
})();
