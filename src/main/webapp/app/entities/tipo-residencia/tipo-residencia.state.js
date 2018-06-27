(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-residencia', {
            parent: 'entity',
            url: '/tipo-residencia',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoResidencias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-residencia/tipo-residencias.html',
                    controller: 'TipoResidenciaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tipo-residencia-detail', {
            parent: 'tipo-residencia',
            url: '/tipo-residencia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoResidencia'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-residencia/tipo-residencia-detail.html',
                    controller: 'TipoResidenciaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TipoResidencia', function($stateParams, TipoResidencia) {
                    return TipoResidencia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-residencia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-residencia-detail.edit', {
            parent: 'tipo-residencia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-residencia/tipo-residencia-dialog.html',
                    controller: 'TipoResidenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoResidencia', function(TipoResidencia) {
                            return TipoResidencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-residencia.new', {
            parent: 'tipo-residencia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-residencia/tipo-residencia-dialog.html',
                    controller: 'TipoResidenciaDialogController',
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
                    $state.go('tipo-residencia', null, { reload: 'tipo-residencia' });
                }, function() {
                    $state.go('tipo-residencia');
                });
            }]
        })
        .state('tipo-residencia.edit', {
            parent: 'tipo-residencia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-residencia/tipo-residencia-dialog.html',
                    controller: 'TipoResidenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoResidencia', function(TipoResidencia) {
                            return TipoResidencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-residencia', null, { reload: 'tipo-residencia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-residencia.delete', {
            parent: 'tipo-residencia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-residencia/tipo-residencia-delete-dialog.html',
                    controller: 'TipoResidenciaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoResidencia', function(TipoResidencia) {
                            return TipoResidencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-residencia', null, { reload: 'tipo-residencia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
