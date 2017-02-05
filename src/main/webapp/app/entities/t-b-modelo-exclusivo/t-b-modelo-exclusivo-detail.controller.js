(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBModeloExclusivoDetailController', TBModeloExclusivoDetailController);

    TBModeloExclusivoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TBModeloExclusivo', 'TBSessao', 'TBModeloGenerico'];

    function TBModeloExclusivoDetailController($scope, $rootScope, $stateParams, previousState, entity, TBModeloExclusivo, TBSessao, TBModeloGenerico) {
        var vm = this;

        vm.tBModeloExclusivo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projeto1App:tBModeloExclusivoUpdate', function(event, result) {
            vm.tBModeloExclusivo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
