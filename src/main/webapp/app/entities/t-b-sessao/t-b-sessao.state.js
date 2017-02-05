(function() {
    'use strict';

    angular
        .module('projeto1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-b-sessao', {
            parent: 'entity',
            url: '/t-b-sessao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBSessao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-sessao/t-b-sessaos.html',
                    controller: 'TBSessaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBSessao');
                    $translatePartialLoader.addPart('tBTiposessao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-b-sessao-detail', {
            parent: 't-b-sessao',
            url: '/t-b-sessao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBSessao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-sessao/t-b-sessao-detail.html',
                    controller: 'TBSessaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBSessao');
                    $translatePartialLoader.addPart('tBTiposessao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TBSessao', function($stateParams, TBSessao) {
                    return TBSessao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-b-sessao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-b-sessao-detail.edit', {
            parent: 't-b-sessao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-sessao/t-b-sessao-dialog.html',
                    controller: 'TBSessaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBSessao', function(TBSessao) {
                            return TBSessao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-sessao.new', {
            parent: 't-b-sessao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-sessao/t-b-sessao-dialog.html',
                    controller: 'TBSessaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idsessao: null,
                                idmodeloexclusivo: null,
                                tipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-b-sessao', null, { reload: 't-b-sessao' });
                }, function() {
                    $state.go('t-b-sessao');
                });
            }]
        })
        .state('t-b-sessao.edit', {
            parent: 't-b-sessao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-sessao/t-b-sessao-dialog.html',
                    controller: 'TBSessaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBSessao', function(TBSessao) {
                            return TBSessao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-sessao', null, { reload: 't-b-sessao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-sessao.delete', {
            parent: 't-b-sessao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-sessao/t-b-sessao-delete-dialog.html',
                    controller: 'TBSessaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TBSessao', function(TBSessao) {
                            return TBSessao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-sessao', null, { reload: 't-b-sessao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
