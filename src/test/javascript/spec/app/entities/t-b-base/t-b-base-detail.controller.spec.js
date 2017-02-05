'use strict';

describe('Controller Tests', function() {

    describe('TBBase Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTBBase, MockTBUsuario, MockTBProjeto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTBBase = jasmine.createSpy('MockTBBase');
            MockTBUsuario = jasmine.createSpy('MockTBUsuario');
            MockTBProjeto = jasmine.createSpy('MockTBProjeto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TBBase': MockTBBase,
                'TBUsuario': MockTBUsuario,
                'TBProjeto': MockTBProjeto
            };
            createController = function() {
                $injector.get('$controller')("TBBaseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projeto1App:tBBaseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
