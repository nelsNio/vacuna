(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoResidenciaDeleteController',TipoResidenciaDeleteController);

    TipoResidenciaDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoResidencia'];

    function TipoResidenciaDeleteController($uibModalInstance, entity, TipoResidencia) {
        var vm = this;

        vm.tipoResidencia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoResidencia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
