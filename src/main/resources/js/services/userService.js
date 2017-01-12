
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
                query : { headers: { "Content-Type": "application/json", "Authorization": us.auth() }, isArray:true }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": us.auth() } }, 
                update: { headers: { "Content-Type": "application/json", "Authorization": us.auth() }, method: "POST", url: "api/v1/user" },
                remove: { headers: { "Content-Type": "application/json", "Authorization": us.auth() } } 
            } 
        );
        // var userResource = $resource("api/v1/user/:username", 
        //     { id: "@username" }, 
        //     { 
        //         save  : { method: "PUT",  url: "api/v1/user" }, 
        //         query : { method: "GET",  url: "api/v1/user" }, 
        //         get   : { }, 
        //         update: { method: "POST", url: "api/v1/user" },
        //         remove: { }
        //     } 
        // );

        us.create = function(user, success, error) {
            // var config = { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/user" };
            // var userResource = $resource( "api/v1/user", { save: config } );
            //user.$save(success, error);
        	//console.log("In create! " + user);
        	us.userResource.save(user, success, error);
        };

        us.getAll = function(success, error) {
            // var config = { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } };
            // var userResource = $resource( "api/v1/user", { query: { headers: { "Authorization": loginService.getToken() } } } );
            us.userResource.query(success, error);
            // $http({
            //     method: "GET",
            //     url: "api/v1/user",
            //     headers: {
            //         "Authorization": loginService.getToken()
            //     },
            //     isArray: true
            // }).then(success, error);

            // var token = loginService.getToken();
            // $resource("api/v1/user", {}, {
            //     query: {
            //         method: "GET",
            //         headers: { "Authorization": token,
            //                    "Content-Type": "application/json" } },
            //         isArray: true
            // }).query(success, error);
        };

        us.retrieve = function(username, success, error) {
            var config = { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } };
            var userResource = $resource( "api/v1/user/:username", { id: "@username" }, { get: config } );
            userResource.get({username: username}, success, error);
        };

        us.update = function(user, success, error) {
            var config = { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "POST", url: "api/v1/user" };
            var userResource = $resource( "api/v1/user", { update: config } );
            user.$update(success, error);
        };

        us.remove = function(user, success, error) {
            var config = { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } };
            var userResource = $resource( "api/v1/user:username", { id: "@username" }, { remove: config } );
            user.$remove(success, error);
        };
    });