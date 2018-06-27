(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('PacienteDialogController', PacienteDialogController);

    PacienteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Paciente', 'AplicacionVacuna', 'Acudiente', 'TipoDocumento', 'Genero', 'Aseguradora', 'GrupoEtnico', 'Regimen', 'Municipio', 'TipoResidencia'];

    function PacienteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Paciente, AplicacionVacuna, Acudiente, TipoDocumento, Genero, Aseguradora, GrupoEtnico, Regimen, Municipio, TipoResidencia) {
        var vm = this;

        vm.paciente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.aplicacionvacunas = AplicacionVacuna.query();
        vm.acudientes = Acudiente.query();
        vm.tipodocumentos = TipoDocumento.query();
        vm.generos = Genero.query();
        vm.aseguradoras = Aseguradora.query();
        vm.grupoetnicos = GrupoEtnico.query();
        vm.regimen = Regimen.query();
        vm.municipios = Municipio.query();
        vm.tiporesidencias = TipoResidencia.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paciente.id !== null) {
                Paciente.update(vm.paciente, onSaveSuccess, onSaveError);
            } else {
                Paciente.save(vm.paciente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:pacienteUpdate', result);
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
