(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBSessaoDeleteController',TBSessaoDeleteController);

    TBSessaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TBSessao'];

    function TBSessaoDeleteController($uibModalInstance, entity, TBSessao) {
        var vm = this;

        vm.tBSessao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TBSessao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
