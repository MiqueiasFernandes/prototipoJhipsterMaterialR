'use strict';

describe('Controller Tests', function() {

    describe('TBModeloGenerico Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTBModeloGenerico, MockTBUsuario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTBModeloGenerico = jasmine.createSpy('MockTBModeloGenerico');
            MockTBUsuario = jasmine.createSpy('MockTBUsuario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TBModeloGenerico': MockTBModeloGenerico,
                'TBUsuario': MockTBUsuario
            };
            createController = function() {
                $injector.get('$controller')("TBModeloGenericoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projeto1App:tBModeloGenericoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
