(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBModeloExclusivoDeleteController',TBModeloExclusivoDeleteController);

    TBModeloExclusivoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TBModeloExclusivo'];

    function TBModeloExclusivoDeleteController($uibModalInstance, entity, TBModeloExclusivo) {
        var vm = this;

        vm.tBModeloExclusivo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TBModeloExclusivo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
