
    var sms = angular.module( "sms" );

    sms.service( "attendenceService", function($resource){
        var attendenceResource = $resource("api/v1/attendence/:id",{id: "@id"},{ save:{method:"POST",url:"api/v1/attendence"}, update:{method:"PUT",url:"api/v1/attendence"} });
        var ts = this;

        ts.create = function(attendence, sucess, error) {
            attendence.$save(success, error);
        };

        ts.getAll = function(success, error) {
            attendenceResource.query(success, error);
        };

        ts.retrieve = function(id, success, error) {
            attendenceResource.get({id: id}, success, error);
        };

        ts.update = function(attendence, success, error) {
            attendence.$update(success, error);
        };

        ts.remove = function(attendence, success, error) {
            attendence.$remove(success, error);
        };
    });