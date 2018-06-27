(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('IpsController', IpsController);

    IpsController.$inject = ['Ips'];

    function IpsController(Ips) {

        var vm = this;

        vm.ips = [];

        loadAll();

        function loadAll() {
            Ips.query(function(result) {
                vm.ips = result;
                vm.searchQuery = null;
            });
        }
    }
})();
