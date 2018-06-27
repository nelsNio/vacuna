(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoDocumentoDeleteController',TipoDocumentoDeleteController);

    TipoDocumentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoDocumento'];

    function TipoDocumentoDeleteController($uibModalInstance, entity, TipoDocumento) {
        var vm = this;

        vm.tipoDocumento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoDocumento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
