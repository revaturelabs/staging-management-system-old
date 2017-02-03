 angular
        .module( "sms" )
        .service( "skillService", skillService );
        
    function skillService( $resource,loginService ) {
        //var skillResource = $resource("/api/v1/TechSkills");
        var ssr = this;



       ssr.skillResource = $resource("api/v1/TechSkills/:skillName", 
       		{id: "@skillName"},
               { 
                save  : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/TechSkills" }, 
                query : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, isArray: true }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } },
                remove: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "DELETE" }  
               } 
           )
           
        ssr.getAll = function(success,error){
        	ssr.skillResource.query(success,error);
        }

        ssr.retrieve = function(skillName, success,error){
            ssr.skillResource.get({skillName: skillName}, success, error);
        }

        ssr.create = function(skill, success, error) {
        	ssr.skillResource.save(skill, success, error);
        }

        ssr.remove = function(skillName, success, error){
            ssr.skillResource.remove({skillName: skillName}, success, error);
        }


    }