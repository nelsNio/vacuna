(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('BiologicoDetailController', BiologicoDetailController);

    BiologicoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Biologico', 'Dosis'];

    function BiologicoDetailController($scope, $rootScope, $stateParams, previousState, entity, Biologico, Dosis) {
        var vm = this;

        vm.biologico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('vacunasApp:biologicoUpdate', function(event, result) {
            vm.biologico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
