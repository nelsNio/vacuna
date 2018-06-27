(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('DosisDeleteController',DosisDeleteController);

    DosisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Dosis'];

    function DosisDeleteController($uibModalInstance, entity, Dosis) {
        var vm = this;

        vm.dosis = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Dosis.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
