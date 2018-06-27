(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('IpsDialogController', IpsDialogController);

    IpsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ips', 'AplicacionVacuna', 'Vacunador', 'Municipio'];

    function IpsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ips, AplicacionVacuna, Vacunador, Municipio) {
        var vm = this;

        vm.ips = entity;
        vm.clear = clear;
        vm.save = save;
        vm.aplicacionvacunas = AplicacionVacuna.query();
        vm.vacunadors = Vacunador.query();
        vm.municipios = Municipio.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ips.id !== null) {
                Ips.update(vm.ips, onSaveSuccess, onSaveError);
            } else {
                Ips.save(vm.ips, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:ipsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
