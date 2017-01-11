
    var sms = angular.module( "sms" );

    sms.service( "userService", function( $resource, loginService ){
        var userResource = $resource("api/v1/user/:username", 
            { id: "@username" }, 
            { 
                save  : { headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8;", "Authorization": loginService.getToken() }, method: "POST", url: "api/v1/user" }, 
                query : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } }, 
                update: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/user" },
                remove: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } } 
            } 
        );
        var us = this;

        us.create = function(user, success, error) {
            user.$save(success, error);
        };

        us.getAll = function(success, error) {
            userResource.query(success, error);
        };

        us.retrieve = function(username, success, error) {
            userResource.get({username: username}, success, error);
        };

        us.update = function(user, success, error) {
            user.$update(success, error);
        };

        us.remove = function(user, success, error) {
            user.$remove(success, error);
        };
    });