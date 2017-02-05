(function() {
    'use strict';

    angular
        .module('projeto1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-b-usuario', {
            parent: 'entity',
            url: '/t-b-usuario',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBUsuario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-usuario/t-b-usuarios.html',
                    controller: 'TBUsuarioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBUsuario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-b-usuario-detail', {
            parent: 't-b-usuario',
            url: '/t-b-usuario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBUsuario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-usuario/t-b-usuario-detail.html',
                    controller: 'TBUsuarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBUsuario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TBUsuario', function($stateParams, TBUsuario) {
                    return TBUsuario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-b-usuario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-b-usuario-detail.edit', {
            parent: 't-b-usuario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-usuario/t-b-usuario-dialog.html',
                    controller: 'TBUsuarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBUsuario', function(TBUsuario) {
                            return TBUsuario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-usuario.new', {
            parent: 't-b-usuario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-usuario/t-b-usuario-dialog.html',
                    controller: 'TBUsuarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idusuario: null,
                                nome: null,
                                senha: null,
                                email: null,
                                diretorio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-b-usuario', null, { reload: 't-b-usuario' });
                }, function() {
                    $state.go('t-b-usuario');
                });
            }]
        })
        .state('t-b-usuario.edit', {
            parent: 't-b-usuario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-usuario/t-b-usuario-dialog.html',
                    controller: 'TBUsuarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBUsuario', function(TBUsuario) {
                            return TBUsuario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-usuario', null, { reload: 't-b-usuario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-usuario.delete', {
            parent: 't-b-usuario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-usuario/t-b-usuario-delete-dialog.html',
                    controller: 'TBUsuarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TBUsuario', function(TBUsuario) {
                            return TBUsuario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-usuario', null, { reload: 't-b-usuario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
