(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AseguradoraDialogController', AseguradoraDialogController);

    AseguradoraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Aseguradora', 'Paciente', 'Regimen'];

    function AseguradoraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Aseguradora, Paciente, Regimen) {
        var vm = this;

        vm.aseguradora = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacientes = Paciente.query();
        vm.regimen = Regimen.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aseguradora.id !== null) {
                Aseguradora.update(vm.aseguradora, onSaveSuccess, onSaveError);
            } else {
                Aseguradora.save(vm.aseguradora, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:aseguradoraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
