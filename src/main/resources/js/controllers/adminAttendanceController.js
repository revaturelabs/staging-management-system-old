var sms = angular.module("sms");
sms.controller("adminAttendanceCtrl", function($scope, $state, userService, $filter) {
	var aac = this;
	
	aac.toast = function(message){
		$scope.$parent.$parent.mastCtrl.toast(message);
	};
	
	/*SET DATE HEADERS IN TABLE*/
	//set current day
	var today = new Date();
    var day = today.getDate();
    var w = today.getDay();
    /*day is the day of the month*/
    /*w is the day of the week*/
    
    
    /*set monday based on what day of the week it is*/
    var m = new Date();
    if(w==0){
    	m.setDate(day+1);
    }
    if(w==2){
    	m.setDate(day-1);
    }
    if(w==3){
    	m.setDate(day-2);
    }
    if(w==4){
    	m.setDate(day-3);
    }
    if(w==5){
    	m.setDate(day-4);
    }
    if(w==6){
    	m.setDate(day-5);
    }
    
    /*set all days based on monday*/
    monday = m;
    tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1),9,0,0,0);
    wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2),9,0,0,0);
    thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3),9,0,0,0);
    friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4),9,0,0,0);
    
    /*set all scope days to print on top of table*/
    console.log(monday);
    aac.monday = (monday.getMonth()+1)+"/"+monday.getDate();
    aac.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
    aac.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
    aac.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
    aac.friday = (friday.getMonth()+1)+"/"+friday.getDate();
    
	//get all attendance for the week for all associates
    
    var allThisWeekAttendance = [];
    
    userService.getAll(function(response){
    	
    	aac.users = $filter("associateFilter")(response);
    	aac.users.forEach(function(user){
    		thisWeekAttendance = $filter("weekFilter")(user, monday);
    		allThisWeekAttendance.push(thisWeekAttendance);
    	});
    	
    }, function(error){
    	aac.toast("Error in retrieving all associates.");
    });
    
    
    
    //create a confirm function
    
    
	//change week
	
});