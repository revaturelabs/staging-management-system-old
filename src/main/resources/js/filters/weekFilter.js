var sms = angular.module("sms");
sms.filter("weekFilter", function(){
	return function(user, monday){
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
	    
		user.attendance.forEach(function(day){
			day = new Date(day);
			if(day.getDate()==monday.getDate() && day.getMonth()==monday.getMonth()){
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
		return thisWeek;
	}
});