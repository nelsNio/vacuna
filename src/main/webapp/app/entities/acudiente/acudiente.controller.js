(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AcudienteController', AcudienteController);

    AcudienteController.$inject = ['Acudiente'];

    function AcudienteController(Acudiente) {

        var vm = this;

        vm.acudientes = [];

        loadAll();

        function loadAll() {
            Acudiente.query(function(result) {
                vm.acudientes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
