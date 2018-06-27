(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dosis', {
            parent: 'entity',
            url: '/dosis',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Doses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dosis/doses.html',
                    controller: 'DosisController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('dosis-detail', {
            parent: 'dosis',
            url: '/dosis/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Dosis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dosis/dosis-detail.html',
                    controller: 'DosisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Dosis', function($stateParams, Dosis) {
                    return Dosis.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dosis',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dosis-detail.edit', {
            parent: 'dosis-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosis/dosis-dialog.html',
                    controller: 'DosisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dosis', function(Dosis) {
                            return Dosis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dosis.new', {
            parent: 'dosis',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosis/dosis-dialog.html',
                    controller: 'DosisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                edadMinima: null,
                                edadMaxima: null,
                                embarazo: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dosis', null, { reload: 'dosis' });
                }, function() {
                    $state.go('dosis');
                });
            }]
        })
        .state('dosis.edit', {
            parent: 'dosis',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosis/dosis-dialog.html',
                    controller: 'DosisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dosis', function(Dosis) {
                            return Dosis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dosis', null, { reload: 'dosis' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dosis.delete', {
            parent: 'dosis',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dosis/dosis-delete-dialog.html',
                    controller: 'DosisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dosis', function(Dosis) {
                            return Dosis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dosis', null, { reload: 'dosis' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
