'use strict';

describe('Controller Tests', function() {

    describe('Aseguradora Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAseguradora, MockPaciente, MockRegimen;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAseguradora = jasmine.createSpy('MockAseguradora');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockRegimen = jasmine.createSpy('MockRegimen');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Aseguradora': MockAseguradora,
                'Paciente': MockPaciente,
                'Regimen': MockRegimen
            };
            createController = function() {
                $injector.get('$controller')("AseguradoraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:aseguradoraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
