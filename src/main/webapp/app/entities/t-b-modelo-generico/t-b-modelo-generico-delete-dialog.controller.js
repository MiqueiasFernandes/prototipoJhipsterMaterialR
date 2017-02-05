(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBModeloGenericoDeleteController',TBModeloGenericoDeleteController);

    TBModeloGenericoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TBModeloGenerico'];

    function TBModeloGenericoDeleteController($uibModalInstance, entity, TBModeloGenerico) {
        var vm = this;

        vm.tBModeloGenerico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TBModeloGenerico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
