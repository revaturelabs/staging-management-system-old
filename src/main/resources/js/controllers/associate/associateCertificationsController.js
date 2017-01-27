var sms = angular.module("sms");

sms.controller("associateCertificationsCtrl", function($scope, $state, $mdDialog,
		loginService, userService, taskTypeService) {

	//refers to the scope of this controller
	var acc = this; 
		
	 /**@prop {object} user Currently logged in user. */
	acc.user = loginService.getUser();
	
	 /**@prop {Date} gradDate Logged in user's graduation date. */
	acc.gradDate = new Date(acc.user.graduationDate);
	
	 /**@prop {Date} formattedGradDate User's Graduation date properly formatted. */
	acc.formattedGradDate = ((acc.gradDate.getMonth()) + 1) + "/" + acc.gradDate.getDate() + "/" + acc.gradDate.getFullYear();
	
	 /**@prop {Date} minDate The minimum date for the calendar */
	acc.minDate = new Date();
	 /**@prop {Date} myDate The current date */
	acc.myDate = new Date();
	/**@prop {Date} maxDate The latest date that a certification can be set. */
	acc.maxDate = getMaxDate();


	//functions
	acc.getMaxDate = getMaxDate;
	acc.cancel = cancel;
	acc.submit = submit;
	

	
	/**
	 * @description Sets the maximum date for the calendar for scheduleing a certification.
	 * If the user is trying to schedule his/her first certification, they can only do it a month from
	 * his/her graduation date. In addition, only one certification can be scheduled at a time.
	 * @returns {Date} The latest date in the future that a certification can be scheduled for.
	 */
	function getMaxDate() {
		//loops through all of the user's tasks
		for(var i = 0; i < acc.user.tasks.length; i++) {
			//sets a user's tasks timestamp date to a date object
    		var certDate = new Date(acc.user.tasks[i].date);
    		var cert = "Certification";
    		
    		//if the task is a certification task and the date has already happened
    		if ( certDate.getTime() <= acc.myDate.getTime() && (acc.user.tasks[i].taskType.type == cert) ) {
    			//returns max date to be month from today
    			return new Date(acc.myDate.getFullYear(), acc.myDate
    					.getMonth() + 1, acc.myDate.getDate());
    		}
		}
		//if there are no certs scheduled or there is a scheduled cert that hasn't happened yet
		//return the user's grad date + 1 month
		return new Date(acc.gradDate.getFullYear(), acc.gradDate
				.getMonth() + 1, acc.gradDate.getDate());
	}
		
	
	
	
	/**
	 * @description Closes the certification dialog window.
	 */
	function cancel() {
		$mdDialog.cancel();
	}
	
	/**
	 * @description Updates the database to include the new certification
	 */
	function submit() {
		//gets all task types
		acc.taskTypes = taskTypeService.getAll(function(response) {
			acc.taskTypes = response;
			
			//sets to certification task type
			var cert = acc.taskTypes[0];
			
			//create new task object
			var newTask = {};
			newTask.taskType = cert;
			newTask.date = acc.myDate;
			newTask.note = acc.note;
			
			//push task into user's task list
			acc.user.tasks.push(newTask);
			
			//updates user's info with new scheduled cert
			userService.update(acc.user,
					function(response){
						//success
						$mdDialog.hide();
					},function(error){
						//failure
						$mdDialog.cancel();
					});
		}, function(error) {
			$mdDialog.cancel();
		});
		
	}

});