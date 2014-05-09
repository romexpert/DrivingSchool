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
                })
        }
    ]);
    
})();