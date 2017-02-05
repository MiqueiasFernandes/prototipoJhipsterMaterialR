(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBSessaoDialogController', TBSessaoDialogController);

    TBSessaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TBSessao', 'TBModeloExclusivo', 'TBProjeto'];

    function TBSessaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TBSessao, TBModeloExclusivo, TBProjeto) {
        var vm = this;

        vm.tBSessao = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbmodeloexclusivos = TBModeloExclusivo.query();
        vm.tbprojetos = TBProjeto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tBSessao.id !== null) {
                TBSessao.update(vm.tBSessao, onSaveSuccess, onSaveError);
            } else {
                TBSessao.save(vm.tBSessao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projeto1App:tBSessaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
