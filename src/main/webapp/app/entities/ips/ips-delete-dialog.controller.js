(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('IpsDeleteController',IpsDeleteController);

    IpsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ips'];

    function IpsDeleteController($uibModalInstance, entity, Ips) {
        var vm = this;

        vm.ips = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ips.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
