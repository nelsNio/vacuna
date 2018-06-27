'use strict';

describe('Controller Tests', function() {

    describe('Municipio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMunicipio, MockPaciente, MockDepartamento, MockIps;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMunicipio = jasmine.createSpy('MockMunicipio');
            MockPaciente = jasmine.createSpy('MockPaciente');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            MockIps = jasmine.createSpy('MockIps');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Municipio': MockMunicipio,
                'Paciente': MockPaciente,
                'Departamento': MockDepartamento,
                'Ips': MockIps
            };
            createController = function() {
                $injector.get('$controller')("MunicipioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:municipioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
