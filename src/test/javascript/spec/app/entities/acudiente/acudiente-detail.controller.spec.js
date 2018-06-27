'use strict';

describe('Controller Tests', function() {

    describe('Acudiente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAcudiente, MockTipoDocumento, MockGenero, MockPaciente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAcudiente = jasmine.createSpy('MockAcudiente');
            MockTipoDocumento = jasmine.createSpy('MockTipoDocumento');
            MockGenero = jasmine.createSpy('MockGenero');
            MockPaciente = jasmine.createSpy('MockPaciente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Acudiente': MockAcudiente,
                'TipoDocumento': MockTipoDocumento,
                'Genero': MockGenero,
                'Paciente': MockPaciente
            };
            createController = function() {
                $injector.get('$controller')("AcudienteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:acudienteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
