(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('BiologicoDialogController', BiologicoDialogController);

    BiologicoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Biologico', 'Dosis'];

    function BiologicoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Biologico, Dosis) {
        var vm = this;

        vm.biologico = entity;
        vm.clear = clear;
        vm.save = save;
        vm.doses = Dosis.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.biologico.id !== null) {
                Biologico.update(vm.biologico, onSaveSuccess, onSaveError);
            } else {
                Biologico.save(vm.biologico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('vacunasApp:biologicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
