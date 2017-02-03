var sms = angular.module("sms")
				 .controller( "assignProjectCtrl", assignProjectCtrl );

function assignProjectCtrl( $scope, $mdDialog, userService, projectService, user, project) {
	var apc = this;
	
	//bindables
	/**@prop {object} user Variable holding user object of user to be updated. */
	apc.user = user;
	/**@prop {object} project Variable holding the user's current project object */
	apc.project = project;
	/**@prop {object} project Variable holding the user's current project object, for reference */
	apc.currentProject = project;
	
	/**@prop {string} message Variable with message to be displayed*/
	apc.message = "";
	

	//functions
	apc.getProjects = getProjects;
	
	/**@var {function} assignSubmit function reference variable. */
	apc.assignSubmit = assignSubmit;
	/**@var {function} assignCancel function reference variable. */
	apc.assignCancel = assignCancel;
	
	
	//initialization
	apc.getProjects();
	
	function getProjects(){
    	projectService.getAll(function(response) {
    		apc.availProjects = response;
    		var today = new Date();
    		for(var i = 0; i<apc.availProjects.length;i++){
    			
    			//remove current Project
    			if(apc.currentProject && apc.availProjects[i].name == apc.currentProject.name){
    				apc.availProjects.splice(i,1);
    				i--;
    			}
    			
    			//if project has ended remove from list
    			else if(apc.availProjects[i].endDate < today.getTime()){
    				apc.availProjects.splice(i,1);
    				i--;
    			}

    		}
    		
    		//add displayDates for end and start dates to available projects
    		for(var i = 0; i<apc.availProjects.length;i++){
				var endDate = new Date(apc.availProjects[i].endDate);
				var startDate = new Date(apc.availProjects[i].startDate);
				
    			apc.availProjects[i].endDateDisplay = (endDate.getMonth()+1)+"/"+endDate.getDate(); 
    			apc.availProjects[i].startDateDisplay = (startDate.getMonth()+1)+"/"+startDate.getDate();
    		}
    		
    	}, function(error) {
    	})
	}
	
	function assignSubmit(){
		//validation
		//no project selected
		if(!apc.project){
			apc.message = "There is no project selected.";
			return;
		}
		//end Validation
		
		// a new project was not selected or the selected project is the same as the current project
		if(!apc.project && apc.project.name == apc.currentProject.name){
			$mdDialog.hide();
			return;
		}
		
		//user does not have a current project a project was selected
		else if(!apc.currentProject && apc.project){
			//create new projectUser object entry with new project
			apc.user.project.push({project:apc.project});
			
			apc.user.activeProject = apc.project;
			
			//save User
			userService.update(apc.user,function(){});
		}
		
		//user has a current Project AND a new project was selected
		else if(apc.project.name != apc.currentProject.name){
			apc.user.activeProject = apc.project;
			
			// update old project
			for(var i = 0; i < apc.user.project.length;i++){
				if(apc.user.project[i].project.name == apc.currentProject.name){
					apc.user.project[i].project = apc.project;
					break;
				}
			}
			apc.user.activeProject = apc.project;
			//save User
			console.log(apc.user);
			userService.update(apc.user,function(){});
			
		}
		
		$mdDialog.hide();
	}
	
	function assignCancel(){
		
		
		
		$mdDialog.cancel();
	}
	
}