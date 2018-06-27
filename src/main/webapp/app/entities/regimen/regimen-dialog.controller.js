(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('RegimenDialogController', RegimenDialogController);

    RegimenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Regimen', 'Paciente', 'Aseguradora'];

    function RegimenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Regimen, Paciente, Aseguradora) {
        var vm = this;

        vm.regimen = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacientes = Paciente.query();
        vm.aseguradoras = Aseguradora.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.regimen.id !== null) {
                Regimen.update(vm.regimen, onSaveSuccess, onSaveError);
            } else {
                Regimen.save(vm.regimen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:regimenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
