(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aseguradora', {
            parent: 'entity',
            url: '/aseguradora',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Aseguradoras'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aseguradora/aseguradoras.html',
                    controller: 'AseguradoraController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('aseguradora-detail', {
            parent: 'aseguradora',
            url: '/aseguradora/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Aseguradora'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aseguradora/aseguradora-detail.html',
                    controller: 'AseguradoraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Aseguradora', function($stateParams, Aseguradora) {
                    return Aseguradora.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'aseguradora',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('aseguradora-detail.edit', {
            parent: 'aseguradora-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aseguradora/aseguradora-dialog.html',
                    controller: 'AseguradoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aseguradora', function(Aseguradora) {
                            return Aseguradora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aseguradora.new', {
            parent: 'aseguradora',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aseguradora/aseguradora-dialog.html',
                    controller: 'AseguradoraDialogController',
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
                    $state.go('aseguradora', null, { reload: 'aseguradora' });
                }, function() {
                    $state.go('aseguradora');
                });
            }]
        })
        .state('aseguradora.edit', {
            parent: 'aseguradora',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aseguradora/aseguradora-dialog.html',
                    controller: 'AseguradoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aseguradora', function(Aseguradora) {
                            return Aseguradora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aseguradora', null, { reload: 'aseguradora' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aseguradora.delete', {
            parent: 'aseguradora',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aseguradora/aseguradora-delete-dialog.html',
                    controller: 'AseguradoraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aseguradora', function(Aseguradora) {
                            return Aseguradora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aseguradora', null, { reload: 'aseguradora' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
