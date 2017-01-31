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
	apc.oldDate = new Date(project.date);
	/**@prop {string} oldNote Variable holding note of task for reference. */
	apc.oldNote = project.note;
	/**@prop {boolean} oldPassed Variable holding status of task for reference. */
	apc.oldPassed = project.passed;
	/**@prop {Date} newDate Variable holding date value to be updated. */
	apc.newDate = new Date(project.date);
    /**@prop {string} newNote Variable holding note value to be updated. */
	apc.newNote = project.note;
    /**@prop {boolean} passed Variable with status of task*/
	apc.newPassed = project.passed;
	
	//functions
	/**@var {function} submit function reference variable. */
	apc.submit = submit;
	/**@var {function} cancel function reference variable. */
	apc.cancel = cancel;
	
    /**
     * @description Called when user clicks submit button. Saves updated information and updates it in the database and closes the dialog.
     */
	function submit() {
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
	function cancel(){
		apc.project.dateDisplay = (apc.oldDate.getMonth()+1)+"/"+apc.oldDate.getDate();
		apc.project.note = apc.oldNote;
		apc.project.passed = apc.oldPassed;
		$mdDialog.hide();
	}
}
