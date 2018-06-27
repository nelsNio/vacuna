(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AcudienteDialogController', AcudienteDialogController);

    AcudienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Acudiente', 'TipoDocumento', 'Genero', 'Paciente'];

    function AcudienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Acudiente, TipoDocumento, Genero, Paciente) {
        var vm = this;

        vm.acudiente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tipodocumentos = TipoDocumento.query();
        vm.generos = Genero.query();
        vm.pacientes = Paciente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.acudiente.id !== null) {
                Acudiente.update(vm.acudiente, onSaveSuccess, onSaveError);
            } else {
                Acudiente.save(vm.acudiente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:acudienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaNacimiento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
