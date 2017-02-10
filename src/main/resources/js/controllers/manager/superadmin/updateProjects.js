var sms = angular.module("sms")
				 .controller( "updateProjectsCtrl", updateProjectsCtrl );

function updateProjectsCtrl( $scope, $mdDialog, userService, projectService) {
	var upc = this;
	
	//bindables
	/**@prop {string} message Variable with message to be displayed.*/
	upc.message = "";
	/**@prop {object} selectedProject Variable with selected project.*/
	upc.selectedProject = {};
	/**@prop {object} errors Variable with list of errors.*/
	upc.errors = [];

	//functions
	/**@var {function} getProjects function reference variable. */
	upc.getProjects = getProjects;
	/**@var {function} newProject function reference variable. */
	upc.newProject = newProject;
	/**@var {function} deleteProject function reference variable. */
	upc.deleteProject = deleteProject;
	/**@var {function} assignSubmit function reference variable. */
	upc.assignSubmit = assignSubmit;
	/**@var {function} close function reference variable. */
	upc.close = close;
	
	//initialization
	upc.getProjects();
	
	function getProjects(){
    	projectService.getAll(function(response) {
    		upc.allProjects = response;
    		for(var i = 0; i<upc.allProjects.length;i++){
    			
    			upc.allProjects[i].startDate = new Date(upc.allProjects[i].startDate);
    			upc.allProjects[i].endDate = new Date(upc.allProjects[i].endDate);
    			upc.allProjects[i].displayName = upc.allProjects[i].name;
    		}
    		upc.selectedProject = upc.allProjects[0];
    	}, function(error) {
    	});
	}
	var count=0;
	function newProject(){
		upc.allProjects.push({"name":"New project" + count,"displayName":"New project" + count});
		upc.selectedProject = upc.allProjects[upc.allProjects.length-1];
		upc.message = "Project added";
		count++;
	}
	
	function deleteProject(){
		//no project selected
		if(!upc.selectedProject || upc.selectedProject == {}){
			upc.message = "Select a project to delete.";
			return;
		}
		
		//delete selected project from DB
		upc.selectedProject.startDate = upc.selectedProject.startDate.getTime();
		upc.selectedProject.endDate = upc.selectedProject.endDate.getTime();
		projectService.del([upc.selectedProject],function(){}, function(error){console.log(error);});
		
		
		//find project to be deleted
		for(var i = 0; i < upc.allProjects.length; i++){
			if(upc.allProjects[i].name == upc.selectedProject.name){
				//clear and remove from all projects list
			}
				upc.selectedProject = {};
				
				upc.allProjects.splice(i,1);
				break;
			}
		}
	
	function assignSubmit(){
		var pass = true;
		upc.errors = [];
		for(var i = 0; i < upc.allProjects.length; i++){
			//validation
			
			//no project name
			if(!upc.allProjects[i].name || upc.allProjects[i].displayName == ""){
				upc.errors.push({ 
					"name" : upc.allProjects[i].name,
					"msg": "Error in project: " + upc.allProjects[i].name +  ", no name."
					});
				pass = false;
			}
			
			//no start date
			try{
			if(!upc.allProjects[i].startDate){
				upc.errors.push({ 
					"name" : upc.allProjects[i].name,
					"msg": "Error in project: " + upc.allProjects[i].name +  ", no start date"
					});
				pass = false;
			}
			}
			catch(err){
				upc.errors.push({ 
					"name" : upc.allProjects[i].name,
					"msg": "Error in project: " + upc.allProjects[i].name +  ", no start date"
					});
				pass = false;
			}
			
			//no end date
			try{
				if(!upc.allProjects[i].endDate){
					upc.errors.push({ 
						"name" : upc.allProjects[i].name,
						"msg": "Error in project: " + upc.allProjects[i].name +  ", no end date"
						});
					pass = false;
				
				}
			}
			catch(err){
				upc.errors.push({ 
					"name" : upc.allProjects[i].name,
					"msg": "Error in project: " + upc.allProjects[i].name +  ", no end date"
					});
				pass = false;
			}
			
			//end dates are after start dates
			if(pass && upc.allProjects[i].endDate.getTime() < upc.allProjects[i].startDate.getTime()){
				var string = "Error in project: " + upc.allProjects[i].name + ", end date is before start date.";
				
				upc.errors.push({
					"name" : upc.allProjects[i].name,
					"msg": string
					});
				
				pass = false;
			}
		}
		
		if(pass){
			for(var s = 0; s<upc.allProjects.length;s++){
				upc.allProjects[s].name = upc.allProjects[s].displayName;
			}
			//update current projects
			projectService.update(upc.allProjects,function(){$mdDialog.cancel();}, function(){});
		}
	}
	
	function close(){
		upc.selectedProject={};
		upc.errors=[];
		upc.getProjects();
		$mdDialog.cancel();
	}
	
}