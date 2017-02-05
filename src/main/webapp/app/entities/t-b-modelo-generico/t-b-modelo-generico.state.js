(function() {
    'use strict';

    angular
        .module('projeto1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-b-modelo-generico', {
            parent: 'entity',
            url: '/t-b-modelo-generico?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBModeloGenerico.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-modelo-generico/t-b-modelo-genericos.html',
                    controller: 'TBModeloGenericoController',
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
                    $translatePartialLoader.addPart('tBModeloGenerico');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-b-modelo-generico-detail', {
            parent: 't-b-modelo-generico',
            url: '/t-b-modelo-generico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBModeloGenerico.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-modelo-generico/t-b-modelo-generico-detail.html',
                    controller: 'TBModeloGenericoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBModeloGenerico');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TBModeloGenerico', function($stateParams, TBModeloGenerico) {
                    return TBModeloGenerico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-b-modelo-generico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-b-modelo-generico-detail.edit', {
            parent: 't-b-modelo-generico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-generico/t-b-modelo-generico-dialog.html',
                    controller: 'TBModeloGenericoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBModeloGenerico', function(TBModeloGenerico) {
                            return TBModeloGenerico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-modelo-generico.new', {
            parent: 't-b-modelo-generico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-generico/t-b-modelo-generico-dialog.html',
                    controller: 'TBModeloGenericoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idmodelogenerico: null,
                                idusuario: null,
                                script: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-b-modelo-generico', null, { reload: 't-b-modelo-generico' });
                }, function() {
                    $state.go('t-b-modelo-generico');
                });
            }]
        })
        .state('t-b-modelo-generico.edit', {
            parent: 't-b-modelo-generico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-generico/t-b-modelo-generico-dialog.html',
                    controller: 'TBModeloGenericoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBModeloGenerico', function(TBModeloGenerico) {
                            return TBModeloGenerico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-modelo-generico', null, { reload: 't-b-modelo-generico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-modelo-generico.delete', {
            parent: 't-b-modelo-generico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-generico/t-b-modelo-generico-delete-dialog.html',
                    controller: 'TBModeloGenericoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TBModeloGenerico', function(TBModeloGenerico) {
                            return TBModeloGenerico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-modelo-generico', null, { reload: 't-b-modelo-generico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
