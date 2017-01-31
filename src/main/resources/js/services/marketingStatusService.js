 angular
        .module( "sms" )
        .service( "marketingStatusService",marketingStatusService );
        
    function marketingStatusService( $resource,loginService ) {
        var marketingStatusResource = $resource("/api/v1/MarketingStatus");
        var mssr = this;
        
        mssr.getAll = function(success,error){
        	marketingStatusResource.query(success,error);
        }
    }