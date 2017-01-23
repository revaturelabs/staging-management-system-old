
    angular
        .module( "sms" )
        .service( "userService", userService );
        
    function userService( $resource, loginService ) {
        var us = this;
        us.userResource = $resource("api/v1/user/:username", 
            { id: "@username" }, 
            { 
                save  : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/user" }, 
                query : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, isArray: true }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } }, 
                update: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "POST", url: "api/v1/user" },
                remove: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } } 
            } 
        );

        us.create = function(user, success, error) {
        	us.userResource.save(user, success, error);
        };

        us.getAll = function(success, error) {
            us.userResource.query(success, error);
        };

        us.retrieve = function(username, success, error) {
            userResource.get({username: username}, success, error);
        };

        us.update = function(user, success, error) {
            us.userResource.update(user, success, error);
        };

        us.remove = function(user, success, error) {
            user.$remove(success, error);
        };
    };