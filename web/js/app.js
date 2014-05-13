(function(){'use strict';
    
    /* App Module */
    var school = angular.module('school', ['ngRoute', 'schoolControllers', 'schoolFactories']);

    school.config([
        '$routeProvider'
        , function($routeProvider) {
            $routeProvider
                .when(
                    '/home'
                    , {
                        templateUrl: 'partials/home.html'
                        , controller: 'HomeCtrl'
                    }
                )
                .when(
                    '/logon'
                    , {
                        templateUrl: 'partials/logon.html'
                        , controller: 'LogonCtrl'
                    }
                )
                .otherwise({
                    redirectTo: '/logon'
                });
        }
    ]);
    
    school.run([
        '$rootScope'
        , '$location'
        , 'currentUser'
        , function($rootScope, $location, currentUser){
            $rootScope.$on(
                '$routeChangeStart'
                , function(){
                    if(!currentUser.isAuthorized && $location.$$path && $location.$$path !== '/logon')
                        $location.path('/logon');
                })
        }
    ]);
    
})();