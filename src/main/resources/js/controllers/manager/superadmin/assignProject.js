var sms = angular.module("sms")
				 .controller( "assignProjectCtrl", assignProjectCtrl );

function assignProjectCtrl( $scope, $mdDialog, userService, user, project, hasProject) {
	var apc = this;
	
	//bindables
	/**@prop {object} user Variable holding user object of user to be updated. */
	apc.user = user;
	/**@prop {object} project Variable holding current project object */
	apc.project = project;

	/**@prop {string} message Variable with message to be displayed*/
	apc.message = "";
	

	//functions
	/**@var {function} assignSubmit function reference variable. */
	apc.assignSubmit = assignSubmit;
	/**@var {function} assignCancel function reference variable. */
	apc.assignCancel = assignCancel;
	
	//initialization
	
	function assignSubmit(){
		
		//update user object
		apc.user.tasks.push(newProject);
		apc.user.project = newProject;
		
		//save user
		userService.update(apc.user,function(){
			$mdDialog.hide();
		});
	}
	
	function assignCancel(){
		
		$mdDialog.hide();
	}
	
}