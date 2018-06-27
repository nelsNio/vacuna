(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AplicacionVacunaDeleteController',AplicacionVacunaDeleteController);

    AplicacionVacunaDeleteController.$inject = ['$uibModalInstance', 'entity', 'AplicacionVacuna'];

    function AplicacionVacunaDeleteController($uibModalInstance, entity, AplicacionVacuna) {
        var vm = this;

        vm.aplicacionVacuna = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AplicacionVacuna.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
