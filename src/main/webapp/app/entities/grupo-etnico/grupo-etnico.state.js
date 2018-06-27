(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('grupo-etnico', {
            parent: 'entity',
            url: '/grupo-etnico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GrupoEtnicos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grupo-etnico/grupo-etnicos.html',
                    controller: 'GrupoEtnicoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('grupo-etnico-detail', {
            parent: 'grupo-etnico',
            url: '/grupo-etnico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GrupoEtnico'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/grupo-etnico/grupo-etnico-detail.html',
                    controller: 'GrupoEtnicoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GrupoEtnico', function($stateParams, GrupoEtnico) {
                    return GrupoEtnico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'grupo-etnico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('grupo-etnico-detail.edit', {
            parent: 'grupo-etnico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo-etnico/grupo-etnico-dialog.html',
                    controller: 'GrupoEtnicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GrupoEtnico', function(GrupoEtnico) {
                            return GrupoEtnico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grupo-etnico.new', {
            parent: 'grupo-etnico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo-etnico/grupo-etnico-dialog.html',
                    controller: 'GrupoEtnicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('grupo-etnico', null, { reload: 'grupo-etnico' });
                }, function() {
                    $state.go('grupo-etnico');
                });
            }]
        })
        .state('grupo-etnico.edit', {
            parent: 'grupo-etnico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo-etnico/grupo-etnico-dialog.html',
                    controller: 'GrupoEtnicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GrupoEtnico', function(GrupoEtnico) {
                            return GrupoEtnico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grupo-etnico', null, { reload: 'grupo-etnico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('grupo-etnico.delete', {
            parent: 'grupo-etnico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/grupo-etnico/grupo-etnico-delete-dialog.html',
                    controller: 'GrupoEtnicoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GrupoEtnico', function(GrupoEtnico) {
                            return GrupoEtnico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('grupo-etnico', null, { reload: 'grupo-etnico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
