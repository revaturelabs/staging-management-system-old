var sms = angular.module("sms");

sms.controller("associateCertificationsCtrl", function($scope, $state, $mdDialog,
		loginService, userService, taskTypeService) {

	//refers to the scope of this controller
	var acc = this; 
		
	//gets logged in user
	acc.user = loginService.getUser();
	
	//gets logged in user's grad date
	acc.gradDate = new Date(acc.user.graduationDate);
	
	//formats user's grad date
	acc.formattedGradDate = ((acc.gradDate.getMonth()) + 1) + "/" + acc.gradDate.getDate() + "/" + acc.gradDate.getFullYear();
	
	//sets new date to today, used to set min date on calendar
	acc.minDate = new Date();
	
	//gets the max date to set for the calendar when scheduling a certification
	acc.getMaxDate = function() {
		//loops through all of the user's tasks
		for(var i = 0; i < acc.user.tasks.length; i++) {
			var today = new Date();
			
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
	};
	
	//today's date
	acc.myDate = new Date();
	
	//max selectable date for the calendar
	acc.maxDate = acc.getMaxDate();
	
	//closes the dialog
	acc.cancel = function() {
		$mdDialog.cancel();
	};
	
	//submits a user's scheduled cert into the database
	acc.submit = function() {
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
		
	};

});