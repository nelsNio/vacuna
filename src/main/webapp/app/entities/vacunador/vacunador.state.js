(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vacunador', {
            parent: 'entity',
            url: '/vacunador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vacunadors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vacunador/vacunadors.html',
                    controller: 'VacunadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('vacunador-detail', {
            parent: 'vacunador',
            url: '/vacunador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vacunador'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vacunador/vacunador-detail.html',
                    controller: 'VacunadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Vacunador', function($stateParams, Vacunador) {
                    return Vacunador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vacunador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vacunador-detail.edit', {
            parent: 'vacunador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vacunador/vacunador-dialog.html',
                    controller: 'VacunadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vacunador', function(Vacunador) {
                            return Vacunador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vacunador.new', {
            parent: 'vacunador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vacunador/vacunador-dialog.html',
                    controller: 'VacunadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                documento: null,
                                nombre: null,
                                apellido: null,
                                telefono: null,
                                correo: null,
                                fechaNacimiento: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vacunador', null, { reload: 'vacunador' });
                }, function() {
                    $state.go('vacunador');
                });
            }]
        })
        .state('vacunador.edit', {
            parent: 'vacunador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vacunador/vacunador-dialog.html',
                    controller: 'VacunadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vacunador', function(Vacunador) {
                            return Vacunador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vacunador', null, { reload: 'vacunador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vacunador.delete', {
            parent: 'vacunador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vacunador/vacunador-delete-dialog.html',
                    controller: 'VacunadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vacunador', function(Vacunador) {
                            return Vacunador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vacunador', null, { reload: 'vacunador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
