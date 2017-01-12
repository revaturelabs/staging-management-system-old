var sms = angular.module("sms");
sms.filter("weekFilter", function(){
	return function(users, monday){
		//filter associate attendance by current viewing monday
		var thisWeek = {};
		
		m = monday;
	    tuesday = new Date();
	    wednesday = new Date();
	    thursday = new Date();
	    friday = new Date();
	    
	    tuesday.setDate(m.getDate()+1);
	    wednesday.setDate(m.getDate()+2);
	    thursday.setDate(m.getDate()+3);
	    friday.setDate(m.getDate()+4);
	    
	    users.forEach(function(user){
	    	user.attendance.forEach(function(day){
				day = new Date(day);
				if(day.getDate()==monday.getDate() && day.getMonth()==monday.getMonth() && thisWeek.monday == null){
					thisWeek.monday = day;
				}
				if(day.getDate()==tuesday.getDate() && day.getMonth()==tuesday.getMonth()){
					thisWeek.tuesday = day;
				}
				if(day.getDate()==wednesday.getDate() && day.getMonth()==wednesday.getMonth()){
					thisWeek.wednesday = day;
				}
				if(day.getDate()==thursday.getDate() && day.getMonth()==thursday.getMonth()){
					thisWeek.thursday = day;
				}
				if(day.getDate()==friday.getDate() && day.getMonth()==friday.getMonth()){
					thisWeek.friday = day;
				}
			})
			user.thisWeek[0] = thisWeek.monday;
	    	user.thisWeek[1] = thisWeek.tuesday;
	    	user.thisWeek[2] = thisWeek.wednesday;
	    	user.thisWeek[3] = thisWeek.monday;
	    	user.thisWeek[4] = thisWeek.friday;
	    	
	    })
		
		return users;
	}
});