(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('VacunadorDeleteController',VacunadorDeleteController);

    VacunadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vacunador'];

    function VacunadorDeleteController($uibModalInstance, entity, Vacunador) {
        var vm = this;

        vm.vacunador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Vacunador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
