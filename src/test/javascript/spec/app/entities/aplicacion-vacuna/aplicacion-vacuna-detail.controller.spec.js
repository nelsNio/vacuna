'use strict';

describe('Controller Tests', function() {

    describe('AplicacionVacuna Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAplicacionVacuna, MockVacunador, MockPaciente, MockDosis, MockIps;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAplicacionVacuna = jasmine.createSpy('MockAplicacionVacuna');
            MockVacunador = jasmine.createSpy('MockVacunador');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockDosis = jasmine.createSpy('MockDosis');
            MockIps = jasmine.createSpy('MockIps');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AplicacionVacuna': MockAplicacionVacuna,
                'Vacunador': MockVacunador,
                'Paciente': MockPaciente,
                'Dosis': MockDosis,
                'Ips': MockIps
            };
            createController = function() {
                $injector.get('$controller')("AplicacionVacunaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:aplicacionVacunaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
