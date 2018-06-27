'use strict';

describe('Controller Tests', function() {

    describe('Departamento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDepartamento, MockMunicipio, MockPais;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            MockMunicipio = jasmine.createSpy('MockMunicipio');
            MockPais = jasmine.createSpy('MockPais');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Departamento': MockDepartamento,
                'Municipio': MockMunicipio,
                'Pais': MockPais
            };
            createController = function() {
                $injector.get('$controller')("DepartamentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'vacunasApp:departamentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
