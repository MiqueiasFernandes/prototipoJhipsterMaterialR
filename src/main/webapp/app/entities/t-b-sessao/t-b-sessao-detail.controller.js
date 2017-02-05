(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBSessaoDetailController', TBSessaoDetailController);

    TBSessaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TBSessao', 'TBModeloExclusivo', 'TBProjeto'];

    function TBSessaoDetailController($scope, $rootScope, $stateParams, previousState, entity, TBSessao, TBModeloExclusivo, TBProjeto) {
        var vm = this;

        vm.tBSessao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projeto1App:tBSessaoUpdate', function(event, result) {
            vm.tBSessao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
