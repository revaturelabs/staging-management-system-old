 angular
        .module( "sms" )
        .service( "skillService", skillService );
    /**
     * @description AngularJs service for skills
     */    
    function skillService( $resource,loginService ) {
        /**@var {object} Resoruce object for connecting to the REST service */
        var skillResource = $resource("/api/v1/TechSkills");
        var ssr = this;

//        ssr.skillResource = $resource("api/v1/TechSkills/", 
//        		{},
//                { 
//                    get   : { headers: { "Content-Type": "application/json"} }
//                } 
//            )
//         

        /**
         * @description Gets all skills from the database.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */   
        ssr.getAll = function(success,error){
        	skillResource.query(success,error);
        }
    }