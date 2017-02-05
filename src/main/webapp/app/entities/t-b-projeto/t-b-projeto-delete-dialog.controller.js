(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBProjetoDeleteController',TBProjetoDeleteController);

    TBProjetoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TBProjeto'];

    function TBProjetoDeleteController($uibModalInstance, entity, TBProjeto) {
        var vm = this;

        vm.tBProjeto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TBProjeto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
