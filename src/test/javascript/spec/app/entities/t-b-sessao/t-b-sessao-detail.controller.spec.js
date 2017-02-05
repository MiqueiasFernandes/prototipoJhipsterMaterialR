'use strict';

describe('Controller Tests', function() {

    describe('TBSessao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTBSessao, MockTBModeloExclusivo, MockTBProjeto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTBSessao = jasmine.createSpy('MockTBSessao');
            MockTBModeloExclusivo = jasmine.createSpy('MockTBModeloExclusivo');
            MockTBProjeto = jasmine.createSpy('MockTBProjeto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TBSessao': MockTBSessao,
                'TBModeloExclusivo': MockTBModeloExclusivo,
                'TBProjeto': MockTBProjeto
            };
            createController = function() {
                $injector.get('$controller')("TBSessaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projeto1App:tBSessaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
