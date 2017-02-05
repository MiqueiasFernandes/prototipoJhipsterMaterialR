(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBProjetoDialogController', TBProjetoDialogController);

    TBProjetoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TBProjeto', 'TBUsuario', 'TBBase', 'TBSessao'];

    function TBProjetoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TBProjeto, TBUsuario, TBBase, TBSessao) {
        var vm = this;

        vm.tBProjeto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbusuarios = TBUsuario.query();
        vm.tbbases = TBBase.query();
        vm.tbsessaos = TBSessao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tBProjeto.id !== null) {
                TBProjeto.update(vm.tBProjeto, onSaveSuccess, onSaveError);
            } else {
                TBProjeto.save(vm.tBProjeto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projeto1App:tBProjetoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
