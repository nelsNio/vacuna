(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('GrupoEtnicoDialogController', GrupoEtnicoDialogController);

    GrupoEtnicoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GrupoEtnico', 'Paciente'];

    function GrupoEtnicoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GrupoEtnico, Paciente) {
        var vm = this;

        vm.grupoEtnico = entity;
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
            if (vm.grupoEtnico.id !== null) {
                GrupoEtnico.update(vm.grupoEtnico, onSaveSuccess, onSaveError);
            } else {
                GrupoEtnico.save(vm.grupoEtnico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:grupoEtnicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
