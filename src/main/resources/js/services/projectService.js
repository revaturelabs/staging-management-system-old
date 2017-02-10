    angular
        .module( "sms" )
        .service( "projectService", projectService );
        
    function projectService( $resource, loginService ) {
    	var pr = this;
    	pr.projectResource = $resource("/api/v1/project",
    	{},
    	{
    		update  : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "POST", url: "api/v1/project" },
    		del  : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "POST", url: "api/v1/project/remove" }
    	});
    	
        pr.getAll = function(success, error) {
        	pr.projectResource.query(success, error);
        }
        
        pr.update = function(projects, success, error) {
        	pr.projectResource.update(projects, success, error);
        }
        
        pr.del = function(projects, success, error) {
        	pr.projectResource.del(projects, success, error);
        }
    }