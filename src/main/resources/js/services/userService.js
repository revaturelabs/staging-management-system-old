
    var sms = angular.module( "sms" );

    sms.service( "userService", function( $resource, $http, loginService ){
        var us = this;
        
        us.auth = function(){
            return loginService.getToken();
        }
        
        us.userResource = $resource("api/v1/user/:username", 
            { id: "@username" }, 
            { 
                save  : { headers: { "Content-Type": "application/json", "Authorization": us.auth() }, method: "PUT", url: "api/v1/user" }, 
                query : { headers: { "Content-Type": "application/json", "Authorization": us.auth() }, isArray: true }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": us.auth() } }, 
                update: { headers: { "Content-Type": "application/json", "Authorization": us.auth() }, method: "POST", url: "api/v1/user" },
                remove: { headers: { "Content-Type": "application/json", "Authorization": us.auth() } } 
            } 
        );

        us.create = function(user, success, error) {

            // var config = { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/user" };
            // var userResource = $resource( "api/v1/user", { save: config } );
            //user.$save(success, error);
        	//console.log("In create! " + user);
        	us.userResource.save(user, success, error);

        };

        us.getAll = function(success, error) {
            us.userResource.query(success, error);
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