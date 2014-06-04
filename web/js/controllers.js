(function(){'use strict';

    var schoolControllers = angular.module('schoolControllers', []);

    var getPersonName = function(personId, staff){
        var person = $.grep(staff, function(value){
            return value.id == personId;
        })[0];

        if(!person)
            return personId;

        return person.name;
    };

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
                
                $scope.isInRole = function(role){
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
            , '$window'
            , '$location'
            , 'groups'
            , 'staff'
            , '$routeParams'
            , function($scope, $window, $location, groups, staff, $routeParams){
                $scope.staff = staff.query();
                $scope.groups = groups.query();
                
                $scope.isTabActive = function(tab){
                    if($routeParams.activeTab)
                        return $routeParams.activeTab === tab;
                    return tab === 'groups';
                };
                
                $scope.getTeacherName = function(teacherId){
                    return getPersonName(teacherId, $scope.staff);
                };
                
                $scope.getPositionName = function(role){
                    return $window.$.grep($window.driftMan.positions, function(value){
                        return value.value === role;
                    })[0].text;
                };
                
                $scope.addEmployee = function(){
                    $location.path('/addEmployee');
                };
                
                $scope.addGroup = function(){
                    $location.path('/addGroup');
                };
            }
        ]
    );
    
    schoolControllers.controller(
        'AddEmployeeCtrl'
        , [
            '$scope'
            , '$window'
            , '$location'
            , 'staff'
            , function($scope, $window, $location, staff){
                $scope.positions = $window.driftMan.positions;
                $scope.person = {
                    role: $scope.positions[1].value //Teacher
                };
                $scope.$window = $window;
                
                $scope.save = function(){
                    staff.save(
                        $scope.person
                        , function(){
                            $location.path('/adminHome').search('activeTab', 'staff').replace();
                        }
                    );
                }
            }
        ]
    );
    
    schoolControllers.controller(
        'AddGroupCtrl'
        , [
            '$scope'
            , '$window'
            , '$location'
            , 'groups'
            , 'staff'
            , function($scope, $window, $location, groups, staff){
                $scope.group = { teacherId: null };
                $scope.teachers = staff.query({role:$window.driftMan.roles.Teacher});
                
                $scope.save = function(){
                    console.log($scope.group);
                    groups.save(
                        $scope.group
                        , function(data){
                            $location.path('/editGroup/' + data.id).replace();
                        }
                    );
                }
            }
        ]
    );
    
    schoolControllers.controller(
        'EditGroupCtrl'
        , [
            '$scope'
            , '$window'
            , '$location'
            , '$routeParams'
            , 'groups'
            , 'staff'
            , function($scope, $window, $location, $routeParams, groups, staff){
                groups.query(function(data){
                    angular.forEach(data, function(value){
                        if(value.id == $routeParams.groupId)
                            $scope.group = value;
                    });
                });
                $scope.teachers = staff.query({role:$window.driftMan.roles.Teacher});
                $scope.students = staff.query({role:$window.driftMan.roles.Student, groupId:$routeParams.groupId});
                
                $scope.getTeacherName = function(teacherId){
                    return getPersonName(teacherId, $scope.teachers);
                };
                
                $scope.addStudent = function(){
                    $location.path('/addStudent/' + $routeParams.groupId)
                }
            }
        ]
    );
    
    schoolControllers.controller(
        'AddStudentCtrl'
        , [
            '$scope'
            , '$window'
            , '$location'
            , '$routeParams'
            , 'groups'
            , 'staff'
            , function($scope, $window, $location, $routeParams, groups, staff){
                $scope.$window = $window;
                $scope.person = {
                    role: $window.driftMan.roles.Student
                };
                
                groups.query(function(data){
                    angular.forEach(data, function(value){
                        if(value.id == $routeParams.groupId)
                            $scope.group = value;
                    });
                });
                
                $scope.save = function(){
                    staff.save(
                        { groupId: $routeParams.groupId }
                        , $scope.person
                        , function(){
                            $location.path('/editGroup/' + $routeParams.groupId).replace();
                        }
                    );
                }
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