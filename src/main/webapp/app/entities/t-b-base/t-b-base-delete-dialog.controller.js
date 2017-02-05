(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBBaseDeleteController',TBBaseDeleteController);

    TBBaseDeleteController.$inject = ['$uibModalInstance', 'entity', 'TBBase'];

    function TBBaseDeleteController($uibModalInstance, entity, TBBase) {
        var vm = this;

        vm.tBBase = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TBBase.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
