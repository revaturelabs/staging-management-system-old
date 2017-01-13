var sms = angular.module("sms");
sms.filter("weekFilter", function(){
	return function(users, monday){
		//filter associate attendance by current viewing monday
		var thisWeek = {};
		
		m = monday;
		tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
	    
        console.log(m);
        console.log(monday);
        console.log(tuesday);
        console.log(wednesday);
        console.log(thursday);
        console.log(friday);
        
	    
	    users.forEach(function(user){
	    	user.thisWeek = [];
	    	console.log(user.thisWeek);
	    	
	    	user.attendance.forEach(function(attendance){
	    		console.log(attendance);
				day = new Date(attendance.date);
				console.log(day);
				if(day.getDate()==monday.getDate() && day.getMonth()==monday.getMonth()){
					thisWeek.monday = {};
					thisWeek.monday.verified = attendance.verified;
					thisWeek.monday.checkedIn = attendance.checkedIn;
					console.log("I am in monday");
				}
				if(day.getDate()==tuesday.getDate() && day.getMonth()==tuesday.getMonth()){
					thisWeek.tuesday = {};
					thisWeek.tuesday.verified = attendance.verified;
					thisWeek.tuesday.checkedIn = attendance.checkedIn;
					console.log("I am in tuesday");
				}
				if(day.getDate()==wednesday.getDate() && day.getMonth()==wednesday.getMonth()){
					thisWeek.wednesday = {};
					thisWeek.wednesday.verified = attendance.verified;
					thisWeek.wednesday.checkedIn = attendance.checkedIn;
					console.log("I am in wednesday");
				}
				if(day.getDate()==thursday.getDate() && day.getMonth()==thursday.getMonth()){
					thisWeek.thursday = {};
					thisWeek.thursday.verified = attendance.verified;
					thisWeek.thursday.checkedIn = attendance.checkedIn;
					console.log("I am in thursday");
					console.log(thisWeek.thursday.verified);
				}
				if(day.getDate()==friday.getDate() && day.getMonth()==friday.getMonth()){
					thisWeek.friday = {};
					thisWeek.friday.verified = attendance.verified;
					thisWeek.friday.checkedIn = attendance.checkedIn;
					console.log("I am in friday");
				}
				user.thisWeek[0] = thisWeek.monday;
				user.thisWeek[1] = thisWeek.tuesday;
				user.thisWeek[2] = thisWeek.wednesday;
				user.thisWeek[3] = thisWeek.thursday;
				user.thisWeek[4] = thisWeek.friday;
		    	console.log(user.thisWeek);
			})
			thisWeek.monday = null;
			thisWeek.tuesday = null;
			thisWeek.wednesday = null;
			thisWeek.thursday = null;
			thisWeek.friday = null;
	    	
	    })
		console.log(users);
		return users;
	}
});