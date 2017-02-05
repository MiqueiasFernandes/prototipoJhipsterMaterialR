(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBModeloGenericoDetailController', TBModeloGenericoDetailController);

    TBModeloGenericoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TBModeloGenerico', 'TBUsuario'];

    function TBModeloGenericoDetailController($scope, $rootScope, $stateParams, previousState, entity, TBModeloGenerico, TBUsuario) {
        var vm = this;

        vm.tBModeloGenerico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projeto1App:tBModeloGenericoUpdate', function(event, result) {
            vm.tBModeloGenerico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
