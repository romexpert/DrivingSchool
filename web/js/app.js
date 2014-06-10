(function(){'use strict';
    
    /* App Module */
    var school = angular.module('school', ['ngRoute', 'schoolControllers', 'schoolFactories']);

    school.config([
        '$routeProvider'
        , '$httpProvider'
        , function($routeProvider, $httpProvider) {
            $routeProvider
                .when(
                    '/studentHome'
                    , {
                        templateUrl: 'partials/studentHome.html'
                        , controller: 'StudentHomeCtrl'
                    }
                )
                .when(
                    '/lecture:number'
                    , {
                        templateUrl: function(params){
                            return 'partials/lectures/lecture' + params.number + '.html';
                        }
                    }
                )
                .when(
                    '/instructorHome'
                    , {
                        templateUrl: 'partials/instructorHome.html'
                        , controller: 'InstructorHomeCtrl'
                    }
                )
                .when(
                    '/adminHome'
                    , {
                        templateUrl: 'partials/adminHome.html'
                        , controller: 'AdminHomeCtrl'
                    }
                )
                .when(
                    '/addEmployee'
                    , {
                        templateUrl: 'partials/addPerson.html'
                        , controller: 'AddEmployeeCtrl'
                    }
                )
                .when(
                    '/addStudent/:groupId'
                    , {
                        templateUrl: 'partials/addStudent.html'
                        , controller: 'AddStudentCtrl'
                    }
                )
                .when(
                    '/addGroup'
                    , {
                        templateUrl: 'partials/addGroup.html'
                        , controller: 'AddGroupCtrl'
                    }
                )
                .when(
                    '/editGroup/:groupId'
                    , {
                        templateUrl: 'partials/editGroup.html'
                        , controller: 'EditGroupCtrl'
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
            
            $httpProvider.responseInterceptors.push(['$q', '$location', 'currentUser', function($q, $location, currentUser) {
		return function(promise) {
                    return promise.catch(function(response) {
                            if (response.status === 403) {
                                currentUser.reset();
                                $location.path('/logon');
                            }
                            return $q.reject(response);
                    });
		}
            }]);
        }
    ]);
    
    school.run([
        '$rootScope'
        , '$location'
        , '$window'
        , 'currentUser'
        , function($rootScope, $location, $window, currentUser){
            $rootScope.$on(
                '$routeChangeStart'
                , function(){
                    if(!currentUser.isAuthorized && $location.$$path && $location.$$path !== '/logon'){
                        try{
                            var user = angular.fromJson($window.localStorage.getItem('currentUser'));
                            if(user && user.isAuthorized){
                                currentUser.set(user.name, user.role);
                                return;
                            }
                        }
                        catch(err){ }

                        $location.path('/logon');
                    }
                }
            );
        }
    ]);
    
})();