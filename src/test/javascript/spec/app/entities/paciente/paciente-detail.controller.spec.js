'use strict';

describe('Controller Tests', function() {

    describe('Paciente Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPaciente, MockAplicacionVacuna, MockAcudiente, MockTipoDocumento, MockGenero, MockAseguradora, MockGrupoEtnico, MockRegimen, MockMunicipio, MockTipoResidencia;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockAplicacionVacuna = jasmine.createSpy('MockAplicacionVacuna');
            MockAcudiente = jasmine.createSpy('MockAcudiente');
            MockTipoDocumento = jasmine.createSpy('MockTipoDocumento');
            MockGenero = jasmine.createSpy('MockGenero');
            MockAseguradora = jasmine.createSpy('MockAseguradora');
            MockGrupoEtnico = jasmine.createSpy('MockGrupoEtnico');
            MockRegimen = jasmine.createSpy('MockRegimen');
            MockMunicipio = jasmine.createSpy('MockMunicipio');
            MockTipoResidencia = jasmine.createSpy('MockTipoResidencia');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Paciente': MockPaciente,
                'AplicacionVacuna': MockAplicacionVacuna,
                'Acudiente': MockAcudiente,
                'TipoDocumento': MockTipoDocumento,
                'Genero': MockGenero,
                'Aseguradora': MockAseguradora,
                'GrupoEtnico': MockGrupoEtnico,
                'Regimen': MockRegimen,
                'Municipio': MockMunicipio,
                'TipoResidencia': MockTipoResidencia
            };
            createController = function() {
                $injector.get('$controller')("PacienteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:pacienteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
