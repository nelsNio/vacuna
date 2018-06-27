(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('VacunadorDialogController', VacunadorDialogController);

    VacunadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vacunador', 'AplicacionVacuna', 'TipoDocumento', 'Genero', 'Ips'];

    function VacunadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Vacunador, AplicacionVacuna, TipoDocumento, Genero, Ips) {
        var vm = this;

        vm.vacunador = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.aplicacionvacunas = AplicacionVacuna.query();
        vm.tipodocumentos = TipoDocumento.query();
        vm.generos = Genero.query();
        vm.ips = Ips.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vacunador.id !== null) {
                Vacunador.update(vm.vacunador, onSaveSuccess, onSaveError);
            } else {
                Vacunador.save(vm.vacunador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:vacunadorUpdate', result);
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
