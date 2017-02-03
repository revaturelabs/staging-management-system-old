 angular
        .module( "sms" )
        .service( "jobEventTypeService",jobEventTypeService );
        
    function jobEventTypeService( $resource,loginService ) {
        var jobEventTypeResource = $resource("/api/v1/JobEventType");
        var jets = this;
        
        jets.getAll = function(success,error){
        	jobEventTypeResource.query(success,error);
        }
    }