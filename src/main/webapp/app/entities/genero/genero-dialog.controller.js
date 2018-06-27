(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('GeneroDialogController', GeneroDialogController);

    GeneroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Genero', 'Paciente', 'Vacunador', 'Acudiente'];

    function GeneroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Genero, Paciente, Vacunador, Acudiente) {
        var vm = this;

        vm.genero = entity;
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
            if (vm.genero.id !== null) {
                Genero.update(vm.genero, onSaveSuccess, onSaveError);
            } else {
                Genero.save(vm.genero, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:generoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
