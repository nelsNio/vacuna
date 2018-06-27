(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('MunicipioDialogController', MunicipioDialogController);

    MunicipioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Municipio', 'Paciente', 'Departamento', 'Ips'];

    function MunicipioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Municipio, Paciente, Departamento, Ips) {
        var vm = this;

        vm.municipio = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pacientes = Paciente.query();
        vm.departamentos = Departamento.query();
        vm.ips = Ips.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.municipio.id !== null) {
                Municipio.update(vm.municipio, onSaveSuccess, onSaveError);
            } else {
                Municipio.save(vm.municipio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:municipioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
