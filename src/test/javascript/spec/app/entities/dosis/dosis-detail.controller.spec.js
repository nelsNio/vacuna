'use strict';

describe('Controller Tests', function() {

    describe('Dosis Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDosis, MockAplicacionVacuna, MockBiologico;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDosis = jasmine.createSpy('MockDosis');
            MockAplicacionVacuna = jasmine.createSpy('MockAplicacionVacuna');
            MockBiologico = jasmine.createSpy('MockBiologico');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Dosis': MockDosis,
                'AplicacionVacuna': MockAplicacionVacuna,
                'Biologico': MockBiologico
            };
            createController = function() {
                $injector.get('$controller')("DosisDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:dosisUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
