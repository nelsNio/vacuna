(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('acudiente', {
            parent: 'entity',
            url: '/acudiente',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Acudientes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acudiente/acudientes.html',
                    controller: 'AcudienteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('acudiente-detail', {
            parent: 'acudiente',
            url: '/acudiente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Acudiente'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acudiente/acudiente-detail.html',
                    controller: 'AcudienteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Acudiente', function($stateParams, Acudiente) {
                    return Acudiente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'acudiente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('acudiente-detail.edit', {
            parent: 'acudiente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acudiente/acudiente-dialog.html',
                    controller: 'AcudienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acudiente', function(Acudiente) {
                            return Acudiente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acudiente.new', {
            parent: 'acudiente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acudiente/acudiente-dialog.html',
                    controller: 'AcudienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                documento: null,
                                nombre: null,
                                apellido: null,
                                fechaNacimiento: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('acudiente', null, { reload: 'acudiente' });
                }, function() {
                    $state.go('acudiente');
                });
            }]
        })
        .state('acudiente.edit', {
            parent: 'acudiente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acudiente/acudiente-dialog.html',
                    controller: 'AcudienteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acudiente', function(Acudiente) {
                            return Acudiente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('acudiente', null, { reload: 'acudiente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acudiente.delete', {
            parent: 'acudiente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acudiente/acudiente-delete-dialog.html',
                    controller: 'AcudienteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Acudiente', function(Acudiente) {
                            return Acudiente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('acudiente', null, { reload: 'acudiente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
