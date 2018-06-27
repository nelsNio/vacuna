(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('biologico', {
            parent: 'entity',
            url: '/biologico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Biologicos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/biologico/biologicos.html',
                    controller: 'BiologicoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('biologico-detail', {
            parent: 'biologico',
            url: '/biologico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Biologico'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/biologico/biologico-detail.html',
                    controller: 'BiologicoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Biologico', function($stateParams, Biologico) {
                    return Biologico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'biologico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('biologico-detail.edit', {
            parent: 'biologico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biologico/biologico-dialog.html',
                    controller: 'BiologicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Biologico', function(Biologico) {
                            return Biologico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('biologico.new', {
            parent: 'biologico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biologico/biologico-dialog.html',
                    controller: 'BiologicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                lote: null,
                                loteJeringa: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('biologico', null, { reload: 'biologico' });
                }, function() {
                    $state.go('biologico');
                });
            }]
        })
        .state('biologico.edit', {
            parent: 'biologico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biologico/biologico-dialog.html',
                    controller: 'BiologicoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Biologico', function(Biologico) {
                            return Biologico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('biologico', null, { reload: 'biologico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('biologico.delete', {
            parent: 'biologico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biologico/biologico-delete-dialog.html',
                    controller: 'BiologicoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Biologico', function(Biologico) {
                            return Biologico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('biologico', null, { reload: 'biologico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
