
    var sms = angular.module( "sms" );

    sms.service( "userService", function($resource){
        var userResource = $resource("api/v1/user/:id",{id: "@id"},{ save:{method:"POST",url:"api/v1/user"}, update:{method:"PUT",url:"api/v1/user"} });
        var us = this;

        us.create = function(user, sucess, error) {
            user.$save(success, error);
        };

        us.getAll = function(success, error) {
            userResource.query(success, error);
        };

        us.retrieve = function(id, success, error) {
            userResource.get({id: id}, success, error);
        };

        us.update = function(user, success, error) {
            user.$update(success, error);
        };

        us.remove = function(user, success, error) {
            user.$remove(success, error);
        };
    });