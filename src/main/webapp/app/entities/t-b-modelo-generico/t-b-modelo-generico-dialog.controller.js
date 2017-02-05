(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBModeloGenericoDialogController', TBModeloGenericoDialogController);

    TBModeloGenericoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TBModeloGenerico', 'TBUsuario'];

    function TBModeloGenericoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TBModeloGenerico, TBUsuario) {
        var vm = this;

        vm.tBModeloGenerico = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbusuarios = TBUsuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tBModeloGenerico.id !== null) {
                TBModeloGenerico.update(vm.tBModeloGenerico, onSaveSuccess, onSaveError);
            } else {
                TBModeloGenerico.save(vm.tBModeloGenerico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projeto1App:tBModeloGenericoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
