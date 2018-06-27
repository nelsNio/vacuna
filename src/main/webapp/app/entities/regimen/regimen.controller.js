(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('RegimenController', RegimenController);

    RegimenController.$inject = ['Regimen'];

    function RegimenController(Regimen) {

        var vm = this;

        vm.regimen = [];

        loadAll();

        function loadAll() {
            Regimen.query(function(result) {
                vm.regimen = result;
                vm.searchQuery = null;
            });
        }
    }
})();
