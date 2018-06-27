(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('RegimenDeleteController',RegimenDeleteController);

    RegimenDeleteController.$inject = ['$uibModalInstance', 'entity', 'Regimen'];

    function RegimenDeleteController($uibModalInstance, entity, Regimen) {
        var vm = this;

        vm.regimen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Regimen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
