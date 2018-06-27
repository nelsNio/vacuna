(function() {
    'use strict';
    angular
        .module('vacunasApp')
        
        
        .factory('ReporteVacunados', ReporteVacunados);

        ReporteVacunados.$inject = ['$resource'];
        function ReporteVacunados ($resource, DateUtils) {
            var resourceUrl =  'api/pacientes/reporte';
    
            return $resource(resourceUrl, {}, {
                'query': { method: 'GET', isArray: true}
                
            });
        }
    

   
})();
