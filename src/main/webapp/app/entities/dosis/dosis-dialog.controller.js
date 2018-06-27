(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('DosisDialogController', DosisDialogController);

    DosisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dosis', 'AplicacionVacuna', 'Biologico'];

    function DosisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dosis, AplicacionVacuna, Biologico) {
        var vm = this;

        vm.dosis = entity;
        vm.clear = clear;
        vm.save = save;
        vm.aplicacionvacunas = AplicacionVacuna.query();
        vm.biologicos = Biologico.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dosis.id !== null) {
                Dosis.update(vm.dosis, onSaveSuccess, onSaveError);
            } else {
                Dosis.save(vm.dosis, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:dosisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
