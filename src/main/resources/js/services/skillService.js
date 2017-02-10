    
    angular
        .module( "sms" )
        .service( "skillService", skillService );
        
    function skillService( $resource ) {
        var skillResource = $resource("/api/v1/TechSkills");
        var ss = this;

        ss.getAll = function(success,error){
        	skillResource.query(success,error);
        }
    }