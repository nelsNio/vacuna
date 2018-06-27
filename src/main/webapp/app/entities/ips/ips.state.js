(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ips', {
            parent: 'entity',
            url: '/ips',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ips'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ips/ips.html',
                    controller: 'IpsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ips-detail', {
            parent: 'ips',
            url: '/ips/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ips'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ips/ips-detail.html',
                    controller: 'IpsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ips', function($stateParams, Ips) {
                    return Ips.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ips',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ips-detail.edit', {
            parent: 'ips-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ips/ips-dialog.html',
                    controller: 'IpsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ips', function(Ips) {
                            return Ips.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ips.new', {
            parent: 'ips',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ips/ips-dialog.html',
                    controller: 'IpsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                direccion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ips', null, { reload: 'ips' });
                }, function() {
                    $state.go('ips');
                });
            }]
        })
        .state('ips.edit', {
            parent: 'ips',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ips/ips-dialog.html',
                    controller: 'IpsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ips', function(Ips) {
                            return Ips.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ips', null, { reload: 'ips' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ips.delete', {
            parent: 'ips',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ips/ips-delete-dialog.html',
                    controller: 'IpsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ips', function(Ips) {
                            return Ips.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ips', null, { reload: 'ips' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
