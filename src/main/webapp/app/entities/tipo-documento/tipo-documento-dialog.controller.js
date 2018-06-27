(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('TipoDocumentoDialogController', TipoDocumentoDialogController);

    TipoDocumentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoDocumento', 'Paciente', 'Vacunador', 'Acudiente'];

    function TipoDocumentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoDocumento, Paciente, Vacunador, Acudiente) {
        var vm = this;

        vm.tipoDocumento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacientes = Paciente.query();
        vm.vacunadors = Vacunador.query();
        vm.acudientes = Acudiente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoDocumento.id !== null) {
                TipoDocumento.update(vm.tipoDocumento, onSaveSuccess, onSaveError);
            } else {
                TipoDocumento.save(vm.tipoDocumento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:tipoDocumentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
