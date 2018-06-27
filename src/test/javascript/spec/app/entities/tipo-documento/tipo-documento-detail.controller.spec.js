'use strict';

describe('Controller Tests', function() {

    describe('TipoDocumento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTipoDocumento, MockPaciente, MockVacunador, MockAcudiente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTipoDocumento = jasmine.createSpy('MockTipoDocumento');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockVacunador = jasmine.createSpy('MockVacunador');
            MockAcudiente = jasmine.createSpy('MockAcudiente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TipoDocumento': MockTipoDocumento,
                'Paciente': MockPaciente,
                'Vacunador': MockVacunador,
                'Acudiente': MockAcudiente
            };
            createController = function() {
                $injector.get('$controller')("TipoDocumentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:tipoDocumentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
