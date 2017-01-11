
    var sms = angular.module( "sms" );

    sms.service( "userService", function( $resource, loginService ){
        var userResource = $resource("api/v1/user/:username",{id: "@username"},{ save:{method:"POST",url:"api/v1/user"}, update:{method:"PUT",url:"api/v1/user"} });
        var us = this;

        us.create = function(user, success, error) {
            var userToken = { user: user, authToken: loginService.getToken() };
            userToken.$save(success, error);
        };

        us.getAll = function(success, error) {
            userResource.query(success, error);
        };

        us.retrieve = function(username, success, error) {
            userResource.get({username: username}, success, error);
        };

        us.update = function(user, success, error) {
            var userToken = { user: user, authToken: loginService.getToken() };
            userToken.$update(success, error);
        };

        us.remove = function(user, success, error) {
            var userToken = { user: user, authToken: loginService.getToken() };
            userToken.$remove(success, error);
        };
    });