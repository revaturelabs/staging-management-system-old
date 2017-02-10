 angular
        .module( "sms" )
        .service( "trainerService",trainerService );
        
    function trainerService( $resource,loginService ) {
        var trainerResource = $resource("/api/v1/Trainer");
        var mssr = this;
        
        mssr.getAll = function(success,error){
        	trainerResource.query(success,error);
        }
    }