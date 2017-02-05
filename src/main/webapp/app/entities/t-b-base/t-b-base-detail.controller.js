(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBBaseDetailController', TBBaseDetailController);

    TBBaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TBBase', 'TBUsuario', 'TBProjeto'];

    function TBBaseDetailController($scope, $rootScope, $stateParams, previousState, entity, TBBase, TBUsuario, TBProjeto) {
        var vm = this;

        vm.tBBase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projeto1App:tBBaseUpdate', function(event, result) {
            vm.tBBase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
