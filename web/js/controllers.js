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
                $scope.credentials = { email: 'student@driftman.ru', password: 'student123' };
                
                var credentials = {
                    currentCredIndex: 0
                    , credArray: [
                        { email: 'student@driftman.ru', password: 'student123' }
                        , { email: 'teacher@driftman.ru', password: 'teacher123' }
                        , { email: 'admin@driftman.ru', password: 'admin123' }
                    ]
                    , nextCredentials: function(){
                        if(++this.currentCredIndex >= this.credArray.length)
                            this.currentCredIndex = 0;
                        return this.credArray[this.currentCredIndex];
                    }
                }
                
                $scope.changeCredentials = function(e){
                    if(e.shiftKey && e.keyCode === 38){
                        e.preventDefault();
                        $scope.credentials = credentials.nextCredentials();
                    }
                }
                
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