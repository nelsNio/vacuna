(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aplicacion-vacuna', {
            parent: 'entity',
            url: '/aplicacion-vacuna',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AplicacionVacunas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aplicacion-vacuna/aplicacion-vacunas.html',
                    controller: 'AplicacionVacunaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('aplicacion-vacuna-detail', {
            parent: 'aplicacion-vacuna',
            url: '/aplicacion-vacuna/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AplicacionVacuna'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aplicacion-vacuna/aplicacion-vacuna-detail.html',
                    controller: 'AplicacionVacunaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AplicacionVacuna', function($stateParams, AplicacionVacuna) {
                    return AplicacionVacuna.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'aplicacion-vacuna',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('aplicacion-vacuna-detail.edit', {
            parent: 'aplicacion-vacuna-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion-vacuna/aplicacion-vacuna-dialog.html',
                    controller: 'AplicacionVacunaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AplicacionVacuna', function(AplicacionVacuna) {
                            return AplicacionVacuna.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aplicacion-vacuna.new', {
            parent: 'aplicacion-vacuna',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion-vacuna/aplicacion-vacuna-dialog.html',
                    controller: 'AplicacionVacunaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('aplicacion-vacuna', null, { reload: 'aplicacion-vacuna' });
                }, function() {
                    $state.go('aplicacion-vacuna');
                });
            }]
        })
        .state('aplicacion-vacuna.edit', {
            parent: 'aplicacion-vacuna',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion-vacuna/aplicacion-vacuna-dialog.html',
                    controller: 'AplicacionVacunaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AplicacionVacuna', function(AplicacionVacuna) {
                            return AplicacionVacuna.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aplicacion-vacuna', null, { reload: 'aplicacion-vacuna' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aplicacion-vacuna.delete', {
            parent: 'aplicacion-vacuna',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aplicacion-vacuna/aplicacion-vacuna-delete-dialog.html',
                    controller: 'AplicacionVacunaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AplicacionVacuna', function(AplicacionVacuna) {
                            return AplicacionVacuna.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aplicacion-vacuna', null, { reload: 'aplicacion-vacuna' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
