(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoResidenciaController', TipoResidenciaController);

    TipoResidenciaController.$inject = ['TipoResidencia'];

    function TipoResidenciaController(TipoResidencia) {

        var vm = this;

        vm.tipoResidencias = [];

        loadAll();

        function loadAll() {
            TipoResidencia.query(function(result) {
                vm.tipoResidencias = result;
                vm.searchQuery = null;
            });
        }
    }
})();
