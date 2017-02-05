(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBProjetoDetailController', TBProjetoDetailController);

    TBProjetoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TBProjeto', 'TBUsuario', 'TBBase', 'TBSessao'];

    function TBProjetoDetailController($scope, $rootScope, $stateParams, previousState, entity, TBProjeto, TBUsuario, TBBase, TBSessao) {
        var vm = this;

        vm.tBProjeto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projeto1App:tBProjetoUpdate', function(event, result) {
            vm.tBProjeto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
