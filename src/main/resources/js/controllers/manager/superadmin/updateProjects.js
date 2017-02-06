var sms = angular.module("sms")
				 .controller( "updateProjectsCtrl", updateProjectsCtrl );

function updateProjectsCtrl( $scope, $mdDialog, userService, projectService) {
	var upc = this;
	
	//bindables
	/**@prop {string} message Variable with message to be displayed*/
	upc.message = "";
	selectedProject = {};

	//functions
	upc.getProjects = getProjects;
	
	/**@var {function} assignSubmit function reference variable. */
	upc.assignSubmit = assignSubmit;
	/**@var {function} assignCancel function reference variable. */
	upc.assignCancel = assignCancel;
	
	
	//initialization
	upc.getProjects();
	
	function getProjects(){
    	projectService.getAll(function(response) {
    		upc.allProjects = response;
    		var today = new Date();
 /*   		for(var i = 0; i<upc.availProjects.length;i++){
    			
    			if(upc.availProjects[i].name == "No Project"){
    				upc.availProjects.splice(i,1);
    				i--;
    				continue;
    			}
    		}*/
    	}, function(error) {
    	})
	}
	
	function assignSubmit(){
		$mdDialog.cancel();
	}
	
	function assignCancel(){
		$mdDialog.cancel();
	}
	
}