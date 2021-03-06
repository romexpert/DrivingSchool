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
            , 'currentUser'
            , 'lectures'
            , 'exercises'
            , function($scope, currentUser, lectures, exercises) {
                $scope.lectures = lectures.query();
                $scope.exercises = exercises.query({studentId: currentUser.id});
            }
        ]
    );
    
    schoolControllers.controller(
        'LectureTestCtrl'
        , [
            '$scope'
            , '$routeParams'
            , '$location'
            , 'lectures'
            , function($scope, $routeParams, $location, lectures){
                $scope.lectureNumber = $routeParams.number;
                $scope.currentQuestionIndex = 0;
                $scope.isNextDisabled = true;
                $scope.questions = lectures.query({number: $scope.lectureNumber}, function(data){
                    if(data.length)
                        $scope.currentQuestion = $scope.questions[$scope.currentQuestionIndex];
                });
                
                $scope.activateNext = function(option){
                    $scope.currentQuestion.answer = option.optionsNumber;
                    $scope.isNextDisabled = false;
                };
                $scope.nextQuestion = function(){
                    $scope.isNextDisabled = true;
                    if($scope.currentQuestionIndex + 1 < $scope.questions.length)
                        $scope.currentQuestion = $scope.questions[++$scope.currentQuestionIndex];
                    else{
                        var result = $.map($scope.questions, function(value){
                            return { id: value.id, answer: value.answer};
                        });
                        
                        lectures.save({lectureNumber: $scope.lectureNumber}, result, function(data){
                            alert(data.message);
                            $location.path('/studentHome').replace();
                        })
                    }
                };
            }
        ]
    );
    
    schoolControllers.controller(
        'InstructorHomeCtrl'
        , [
            '$scope'
            , '$window'
            , 'currentUser'
            , 'staff'
            , function($scope, $window, currentUser, staff){
                //TODO
                $scope.students = staff.query({role:$window.driftMan.roles.Student,instructorId:currentUser.id});
            }
        ]
    );
    
    schoolControllers.controller(
        'PracticeCtrl'
        , [
            '$scope'
            , '$window'
            , '$routeParams'
            , 'staff'
            , 'exercises'
            , function($scope, $window, $routeParams, staff, exercises){
                staff.query({role: $window.driftMan.roles.Student}, function(data){
                    angular.forEach(data, function(value){
                        if(value.id == $routeParams.studentId)
                            $scope.student = value;
                    });
                    
                    var updateExercises = function(){
                        $scope.exercises = exercises.query({studentId: $routeParams.studentId});
                    }
                    updateExercises();
                    
                    $scope.setPractice = function(practice){
                        exercises.save({
                                id: practice.id
                            }
                            , null
                            , function(){
                                updateExercises();
                            }
                        );
                    }
                });
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
                
                $scope.getInstructorName = function(instructorId){
                    return getPersonName(instructorId, $scope.staff);
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
                
                $scope.deletePerson = function(person){
                    if(!$window.confirm('Вы уверены, что хотите удалить работника "' + person.name + '"?'))
                        return;
                    
                    staff.delete({id: person.id}, function(){
                        $scope.staff = staff.query();
                    });
                }
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
                    role: $scope.positions[1].value //Instructor
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
            , function($scope, $window, $location, groups){
                $scope.group = {  };
                
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
                $scope.instructors = staff.query({role:$window.driftMan.roles.Instructor});
                
                var resfreshStudents = function(){
                    $scope.students = staff.query({role:$window.driftMan.roles.Student, groupId:$routeParams.groupId});
                }
                resfreshStudents();
                
                $scope.getInstructorsName = function(instructorId){
                    return getPersonName(instructorId, $scope.instructors);
                };
                
                $scope.addStudent = function(){
                    $location.path('/addStudent/' + $routeParams.groupId)
                };
                
                $scope.deleteStudent = function(person){
                    if(!$window.confirm('Вы уверены, что хотите удалить студента "' + person.name + '"?'))
                        return;
                    
                    staff.delete({id: person.id}, function(){
                        resfreshStudents();
                    });
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
                $scope.instructors = staff.query({role:$window.driftMan.roles.Instructor});
                
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
                        , { email: 'instructor@driftman.ru', password: 'instructor123' }
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
                    if(e.shiftKey && e.keyCode === 13){
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
                            currentUser.set(data.id, data.name, data.role);
                            
                            var newPath = '/studentHome';
                            if(data.role === $window.driftMan.roles.Instructor)
                                newPath = '/instructorHome';
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