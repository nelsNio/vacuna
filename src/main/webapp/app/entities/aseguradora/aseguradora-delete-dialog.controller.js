(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AseguradoraDeleteController',AseguradoraDeleteController);

    AseguradoraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Aseguradora'];

    function AseguradoraDeleteController($uibModalInstance, entity, Aseguradora) {
        var vm = this;

        vm.aseguradora = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Aseguradora.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
