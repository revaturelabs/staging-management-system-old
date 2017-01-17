var sms = angular.module("sms");
sms.controller("adminAttendanceCtrl", function($scope, $state, userService, $filter) {
	var aac = this;
	
	aac.toast = function(message){
		$scope.$parent.$parent.mastCtrl.toast(message);
	};
	
	/*This block sets the DATE HEADERS IN TABLE*/
	//set current day
	var today = new Date();
	//Remove the timestamp from the object, just need the date
	today = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    var day = today.getDate();
    var w = today.getDay();
    //day is the day of the month
    //w is the day of the week
    
    
    // the following block sets monday based on what day of the week it is
    var m = new Date();
    //set an active day for week change functions for the column highlighting
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
        
    //set global monday for day week change functions
    aac.thisCurrentMonday = m;
    
    //set all days of the week based on monday
    var setMonday = new Date();
    setMonday.setDate(m.getDate());
    aac.thisMonday = m;
    aac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
    aac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
    aac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
    aac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
    
    //data bind all scope days to print on top of table
    aac.monday = (aac.thisMonday.getMonth()+1)+"/"+aac.thisMonday.getDate();
    aac.tuesday = (aac.thisTuesday.getMonth()+1)+"/"+aac.thisTuesday.getDate();
    aac.wednesday = (aac.thisWednesday.getMonth()+1)+"/"+aac.thisWednesday.getDate();
    aac.thursday = (aac.thisThursday.getMonth()+1)+"/"+aac.thisThursday.getDate();
    aac.friday = (aac.thisFriday.getMonth()+1)+"/"+aac.thisFriday.getDate();
    
    /* this is the end of the setting up the week block */
    
    
    
	/*get all attendance for the week for all associates*/
    
    //run user service to get all users
    userService.getAll(function(response){
    	
    	//in the response filter out users that aren't associates
    	aac.users = $filter("associateFilter")(response);
    	//filter the associates to get the date objects that are only for the current week
    	aac.users = $filter("weekFilter")(aac.users, aac.thisMonday);
    	
    }, function(error){
    	aac.toast("Error in retrieving all associates.");
    });
    
    /*this is the end of getting the users*/
    
    /*create a legend for the table symbols*/
    
    //used as a legend to display what the icon data is
    aac.legend = [
    	{name: 'check_circle'  , color: "orange", description: "click to verify attendance" },
        {name: 'done'  , color: "#00A", description: "if the associate checked in but has NOT yet been verified" },
        {name: 'close', color: "#A00" , description: "if the associate is NOT checked in and NOT verified"},
        {name: 'done_all' , color: "rgb(89, 226, 168)" , description: "if the associate's attendance has been verified" },

        {name: '    ' , color: "#777", description: "no information available yet" }

     ]; 
    
    /*end of legend creation*/
    
    
    /*create a verify attendance function*/
    
    $scope.verifyAttendance = function(user, selectedDay){
    	//figure out which day was clicked
    	thisDay = aac.thisMonday;
    	if(selectedDay==1){
        	thisDay = aac.thisTuesday;
        }
        if(selectedDay==2){
        	thisDay = aac.thisWednesday;
        }
        if(selectedDay==3){
        	thisDay = aac.thisThursday;
        }
        if(selectedDay==4){
        	thisDay = aac.thisFriday;
        }
    	
        //get the attendance object that matches the clicked on day
        //accomplish this by doing a for each loop that looks through each object
        //set a variable that varifies the object has been updated
        updated = false;
        user.attendance.forEach(function(attendance){
			day = new Date(attendance.date);
			if(day.getDate()==thisDay.getDate() && day.getMonth()==thisDay.getMonth()){
				//set the status to true on the object
				attendance.verified = true;
				attendance.checkedIn = true;
				updated = true;
			}
		})
    	
		//if the object wwasn't updated then the object will be created
		if(!updated){
			user.attendance.date = new Date();
			user.attendance.verified = true;
			user.attendance.checkedIn = true;
			user.attendance.note = "Checked in and validated by admin";
		}
		
		//call user service to send the update to the database
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
        
        /*filter the week so only the current week is visible*/
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
        
        /*filter the week so only the current week is visible*/
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