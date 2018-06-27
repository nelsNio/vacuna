'use strict';

describe('Controller Tests', function() {

    describe('Ips Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIps, MockAplicacionVacuna, MockVacunador, MockMunicipio;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIps = jasmine.createSpy('MockIps');
            MockAplicacionVacuna = jasmine.createSpy('MockAplicacionVacuna');
            MockVacunador = jasmine.createSpy('MockVacunador');
            MockMunicipio = jasmine.createSpy('MockMunicipio');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ips': MockIps,
                'AplicacionVacuna': MockAplicacionVacuna,
                'Vacunador': MockVacunador,
                'Municipio': MockMunicipio
            };
            createController = function() {
                $injector.get('$controller')("IpsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:ipsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
