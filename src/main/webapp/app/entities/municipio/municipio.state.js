(function() {
    'use strict';

    angular
        .module('vacunasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('municipio', {
            parent: 'entity',
            url: '/municipio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Municipios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/municipio/municipios.html',
                    controller: 'MunicipioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('municipio-detail', {
            parent: 'municipio',
            url: '/municipio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Municipio'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/municipio/municipio-detail.html',
                    controller: 'MunicipioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Municipio', function($stateParams, Municipio) {
                    return Municipio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'municipio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('municipio-detail.edit', {
            parent: 'municipio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipio/municipio-dialog.html',
                    controller: 'MunicipioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Municipio', function(Municipio) {
                            return Municipio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('municipio.new', {
            parent: 'municipio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipio/municipio-dialog.html',
                    controller: 'MunicipioDialogController',
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
                    $state.go('municipio', null, { reload: 'municipio' });
                }, function() {
                    $state.go('municipio');
                });
            }]
        })
        .state('municipio.edit', {
            parent: 'municipio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipio/municipio-dialog.html',
                    controller: 'MunicipioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Municipio', function(Municipio) {
                            return Municipio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('municipio', null, { reload: 'municipio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('municipio.delete', {
            parent: 'municipio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipio/municipio-delete-dialog.html',
                    controller: 'MunicipioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Municipio', function(Municipio) {
                            return Municipio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('municipio', null, { reload: 'municipio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
