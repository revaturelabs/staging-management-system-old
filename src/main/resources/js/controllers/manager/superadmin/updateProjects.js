var sms = angular.module("sms")
				 .controller( "updateProjectsCtrl", updateProjectsCtrl );

function updateProjectsCtrl( $scope, $mdDialog, userService, projectService) {
	var upc = this;
	
	//bindables
	/**@prop {string} message Variable with message to be displayed*/
	upc.message = "";
	upc.selectedProject = {};

	//functions
	upc.getProjects = getProjects;
	upc.newProject = newProject;
	upc.deleteProject = deleteProject;
	
	/**@var {function} assignSubmit function reference variable. */
	upc.assignSubmit = assignSubmit;
	/**@var {function} assignCancel function reference variable. */
	upc.assignCancel = assignCancel;
	upc.close = close;
	
	
	//initialization
	upc.getProjects();
	
	var noProj={};
	function getProjects(){
    	projectService.getAll(function(response) {
    		upc.allProjects = response;
    		var today = new Date();
    		for(var i = 0; i<upc.allProjects.length;i++){
    			if(upc.allProjects[i].name == "No Project"){
    				noProj = upc.allProjects[i];
    				upc.allProjects.splice(i,1);
    				i--;
    				continue;
    			}
    			upc.allProjects[i].startDate = new Date(upc.allProjects[i].startDate);
    			upc.allProjects[i].endDate = new Date(upc.allProjects[i].endDate);
    			
    		}
    		
    	}, function(error) {
    	})
	}
	
	function newProject(){
		upc.allProjects.push({"name":"New project"});
		upc.selectedProject = upc.allProjects[upc.allProjects.length-1];
		upc.message = "Project added";
	}
	
	function deleteProject(){
		
	}
	
	function assignSubmit(){
		$mdDialog.cancel();
	}
	
	function assignCancel(){
		$mdDialog.cancel();
	}
	
	function close(){
		$mdDialog.cancel();
	}
	
}