'use strict';

describe('Controller Tests', function() {

    describe('Vacunador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVacunador, MockAplicacionVacuna, MockTipoDocumento, MockGenero, MockIps;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVacunador = jasmine.createSpy('MockVacunador');
            MockAplicacionVacuna = jasmine.createSpy('MockAplicacionVacuna');
            MockTipoDocumento = jasmine.createSpy('MockTipoDocumento');
            MockGenero = jasmine.createSpy('MockGenero');
            MockIps = jasmine.createSpy('MockIps');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Vacunador': MockVacunador,
                'AplicacionVacuna': MockAplicacionVacuna,
                'TipoDocumento': MockTipoDocumento,
                'Genero': MockGenero,
                'Ips': MockIps
            };
            createController = function() {
                $injector.get('$controller')("VacunadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:vacunadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
