(function(){'use strict';

    var schoolFactories = angular.module('schoolFactories', ['ngResource']);

    schoolFactories.factory(
        'logon'
        , [
            '$resource'
            , function($resource){
                return $resource('LogonApi');
            }
        ]
    );
    
    schoolFactories.factory(
        'lectures'
        , [
            '$resource'
            , function($resource){
                return $resource('LectureApi');
            }
        ]
    );
    
    schoolFactories.factory(
        'exercises'
        , [
            '$resource'
            , function($resource){
                return $resource('ExerciseApi');
            }
        ]
    );
    
    schoolFactories.factory(
        'errorFactory'
        , function () {
            var container = [];

            return {
                addError: function (err) {
                    container.push(err);
                }
                , removeLastError: function () {
                    container.pop();
                }
                , count: function() {
                    return container.length;
                }
                , errorLinesForDisplay: function () {
                    if (!container.length)
                        return [];
                    return container[container.length - 1].split('\n');
                }
            };
        }
    );
    
    schoolFactories.factory(
        'currentUser'
        , [
            '$window'
            , function ($window) {
                var currentUser = {
                    role: null
                    , isAuthorized: false

                    , set: function(name, role){
                        this.name = name;
                        this.role = role;
                        this.isAuthorized = true;

                        try{
                            $window.localStorage.setItem('currentUser', angular.toJson(currentUser));
                        }
                        catch(err){ }
                    }
                    , reset: function(){
                        this.set(null, null);
                        this.isAuthorized = false;
                        
                        try{
                            $window.localStorage.removeItem('currentUser');
                        }
                        catch(err){ }
                    }
                };

                return currentUser;
            }
        ]
    );
  
})();