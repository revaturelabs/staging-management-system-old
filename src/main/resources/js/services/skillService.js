 angular
        .module( "sms" )
        .service( "skillService", skillService );
        
    function skillService( $resource,loginService ) {
        var skillResource = $resource("/api/v1/TechSkills");
        var ssr = this;

//        ssr.skillResource = $resource("api/v1/TechSkills/", 
//        		{},
//                { 
//                    get   : { headers: { "Content-Type": "application/json"} }
//                } 
//            )
//            
        ssr.getAll = function(success,error){
        	skillResource.query(success,error);
        }
    }