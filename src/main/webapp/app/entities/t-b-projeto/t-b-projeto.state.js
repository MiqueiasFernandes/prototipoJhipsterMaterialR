(function() {
    'use strict';

    angular
        .module('projeto1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-b-projeto', {
            parent: 'entity',
            url: '/t-b-projeto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBProjeto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-projeto/t-b-projetos.html',
                    controller: 'TBProjetoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBProjeto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-b-projeto-detail', {
            parent: 't-b-projeto',
            url: '/t-b-projeto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBProjeto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-projeto/t-b-projeto-detail.html',
                    controller: 'TBProjetoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBProjeto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TBProjeto', function($stateParams, TBProjeto) {
                    return TBProjeto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-b-projeto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-b-projeto-detail.edit', {
            parent: 't-b-projeto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-projeto/t-b-projeto-dialog.html',
                    controller: 'TBProjetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBProjeto', function(TBProjeto) {
                            return TBProjeto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-projeto.new', {
            parent: 't-b-projeto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-projeto/t-b-projeto-dialog.html',
                    controller: 'TBProjetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idprojeto: null,
                                idusuario: null,
                                idsessao: null,
                                idbase: null,
                                script: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-b-projeto', null, { reload: 't-b-projeto' });
                }, function() {
                    $state.go('t-b-projeto');
                });
            }]
        })
        .state('t-b-projeto.edit', {
            parent: 't-b-projeto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-projeto/t-b-projeto-dialog.html',
                    controller: 'TBProjetoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBProjeto', function(TBProjeto) {
                            return TBProjeto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-projeto', null, { reload: 't-b-projeto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-projeto.delete', {
            parent: 't-b-projeto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-projeto/t-b-projeto-delete-dialog.html',
                    controller: 'TBProjetoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TBProjeto', function(TBProjeto) {
                            return TBProjeto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-projeto', null, { reload: 't-b-projeto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
