var sms = angular.module("sms");
sms.filter("weekFilter", function($filter){
	return function(users, monday){
		//filter associate attendance by current viewing monday
		var thisWeek = {};
		
		//set a value for the iconFilter
		iconInput = "week";
		
		//pull in current monday and set the rest of the week up
		m = monday;
		tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));

        //run a for each function to cycle through all users
	    users.forEach(function(user){
	    	//make an empty array on the user object
	    	user.thisWeek = [];
	    	
	    	//run a for each on the for all the attendance records
	    	user.attendance.forEach(function(attendance){
				
	    		//filter out the current week 
	    		//by matching the attendance date object with the current week values
	    		day = new Date(attendance.date);
				if(day.getDate()==monday.getDate() && day.getMonth()==monday.getMonth()){
					thisWeek.monday = {};
					thisWeek.monday.verified = attendance.verified;
					thisWeek.monday.checkedIn = attendance.checkedIn;
					thisWeek.monday = $filter("iconFilter")(thisWeek.monday, iconInput);
				}
				if(day.getDate()==tuesday.getDate() && day.getMonth()==tuesday.getMonth()){
					thisWeek.tuesday = {};
					thisWeek.tuesday.verified = attendance.verified;
					thisWeek.tuesday.checkedIn = attendance.checkedIn;
					thisWeek.tuesday = $filter("iconFilter")(thisWeek.tuesday, iconInput);
				}
				if(day.getDate()==wednesday.getDate() && day.getMonth()==wednesday.getMonth()){
					thisWeek.wednesday = {};
					thisWeek.wednesday.verified = attendance.verified;
					thisWeek.wednesday.checkedIn = attendance.checkedIn;
					thisWeek.wednesday = $filter("iconFilter")(thisWeek.wednesday, iconInput);
				}
				if(day.getDate()==thursday.getDate() && day.getMonth()==thursday.getMonth()){
					thisWeek.thursday = {};
					thisWeek.thursday.verified = attendance.verified;
					thisWeek.thursday.checkedIn = attendance.checkedIn;
					thisWeek.thursday = $filter("iconFilter")(thisWeek.thursday, iconInput);
					
				}
				if(day.getDate()==friday.getDate() && day.getMonth()==friday.getMonth()){
					thisWeek.friday = {};
					thisWeek.friday.verified = attendance.verified;
					thisWeek.friday.checkedIn = attendance.checkedIn;
					thisWeek.friday = $filter("iconFilter")(thisWeek.friday, iconInput);
				}
				//fill out each day with day objects based on values received during for loop
				user.thisWeek[0] = thisWeek.monday;
				user.thisWeek[1] = thisWeek.tuesday;
				user.thisWeek[2] = thisWeek.wednesday;
				user.thisWeek[3] = thisWeek.thursday;
				user.thisWeek[4] = thisWeek.friday;
			})
			//after this user is filled out, reset all objects values to null for next user
			thisWeek.monday = null;
			thisWeek.tuesday = null;
			thisWeek.wednesday = null;
			thisWeek.thursday = null;
			thisWeek.friday = null;
	    	
	    })
		return users;
	}
});