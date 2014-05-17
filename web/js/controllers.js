(function(){'use strict';

    var schoolControllers = angular.module('schoolControllers', []);

    schoolControllers.controller(
        'AppCtrl'
        , [
            '$scope'
            , '$location'
            , 'currentUser'
            , 'errorFactory'
            , function($scope, $location, currentUser, errorFactory) {
                $scope.currentUser = currentUser;
                $scope.errorFactory = errorFactory;
                
                $scope.logout = function(){
                    currentUser.reset();
                    $location.path('/logon')
                }
            }
        ]
    );
    
    schoolControllers.controller(
        'HomeCtrl'
        , [
            '$scope'
            , '$location'
            , 'lectures'
            , 'exercises'
            , function($scope, $location, lectures, exercises) {
                $scope.lectures = lectures.query();
                $scope.exercises = exercises.query();
                $scope.readLecture = function(number){
                    $location.path('/lecture' + number);
                }
            }
        ]
    );
    
    schoolControllers.controller(
        'LogonCtrl'
        , [
            '$scope'
            , '$location'
            , 'logon'
            , 'currentUser'
            , 'errorFactory'
            , function($scope, $location, logon, currentUser, errorFactory) {
                $scope.credentials = { email: 'ex@ex.ru', password: 'ex@ex.ru' };
                
                $scope.login = function(){
                    logon
                        .save($scope.credentials)
                        .$promise
                        .then(function(data){
                            //TODO: form server
                            currentUser.set(data.name, data.role);
                            $location.path('home');
                        })
                        .catch(function(error){
                            errorFactory.addError(
                                error.status === 403 ? 'Неправильный e-mail или пароль.' : error.statusText
                            );
                        });
                };
            }
        ]
    );
    
})();