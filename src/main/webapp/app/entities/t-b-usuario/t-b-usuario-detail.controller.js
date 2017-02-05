(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBUsuarioDetailController', TBUsuarioDetailController);

    TBUsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TBUsuario', 'TBProjeto', 'TBModeloGenerico', 'TBBase'];

    function TBUsuarioDetailController($scope, $rootScope, $stateParams, previousState, entity, TBUsuario, TBProjeto, TBModeloGenerico, TBBase) {
        var vm = this;

        vm.tBUsuario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projeto1App:tBUsuarioUpdate', function(event, result) {
            vm.tBUsuario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
