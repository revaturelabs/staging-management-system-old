
    var sms = angular.module( "sms" );

    sms.service( "taskService", function($resource){
        var taskResource = $resource("api/v1/task/:id",{id: "@id"},{ save:{method:"POST",url:"api/v1/task"}, update:{method:"PUT",url:"api/v1/task"} });
        var ts = this;

        ts.create = function(task, sucess, error) {
            task.$save(success, error);
        };

        ts.getAll = function(success, error) {
            taskResource.query(success, error);
        };

        ts.retrieve = function(id, success, error) {
            taskResource.get({id: id}, success, error);
        };

        ts.update = function(task, success, error) {
            task.$update(success, error);
        };

        ts.remove = function(task, success, error) {
            task.$remove(success, error);
        };
    });