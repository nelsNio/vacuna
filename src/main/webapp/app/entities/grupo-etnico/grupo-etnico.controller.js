(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('GrupoEtnicoController', GrupoEtnicoController);

    GrupoEtnicoController.$inject = ['GrupoEtnico'];

    function GrupoEtnicoController(GrupoEtnico) {

        var vm = this;

        vm.grupoEtnicos = [];

        loadAll();

        function loadAll() {
            GrupoEtnico.query(function(result) {
                vm.grupoEtnicos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
