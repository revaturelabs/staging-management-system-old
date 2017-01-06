
    var sms = angular.module( "sms" );

    sms.service( "userService", function($resource){
        var userResource = $resource("api/v1/user/:id",{id: "@id"},{ save:{method:"POST",url:"api/v1/user"}, update:{method:"PUT",url:"api/v1/user"} });

        (function create(user, sucess, error) {
            user.$save(success, error);
        }).bind(this);

        (function getAll(success, error) {
            userResource.query(success, error);
        }).bind(this);

        (function getById(id, success, error) {
            userResource.get({id: id}, success, error);
        }).bind(this);

        (function update(user, success, error) {
            user.$update(success, error);
        }).bind(this);

        (function remove(user, success, error) {
            user.$remove(success, error);
        }).bind(this);
    });