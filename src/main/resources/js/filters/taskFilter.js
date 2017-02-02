var sms = angular.module("sms");
sms.filter("taskFilter", function($filter){
	return function(users, now){
		//based on current day see what tasks are coming up and what tasks are past
		//start by getting the future date and past date
		future = new Date(now);
		past = new Date(now);
		future.setDate(future.getDate()+3);
		past.setDate(past.getDate()-3);
		
		users.forEach(function(user){
			var upcoming = 0;
			user.notification = {};
			user.notification.number = 0;
			user.panels = [];
			user.certs = [];
			user.projects = [];
			user.tasks.forEach(function(task){
				thisTask = {};
				thisTask.type = task.taskType.type;
				thisTask.note = task.note;
				thisTask.passed = task.passed;
				thisTask.id = task.id;
				
				
				//check when the task is
				thisTask.date = task.date;
				thisDate = new Date(task.date);
				//set up a date to display that doesn't mess with the sorting
				thisTask.dateDisplay = (thisDate.getMonth()+1)+"/"+thisDate.getDate();
				
				if((thisDate > now) && (thisDate < future)){
					//see if the task is less than 3 days in the future
					//add to notifications as upcoming event
					upcoming += 1;
					
					//add icon with color to denote upcoming event
					iconInput = "task";
					thisTask = $filter("iconFilter")(thisTask, iconInput);
				}else if(thisDate > past){
					//highlight as recently past event with a grayed icon
					iconInput = "pastTask";
					thisTask = $filter("iconFilter")(thisTask, iconInput);
				}
				if(thisTask.type == "Certification"){
					user.certs.push(thisTask);
				}else if(thisTask.type == "Panel"){
					user.panels.push(thisTask);
				}else{
					user.projects.push(thisTask);
				}
			})
			//set up notifications for upcoming events
			if(upcoming > 0){
				iconInput = "notification";
				user.notification.number = upcoming;
				user.notification = $filter("iconFilter")(user.notification, iconInput);
			}
		});
		return users;
	}
});