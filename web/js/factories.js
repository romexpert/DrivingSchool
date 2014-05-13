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
                return $resource('LecturesApi');
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
        , function () {
            var currentUser = {
                role: null
                , isAuthorized: false
                
                , set: function(name, role){
                    this.name = name;
                    this.role = role;
                    this.isAuthorized = true;
                }
                , reset: function(){
                    this.set(null, null);
                    this.isAuthorized = false;
                }
            };
            
            return currentUser;
        }
    );
  
})();