
    var sms = angular.module( "sms" );

    sms.service( "userService", function( $resource, loginService ){
        var userResource = $resource("api/v1/user/:username", 
            { id: "@username" }, 
            { 
                save  : { method: "POST", url: "api/v1/user", headers: { Authorization: loginService.getToken() } }, 
                query : { headers: { Authorization: loginService.getToken() } }, 
                get   : { headers: { Authorization: loginService.getToken() } }, 
                update: { method: "PUT", url: "api/v1/user",  headers: { Authorization: loginService.getToken() } },
                remove: { headers: { Authorization: loginService.getToken() } } 
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