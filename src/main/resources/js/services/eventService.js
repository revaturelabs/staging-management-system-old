
    var sms = angular.module( "sms" );

    sms.service( "eventService", function($resource){
        var eventResource = $resource("api/v1/event/:id",{id: "@id"},{ save:{method:"POST",url:"api/v1/event"}, update:{method:"PUT",url:"api/v1/event"} });
        var ts = this;

        ts.create = function(event, sucess, error) {
            event.$save(success, error);
        };

        ts.getAll = function(success, error) {
            eventResource.query(success, error);
        };

        ts.retrieve = function(id, success, error) {
            eventResource.get({id: id}, success, error);
        };

        ts.update = function(event, success, error) {
            event.$update(success, error);
        };

        ts.remove = function(event, success, error) {
            event.$remove(success, error);
        };
    });