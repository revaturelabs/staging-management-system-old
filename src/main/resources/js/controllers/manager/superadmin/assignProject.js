var sms = angular.module("sms")
				 .controller( "assignProjectCtrl", assignProjectCtrl );

function assignProjectCtrl( $scope, $mdDialog, userService, user, project, hasProject) {
	var apc = this;
	
	//bindables
	/**@prop {object} user Variable holding user object of user to be updated. */
	apc.user = user;
	/**@prop {object} project Variable holding current project object */
	apc.project = project;
	/**@prop {Date} oldDate Variable holding date of task for reference. */
	apc.oldDate = new Date();
	/**@prop {string} oldNote Variable holding note of task for reference. */
	apc.oldNote = "";
	/**@prop {boolean} oldPassed Variable holding status of task for reference. */
	apc.oldPassed = false;
	/**@prop {Date} newDate Variable holding date value to be updated. */
	apc.newDate;
    /**@prop {string} newNote Variable holding note value to be updated. */
	apc.newNote = "";
    /**@prop {boolean} passed Variable with status of task*/
	apc.newPassed = false;
	/**@prop {string} message Variable with message to be displayed*/
	apc.message = "";
	

	//functions
	/**@var {function} updateSubmit function reference variable. */
	apc.updateSubmit = updateSubmit;
	/**@var {function} updateCancel function reference variable. */
	apc.updateCancel = updateCancel;
	/**@var {function} assignSubmit function reference variable. */
	apc.assignSubmit = assignSubmit;
	/**@var {function} assignCancel function reference variable. */
	apc.assignCancel = assignCancel;
	
	//initialization
	if(hasProject){
		apc.oldDate = new Date(project.date);
		apc.oldNote = project.note;
		apc.oldPassed = project.passed;
		apc.newDate = new Date(project.date);
		apc.newNote = project.note;
		apc.newPassed = project.passed;
	}
	
    /**
     * @description Called when user clicks submit button. Saves updated information and updates it in the database and closes the dialog.
     */
	function updateSubmit() {
		
		if(!isValid()){
			return;
		}
		
		for(var i = 0; i < apc.user.tasks.length;i++){
			if(apc.user.tasks[i].id == project.id){
				
				if(apc.newDate != null && apc.newDate && apc.newDate != ""){
					project.date = apc.newDate.getTime();
					apc.user.tasks[i].date = apc.newDate.getTime();
					project.dateDisplay = (apc.newDate.getMonth()+1)+"/"+apc.newDate.getDate();

				}
				
				apc.user.tasks[i].note = apc.newNote;
				apc.user.tasks[i].passed = apc.newPassed;
				
				project.note = apc.newNote;
				project.passed = apc.newPassed
			}
		}
		
		userService.update(apc.user,function(){
			$mdDialog.hide();
		});
		
	}
	
    /**
     * @description Called when user clicks cancel button. Resets values to their original values and closes the dialog.
     */
	function updateCancel(){
		apc.project.dateDisplay = (apc.oldDate.getMonth()+1)+"/"+apc.oldDate.getDate();
		apc.project.note = apc.oldNote;
		apc.project.passed = apc.oldPassed;
		$mdDialog.hide();
	}
	
	function assignSubmit(){
		
		if(!isValid()){
			return;
		}
		
		//add task to user
		var newProject = {
				date : apc.newDate.getTime(),
				dateDisplay: (apc.newDate.getMonth()+1)+"/"+apc.newDate.getDate(),
				note: apc.newNote,
				passed: apc.newPassed,
				type:"Project",
				taskType:{
					id:3,
					type:"Project"
				}
			};
		
		//update user object
		apc.user.tasks.push(newProject);
		apc.user.project = newProject;
		
		//save user
		userService.update(apc.user,function(){
			$mdDialog.hide();
		});
	}
	
	function assignCancel(){
		apc.newDate = new Date();
		apc.newNote = "";
		apc.newPassed = false;
		$mdDialog.hide();
	}
	
	function isValid(){
		//validation
		//date inputed
		if(apc.newDate == null || apc.newDate == undefined){
			apc.message = "Please enter a date";
			return;
		}
		
		//end date can't be in the past
		if(apc.newDate.getTime() < (new Date()).getTime()){
			apc.message = "End date can't be in the past";
			return;
		}
		//project name is required
		if("" == apc.newNote || apc.newNote == null || apc.newNote == undefined ){
			apc.message = "project name is required";
			return;
		}
	}
}
