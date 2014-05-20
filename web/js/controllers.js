(function(){'use strict';

    var schoolControllers = angular.module('schoolControllers', []);

    schoolControllers.controller(
        'AppCtrl'
        , [
            '$scope'
            , '$window'
            , '$location'
            , 'currentUser'
            , 'errorFactory'
            , function($scope, $window, $location, currentUser, errorFactory) {
                $scope.currentUser = currentUser;
                $scope.errorFactory = errorFactory;
                $scope.roles = $window.driftMan.roles;
                
                $scope.isTabActive = function(url){
                    return $location.$$path === url;
                };
                
                $scope.inRole = function(role){
                    return currentUser.role === role;
                };
                
                $scope.logout = function(){
                    currentUser.reset();
                    $location.path('/logon')
                };
            }
        ]
    );
    
    schoolControllers.controller(
        'StudentHomeCtrl'
        , [
            '$scope'
            , 'lectures'
            , 'exercises'
            , function($scope, lectures, exercises) {
                $scope.lectures = lectures.query();
                $scope.exercises = exercises.query();
            }
        ]
    );
    
    schoolControllers.controller(
        'TeacherHomeCtrl'
        , [
            '$scope'
            , function($scope){
                
            }
        ]
    );
    
    schoolControllers.controller(
        'AdminHomeCtrl'
        , [
            '$scope'
            , 'teachers'
            , function($scope, teachers){
                $scope.teachers = teachers.query();
            }
        ]
    );
    
    schoolControllers.controller(
        'LogonCtrl'
        , [
            '$scope'
            , '$window'
            , '$location'
            , 'logon'
            , 'currentUser'
            , 'errorFactory'
            , function($scope, $window, $location, logon, currentUser, errorFactory) {
                
                var credentials = {
                    currentCredIndex: 0
                    , credArray: [
                        { email: 'student@driftman.ru', password: 'student123' }
                        , { email: 'teacher@driftman.ru', password: 'teacher123' }
                        , { email: 'admin@driftman.ru', password: 'admin123' }
                    ]
                    , nextCredentials: function(index){
                        if(index)
                            this.currentCredIndex = --index;
                        if(++this.currentCredIndex >= this.credArray.length)
                            this.currentCredIndex = 0;
                        
                        $window.localStorage.setItem('credIndex', this.currentCredIndex);
                        return this.credArray[this.currentCredIndex];
                    }
                }
                
                $scope.credentials = credentials.nextCredentials($window.localStorage.getItem('credIndex'));
                
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
                            
                            var newPath = '/studentHome';
                            if(data.role === $window.driftMan.roles.Teacher)
                                newPath = '/teacherHome';
                            else if(data.role === $window.driftMan.roles.Admin)
                                newPath = '/adminHome';
                                
                            $location.path(newPath);
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