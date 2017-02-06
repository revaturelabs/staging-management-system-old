    
    angular
        .module( "sms" )
        .service( "skillService", skillService );
        

    function skillService( $resource,loginService ) {
        var ssr = this;



       ssr.skillResource = $resource('api/v1/TechSkills/:skillName/:newSkillName', 
       		{skillName: "@skillName", newSkillName: "@newSkillName"},
               { 
                save  : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/TechSkills" }, 
                query : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, isArray: true, url: "api/v1/TechSkills/:skillName" }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, url: "api/v1/TechSkills/:skillName" },
                remove: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "DELETE", url: "api/v1/TechSkills/:skillName" },
                update: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT"}  
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

        ssr.update = function(skillName, newSkillName, success, error){
            ssr.skillResource.update({skillName: skillName, newSkillName : newSkillName}, success, error);
        }

    }