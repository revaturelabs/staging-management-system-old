 angular
        .module( "sms" )
        .service( "skillService", skillService );
        
    function skillService( $resource,loginService ) {
        //var skillResource = $resource("/api/v1/TechSkills");
        var ssr = this;

       ssr.skillResource = $resource("api/v1/TechSkills/:skillName", 
       		{id: "@skillName"},
               { 
                query : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, isArray: true }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } }, 
               } 
           )
           
        ssr.getAll = function(success,error){
        	ssr.skillResource.query(success,error);
        }

        ssr.retrieve = function(skillName, success,error){
            ssr.skillResource.get({skillName: skillName}, success, error);
        }
    }