(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('TBModeloExclusivoDialogController', TBModeloExclusivoDialogController);

    TBModeloExclusivoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TBModeloExclusivo', 'TBSessao', 'TBModeloGenerico'];

    function TBModeloExclusivoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TBModeloExclusivo, TBSessao, TBModeloGenerico) {
        var vm = this;

        vm.tBModeloExclusivo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tbsessaos = TBSessao.query();
        vm.mapeias = TBModeloGenerico.query({filter: 'tbmodeloexclusivo-is-null'});
        $q.all([vm.tBModeloExclusivo.$promise, vm.mapeias.$promise]).then(function() {
            if (!vm.tBModeloExclusivo.mapeia || !vm.tBModeloExclusivo.mapeia.id) {
                return $q.reject();
            }
            return TBModeloGenerico.get({id : vm.tBModeloExclusivo.mapeia.id}).$promise;
        }).then(function(mapeia) {
            vm.mapeias.push(mapeia);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tBModeloExclusivo.id !== null) {
                TBModeloExclusivo.update(vm.tBModeloExclusivo, onSaveSuccess, onSaveError);
            } else {
                TBModeloExclusivo.save(vm.tBModeloExclusivo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projeto1App:tBModeloExclusivoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
