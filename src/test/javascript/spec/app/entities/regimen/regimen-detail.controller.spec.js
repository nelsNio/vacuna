'use strict';

describe('Controller Tests', function() {

    describe('Regimen Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRegimen, MockPaciente, MockAseguradora;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRegimen = jasmine.createSpy('MockRegimen');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockAseguradora = jasmine.createSpy('MockAseguradora');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Regimen': MockRegimen,
                'Paciente': MockPaciente,
                'Aseguradora': MockAseguradora
            };
            createController = function() {
                $injector.get('$controller')("RegimenDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:regimenUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
