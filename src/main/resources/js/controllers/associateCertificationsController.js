var sms = angular.module("sms");

sms.controller("associateCertificationsCtrl", function($scope, $state, $mdDialog,
		loginService, userService, taskTypeService) {

	var acc = this;
		
	acc.user = loginService.getUser();
	acc.gradDate = new Date(acc.user.graduationDate);
	acc.formattedGradDate = ((acc.gradDate.getMonth()) + 1) + "/" + acc.gradDate.getDate() + "/" + acc.gradDate.getFullYear();

	acc.myDate = new Date();
	acc.minDate = new Date();
	acc.maxDate = new Date(acc.gradDate.getFullYear(), acc.gradDate
			.getMonth() + 1, acc.gradDate.getDate());
	acc.onlyWeekendsPredicate = function(date) {
		var day = date.getDay();
		return day === 0 || day === 6;
	}
	
	acc.cancel = function() {
		$mdDialog.cancel();
	}
	
	acc.submit = function() {
		
		acc.taskTypes = taskTypeService.getAll(function(response) {
			acc.taskTypes = response;
			var cert = acc.taskTypes[0];
			
			var newTask = {};
			newTask.taskType = cert;
			newTask.date = acc.myDate;
			newTask.note = acc.note;
			//console.log(newTask);
			acc.user.tasks.push(newTask);
			
			//console.log(acc.user);
			userService.update(acc.user,
					function(response){
						//success
						/*console.log("Response:", response);*/
						$mdDialog.hide();
					},function(error){
						//failure
						//console.log("Error:", error);
						$mdDialog.cancel();
					});
		}, function(error) {
			$mdDialog.cancel();
		});
		

		
		/*var newTaskType = {};
		newTaskType.ID = 1;
		newTaskType.type = "Certification";*/
	
/*		var newTask = {};
		newTask.taskType = newTaskType;
		newTask.date = acc.myDate;
		newTask.note = acc.note;
		console.log(newTask);
		acc.user.tasks.push(newTask);
		
		//alert((acc.user.tasks[acc.user.tasks.length - 1]).date);
		console.log(acc.user);
		userService.update(acc.user,
				function(response){
					//success
					console.log("Response:", response);
					$mdDialog.hide();
				},function(error){
					//failure
					console.log("Error:", error);
					$mdDialog.cancel();
				});*/
		
		//$mdDialog.hide();
	};

});