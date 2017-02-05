'use strict';

describe('Controller Tests', function() {

    describe('TBUsuario Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTBUsuario, MockTBProjeto, MockTBModeloGenerico, MockTBBase;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTBUsuario = jasmine.createSpy('MockTBUsuario');
            MockTBProjeto = jasmine.createSpy('MockTBProjeto');
            MockTBModeloGenerico = jasmine.createSpy('MockTBModeloGenerico');
            MockTBBase = jasmine.createSpy('MockTBBase');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TBUsuario': MockTBUsuario,
                'TBProjeto': MockTBProjeto,
                'TBModeloGenerico': MockTBModeloGenerico,
                'TBBase': MockTBBase
            };
            createController = function() {
                $injector.get('$controller')("TBUsuarioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projeto1App:tBUsuarioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
