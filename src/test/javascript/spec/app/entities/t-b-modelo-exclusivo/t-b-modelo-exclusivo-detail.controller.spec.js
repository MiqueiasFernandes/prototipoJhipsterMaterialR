'use strict';

describe('Controller Tests', function() {

    describe('TBModeloExclusivo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTBModeloExclusivo, MockTBSessao, MockTBModeloGenerico;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTBModeloExclusivo = jasmine.createSpy('MockTBModeloExclusivo');
            MockTBSessao = jasmine.createSpy('MockTBSessao');
            MockTBModeloGenerico = jasmine.createSpy('MockTBModeloGenerico');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TBModeloExclusivo': MockTBModeloExclusivo,
                'TBSessao': MockTBSessao,
                'TBModeloGenerico': MockTBModeloGenerico
            };
            createController = function() {
                $injector.get('$controller')("TBModeloExclusivoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projeto1App:tBModeloExclusivoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
