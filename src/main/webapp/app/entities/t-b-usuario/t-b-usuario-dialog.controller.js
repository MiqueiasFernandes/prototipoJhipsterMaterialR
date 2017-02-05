(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBUsuarioDialogController', TBUsuarioDialogController);

    TBUsuarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TBUsuario', 'TBProjeto', 'TBModeloGenerico', 'TBBase'];

    function TBUsuarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TBUsuario, TBProjeto, TBModeloGenerico, TBBase) {
        var vm = this;

        vm.tBUsuario = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbprojetos = TBProjeto.query();
        vm.tbmodelogenericos = TBModeloGenerico.query();
        vm.tbbases = TBBase.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tBUsuario.id !== null) {
                TBUsuario.update(vm.tBUsuario, onSaveSuccess, onSaveError);
            } else {
                TBUsuario.save(vm.tBUsuario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projeto1App:tBUsuarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
