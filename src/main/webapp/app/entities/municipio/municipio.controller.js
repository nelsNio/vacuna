(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .controller('MunicipioController', MunicipioController);

    MunicipioController.$inject = ['Municipio'];

    function MunicipioController(Municipio) {

        var vm = this;

        vm.municipios = [];

        loadAll();

        function loadAll() {
            Municipio.query(function(result) {
                vm.municipios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
