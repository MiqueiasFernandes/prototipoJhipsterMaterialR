(function() {
    'use strict';

    angular
        .module('projeto1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-b-modelo-exclusivo', {
            parent: 'entity',
            url: '/t-b-modelo-exclusivo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBModeloExclusivo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-modelo-exclusivo/t-b-modelo-exclusivos.html',
                    controller: 'TBModeloExclusivoController',
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
                    $translatePartialLoader.addPart('tBModeloExclusivo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-b-modelo-exclusivo-detail', {
            parent: 't-b-modelo-exclusivo',
            url: '/t-b-modelo-exclusivo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBModeloExclusivo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-modelo-exclusivo/t-b-modelo-exclusivo-detail.html',
                    controller: 'TBModeloExclusivoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBModeloExclusivo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TBModeloExclusivo', function($stateParams, TBModeloExclusivo) {
                    return TBModeloExclusivo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-b-modelo-exclusivo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-b-modelo-exclusivo-detail.edit', {
            parent: 't-b-modelo-exclusivo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-exclusivo/t-b-modelo-exclusivo-dialog.html',
                    controller: 'TBModeloExclusivoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBModeloExclusivo', function(TBModeloExclusivo) {
                            return TBModeloExclusivo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-modelo-exclusivo.new', {
            parent: 't-b-modelo-exclusivo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-exclusivo/t-b-modelo-exclusivo-dialog.html',
                    controller: 'TBModeloExclusivoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idmodeloexclusivo: null,
                                idusuario: null,
                                idmodelogenerico: null,
                                mapeamento: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-b-modelo-exclusivo', null, { reload: 't-b-modelo-exclusivo' });
                }, function() {
                    $state.go('t-b-modelo-exclusivo');
                });
            }]
        })
        .state('t-b-modelo-exclusivo.edit', {
            parent: 't-b-modelo-exclusivo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-exclusivo/t-b-modelo-exclusivo-dialog.html',
                    controller: 'TBModeloExclusivoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBModeloExclusivo', function(TBModeloExclusivo) {
                            return TBModeloExclusivo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-modelo-exclusivo', null, { reload: 't-b-modelo-exclusivo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-modelo-exclusivo.delete', {
            parent: 't-b-modelo-exclusivo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-modelo-exclusivo/t-b-modelo-exclusivo-delete-dialog.html',
                    controller: 'TBModeloExclusivoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TBModeloExclusivo', function(TBModeloExclusivo) {
                            return TBModeloExclusivo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-modelo-exclusivo', null, { reload: 't-b-modelo-exclusivo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
