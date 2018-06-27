(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('AplicacionVacunaDialogController', AplicacionVacunaDialogController);

    AplicacionVacunaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AplicacionVacuna', 'Vacunador', 'Paciente', 'Dosis', 'Ips'];

    function AplicacionVacunaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AplicacionVacuna, Vacunador, Paciente, Dosis, Ips) {
        var vm = this;

        vm.aplicacionVacuna = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.vacunadors = Vacunador.query();
        vm.pacientes = Paciente.query();
        vm.doses = Dosis.query();
        vm.ips = Ips.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aplicacionVacuna.id !== null) {
                AplicacionVacuna.update(vm.aplicacionVacuna, onSaveSuccess, onSaveError);
            } else {
                AplicacionVacuna.save(vm.aplicacionVacuna, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:aplicacionVacunaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
