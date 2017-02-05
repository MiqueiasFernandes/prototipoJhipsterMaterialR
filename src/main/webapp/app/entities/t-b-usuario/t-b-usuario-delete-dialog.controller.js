(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBUsuarioDeleteController',TBUsuarioDeleteController);

    TBUsuarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'TBUsuario'];

    function TBUsuarioDeleteController($uibModalInstance, entity, TBUsuario) {
        var vm = this;

        vm.tBUsuario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TBUsuario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
