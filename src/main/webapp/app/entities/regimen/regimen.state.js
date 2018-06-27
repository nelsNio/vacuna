(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('regimen', {
            parent: 'entity',
            url: '/regimen',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Regimen'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/regimen/regimen.html',
                    controller: 'RegimenController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('regimen-detail', {
            parent: 'regimen',
            url: '/regimen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Regimen'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/regimen/regimen-detail.html',
                    controller: 'RegimenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Regimen', function($stateParams, Regimen) {
                    return Regimen.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'regimen',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('regimen-detail.edit', {
            parent: 'regimen-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/regimen/regimen-dialog.html',
                    controller: 'RegimenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Regimen', function(Regimen) {
                            return Regimen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('regimen.new', {
            parent: 'regimen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/regimen/regimen-dialog.html',
                    controller: 'RegimenDialogController',
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
                    $state.go('regimen', null, { reload: 'regimen' });
                }, function() {
                    $state.go('regimen');
                });
            }]
        })
        .state('regimen.edit', {
            parent: 'regimen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/regimen/regimen-dialog.html',
                    controller: 'RegimenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Regimen', function(Regimen) {
                            return Regimen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('regimen', null, { reload: 'regimen' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('regimen.delete', {
            parent: 'regimen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/regimen/regimen-delete-dialog.html',
                    controller: 'RegimenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Regimen', function(Regimen) {
                            return Regimen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('regimen', null, { reload: 'regimen' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
