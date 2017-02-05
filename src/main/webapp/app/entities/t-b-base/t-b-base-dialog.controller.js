(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBBaseDialogController', TBBaseDialogController);

    TBBaseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TBBase', 'TBUsuario', 'TBProjeto'];

    function TBBaseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TBBase, TBUsuario, TBProjeto) {
        var vm = this;

        vm.tBBase = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbusuarios = TBUsuario.query();
        vm.tbprojetos = TBProjeto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tBBase.id !== null) {
                TBBase.update(vm.tBBase, onSaveSuccess, onSaveError);
            } else {
                TBBase.save(vm.tBBase, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projeto1App:tBBaseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
