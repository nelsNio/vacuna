(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('DosisController', DosisController);

    DosisController.$inject = ['Dosis'];

    function DosisController(Dosis) {

        var vm = this;

        vm.doses = [];

        loadAll();

        function loadAll() {
            Dosis.query(function(result) {
                vm.doses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
