(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoResidenciaDialogController', TipoResidenciaDialogController);

    TipoResidenciaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoResidencia', 'Paciente'];

    function TipoResidenciaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoResidencia, Paciente) {
        var vm = this;

        vm.tipoResidencia = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacientes = Paciente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoResidencia.id !== null) {
                TipoResidencia.update(vm.tipoResidencia, onSaveSuccess, onSaveError);
            } else {
                TipoResidencia.save(vm.tipoResidencia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:tipoResidenciaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
