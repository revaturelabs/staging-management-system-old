 angular
        .module( "sms" )
        .service( "skillService", skillService );
        
    function skillService( $resource,loginService ) {
        var skillResource = $resource("/api/v1/skill");
        var ssr = this;

        ssr.skillResource = $resource("api/v1/skill/", 
        		{},
                { 
                    get   : { headers: { "Content-Type": "application/json"} }
                } 
            )
            
        ssr.get = function(success,error){
        	ssr.skillResource(success,error);
        }
    }