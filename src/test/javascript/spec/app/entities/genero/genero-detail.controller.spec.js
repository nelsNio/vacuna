'use strict';

describe('Controller Tests', function() {

    describe('Genero Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGenero, MockPaciente, MockVacunador, MockAcudiente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGenero = jasmine.createSpy('MockGenero');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockVacunador = jasmine.createSpy('MockVacunador');
            MockAcudiente = jasmine.createSpy('MockAcudiente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Genero': MockGenero,
                'Paciente': MockPaciente,
                'Vacunador': MockVacunador,
                'Acudiente': MockAcudiente
            };
            createController = function() {
                $injector.get('$controller')("GeneroDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:generoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
