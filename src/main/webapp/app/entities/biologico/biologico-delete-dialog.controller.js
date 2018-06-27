(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('BiologicoDeleteController',BiologicoDeleteController);

    BiologicoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Biologico'];

    function BiologicoDeleteController($uibModalInstance, entity, Biologico) {
        var vm = this;

        vm.biologico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Biologico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
