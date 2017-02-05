(function() {
    'use strict';

    angular
        .module('projeto1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-b-base', {
            parent: 'entity',
            url: '/t-b-base?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBBase.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-base/t-b-bases.html',
                    controller: 'TBBaseController',
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
                    $translatePartialLoader.addPart('tBBase');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-b-base-detail', {
            parent: 't-b-base',
            url: '/t-b-base/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'projeto1App.tBBase.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-b-base/t-b-base-detail.html',
                    controller: 'TBBaseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tBBase');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TBBase', function($stateParams, TBBase) {
                    return TBBase.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-b-base',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-b-base-detail.edit', {
            parent: 't-b-base-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-base/t-b-base-dialog.html',
                    controller: 'TBBaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBBase', function(TBBase) {
                            return TBBase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-base.new', {
            parent: 't-b-base',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-base/t-b-base-dialog.html',
                    controller: 'TBBaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idbase: null,
                                idusuario: null,
                                url: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-b-base', null, { reload: 't-b-base' });
                }, function() {
                    $state.go('t-b-base');
                });
            }]
        })
        .state('t-b-base.edit', {
            parent: 't-b-base',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-base/t-b-base-dialog.html',
                    controller: 'TBBaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TBBase', function(TBBase) {
                            return TBBase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-base', null, { reload: 't-b-base' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-b-base.delete', {
            parent: 't-b-base',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-b-base/t-b-base-delete-dialog.html',
                    controller: 'TBBaseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TBBase', function(TBBase) {
                            return TBBase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-b-base', null, { reload: 't-b-base' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
