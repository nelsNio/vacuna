(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-documento', {
            parent: 'entity',
            url: '/tipo-documento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoDocumentos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-documento/tipo-documentos.html',
                    controller: 'TipoDocumentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tipo-documento-detail', {
            parent: 'tipo-documento',
            url: '/tipo-documento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoDocumento'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-documento/tipo-documento-detail.html',
                    controller: 'TipoDocumentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TipoDocumento', function($stateParams, TipoDocumento) {
                    return TipoDocumento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-documento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-documento-detail.edit', {
            parent: 'tipo-documento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-documento/tipo-documento-dialog.html',
                    controller: 'TipoDocumentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoDocumento', function(TipoDocumento) {
                            return TipoDocumento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-documento.new', {
            parent: 'tipo-documento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-documento/tipo-documento-dialog.html',
                    controller: 'TipoDocumentoDialogController',
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
                    $state.go('tipo-documento', null, { reload: 'tipo-documento' });
                }, function() {
                    $state.go('tipo-documento');
                });
            }]
        })
        .state('tipo-documento.edit', {
            parent: 'tipo-documento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-documento/tipo-documento-dialog.html',
                    controller: 'TipoDocumentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoDocumento', function(TipoDocumento) {
                            return TipoDocumento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-documento', null, { reload: 'tipo-documento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-documento.delete', {
            parent: 'tipo-documento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-documento/tipo-documento-delete-dialog.html',
                    controller: 'TipoDocumentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoDocumento', function(TipoDocumento) {
                            return TipoDocumento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-documento', null, { reload: 'tipo-documento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
