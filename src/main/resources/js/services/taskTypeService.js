    var sms = angular.module( "sms" );

    sms.service( "taskTypeService", function($resource){
        var taskTypeResource = $resource("/api/v1/taskType");
        var tts = this;

        tts.getAll = function(success, error) {
            taskTypeResource.query(success, error);
        };
    });