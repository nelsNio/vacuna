(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoDocumentoController', TipoDocumentoController);

    TipoDocumentoController.$inject = ['TipoDocumento'];

    function TipoDocumentoController(TipoDocumento) {

        var vm = this;

        vm.tipoDocumentos = [];

        loadAll();

        function loadAll() {
            TipoDocumento.query(function(result) {
                vm.tipoDocumentos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
