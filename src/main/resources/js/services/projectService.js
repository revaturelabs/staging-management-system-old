    angular
        .module( "sms" )
        .service( "projectService", projectService );
        
    function projectService( $resource ) {
        var projectResource = $resource("/api/v1/project");
        var pr = this;

        pr.getAll = function(success, error) {
        	projectResource.query(success, error);
        }
        
        
    }