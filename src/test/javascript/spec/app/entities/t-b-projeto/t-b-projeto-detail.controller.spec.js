'use strict';

describe('Controller Tests', function() {

    describe('TBProjeto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTBProjeto, MockTBUsuario, MockTBBase, MockTBSessao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTBProjeto = jasmine.createSpy('MockTBProjeto');
            MockTBUsuario = jasmine.createSpy('MockTBUsuario');
            MockTBBase = jasmine.createSpy('MockTBBase');
            MockTBSessao = jasmine.createSpy('MockTBSessao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TBProjeto': MockTBProjeto,
                'TBUsuario': MockTBUsuario,
                'TBBase': MockTBBase,
                'TBSessao': MockTBSessao
            };
            createController = function() {
                $injector.get('$controller')("TBProjetoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projeto1App:tBProjetoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
