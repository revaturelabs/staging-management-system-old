    
    angular
        .module( "sms" )
        .service( "taskTypeService", taskTypeService );
        
    function taskTypeService( $resource ) {
        var taskTypeResource = $resource("/api/v1/taskType");
        var tts = this;

        tts.getAll = function(success, error) {
            taskTypeResource.query(success, error);
        };
    };