(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('BiologicoController', BiologicoController);

    BiologicoController.$inject = ['Biologico'];

    function BiologicoController(Biologico) {

        var vm = this;

        vm.biologicos = [];

        loadAll();

        function loadAll() {
            Biologico.query(function(result) {
                vm.biologicos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
