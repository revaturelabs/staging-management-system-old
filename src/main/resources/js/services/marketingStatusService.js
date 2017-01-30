 angular
        .module( "sms" )
        .service( "mStatusService",mStatusService );
        
    function mStatusService( $resource,loginService ) {
        var mStatusResource = $resource("/api/v1/MarketingStatus");
        var mssr = this;

//        mssr.skillResource = $resource("api/v1/TechSkills/", 
//        		{},
//                { 
//                    get   : { headers: { "Content-Type": "application/json"} }
//                } 
//            )
//            
        mssr.getAll = function(success,error){
        	mStatusResource.query(success,error);
        }
    }