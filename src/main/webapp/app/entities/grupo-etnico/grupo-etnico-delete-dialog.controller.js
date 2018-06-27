(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('GrupoEtnicoDeleteController',GrupoEtnicoDeleteController);

    GrupoEtnicoDeleteController.$inject = ['$uibModalInstance', 'entity', 'GrupoEtnico'];

    function GrupoEtnicoDeleteController($uibModalInstance, entity, GrupoEtnico) {
        var vm = this;

        vm.grupoEtnico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GrupoEtnico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
