(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AcudienteDeleteController',AcudienteDeleteController);

    AcudienteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Acudiente'];

    function AcudienteDeleteController($uibModalInstance, entity, Acudiente) {
        var vm = this;

        vm.acudiente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Acudiente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
