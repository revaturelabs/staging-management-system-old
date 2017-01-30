 angular
        .module( "sms" )
        .service( "skillService", skillService );
    /**
     * @description AngularJs service for skills
     */    
    function skillService( $resource) {
        /**@var {object} Resoruce object for connecting to the REST service */
        var skillResource = $resource("/api/v1/TechSkills");
        var ssr = this;    

        /**
         * @description Gets all skills from the database.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */   
        ssr.getAll = function(success){
        	skillResource.query(success);
        }
    }