var sms = angular.module("sms");
sms.controller("adminAttendanceCtrl", function($scope, $state, userService, $filter) {
	var aac = this;
	
	aac.toast = function(message){
		$scope.$parent.$parent.mastCtrl.toast(message);
	};
	
	/*SET DATE HEADERS IN TABLE*/
	//set current day
	var today = new Date();
	today = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    var day = today.getDate();
    var w = today.getDay();
    /*day is the day of the month*/
    /*w is the day of the week*/
    
    
    /*set monday based on what day of the week it is*/
    var m = new Date();
    aac.activeDay = 1;
    if(w==0){
    	m.setDate(day+1);
    	aac.activeDay = 0;
    }
    if(w==2){
    	m.setDate(day-1);
    	aac.activeDay = 2;
    }
    if(w==3){
    	m.setDate(day-2);
    	aac.activeDay = 3;
    }
    if(w==4){
    	m.setDate(day-3);
    	aac.activeDay = 4;
    }
    if(w==5){
    	m.setDate(day-4);
    	aac.activeDay = 5;
    }
    if(w==6){
    	m.setDate(day-5);
    	aac.activeDay = 6;
    }
    /*set global monday for day week change functions*/
    aac.thisCurrentMonday = m;
    
    /*set all days based on monday*/
    var setMonday = new Date();
    setMonday.setDate(m.getDate());
    aac.thisMonday = m;
    aac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
    aac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
    aac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
    aac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
    
    /*set all scope days to print on top of table*/
    console.log(aac.thisMonday);
    aac.monday = (aac.thisMonday.getMonth()+1)+"/"+aac.thisMonday.getDate();
    aac.tuesday = (aac.thisTuesday.getMonth()+1)+"/"+aac.thisTuesday.getDate();
    aac.wednesday = (aac.thisWednesday.getMonth()+1)+"/"+aac.thisWednesday.getDate();
    aac.thursday = (aac.thisThursday.getMonth()+1)+"/"+aac.thisThursday.getDate();
    aac.friday = (aac.thisFriday.getMonth()+1)+"/"+aac.thisFriday.getDate();
    
	//get all attendance for the week for all associates
    
    /*aac.allThisWeekAttendance = [];*/
    
    userService.getAll(function(response){
    	
    	aac.users = $filter("associateFilter")(response);
    	console.log(aac.users);
    	aac.users = $filter("weekFilter")(aac.users, aac.thisMonday);
    	console.log(aac.users);
    	
    	
    }, function(error){
    	aac.toast("Error in retrieving all associates.");
    });
    
    
    
    //create a confirm function
    $scope.verifyAttendance = function(user, w){
    	thisDay = aac.thisMonday;
    	if(w==2){
        	thisDay = aac.thisTuesday;
        }
        if(w==3){
        	thisDay = aac.thisWednesday;
        }
        if(w==4){
        	thisDay = aac.thisThursday;
        }
        if(w==5){
        	thisDay = aac.thisFriday;
        }
    	
        user.attendance.forEach(function(attendance){
    		console.log(attendance);
			day = new Date(attendance.date);
			console.log(day);
			if(day.getDate()==thisDay.getDate() && day.getMonth()==thisDay.getMonth()){
				attendance.verified = true;
				attendance.checkedIn = true;
			}
		})
    	
    	userService.update(user, function(response){
    		aac.toast("Successful update");
    	}, function(error){
    		aac.toast("Error updating user attendance");
    	})
    	
    }
    
    
    
    
    
	//change week functions
	$scope.goBackOneWeek = function() {
		/*set the new week up*/
		m = new Date();
        m.setFullYear(setMonday.getFullYear(), setMonday.getMonth(), (setMonday.getDate()-7));
        
        setMonday.setFullYear(m.getFullYear(), m.getMonth(), m.getDate());
        aac.thisMonday = m;
        aac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        aac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        aac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        aac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
        
        /*set all scope days to print on top of table*/
        aac.monday = (aac.thisMonday.getMonth()+1)+"/"+aac.thisMonday.getDate();
        aac.tuesday = (aac.thisTuesday.getMonth()+1)+"/"+aac.thisTuesday.getDate();
        aac.wednesday = (aac.thisWednesday.getMonth()+1)+"/"+aac.thisWednesday.getDate();
        aac.thursday = (aac.thisThursday.getMonth()+1)+"/"+aac.thisThursday.getDate();
        aac.friday = (aac.thisFriday.getMonth()+1)+"/"+aac.thisFriday.getDate();
        
        /*make data change for new week*/
        aac.users = $filter("weekFilter")(aac.users, aac.thisMonday);
        
        /*setting active days*/
        /*remove active day*/
        aac.activeDay = null;
        
        /*see if this week is the active day week*/
        if(aac.thisCurrentMonday.getDate()==aac.thisMonday.getDate() && aac.thisCurrentMonday.getMonth()==aac.thisMonday.getMonth()){
        	aac.activeDay = w;
        }
        
    };
    
    $scope.goForwardOneWeek = function() {
    	/*set the new week up*/
        m = new Date();
        m.setFullYear(setMonday.getFullYear(), setMonday.getMonth(), (setMonday.getDate()+7));
        
        setMonday.setFullYear(m.getFullYear(), m.getMonth(), m.getDate());
        aac.thisMonday = m;
        aac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        aac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        aac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        aac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
        
        /*set all scope days to print on top of table*/
        aac.monday = (aac.thisMonday.getMonth()+1)+"/"+aac.thisMonday.getDate();
        aac.tuesday = (aac.thisTuesday.getMonth()+1)+"/"+aac.thisTuesday.getDate();
        aac.wednesday = (aac.thisWednesday.getMonth()+1)+"/"+aac.thisWednesday.getDate();
        aac.thursday = (aac.thisThursday.getMonth()+1)+"/"+aac.thisThursday.getDate();
        aac.friday = (aac.thisFriday.getMonth()+1)+"/"+aac.thisFriday.getDate();
        
        /*make data change for new week*/
        aac.users = $filter("weekFilter")(aac.users, aac.thisMonday);
        
        /*setting active days*/
        /*remove active day*/
        aac.activeDay = null;
        
        /*see if this week is the active day week*/
        if(aac.thisCurrentMonday.getDate()==aac.thisMonday.getDate() && aac.thisCurrentMonday.getMonth()==aac.thisMonday.getMonth()){
        	aac.activeDay = w;
        }
        
    };
});