
    var sms = angular.module( "sms" );
    sms.controller( "managerAttendanceCtrl", function($scope, $state, userService, $filter){
    	var mac = this;
    	
    	// $scope.$parent.suCtrl.title = "Associate Weekly Attendance";

        $scope.$emit( "changeFunction", { title: "Test", actions: {} });

    	
    	mac.toast = function(message){
    		// $scope.$parent.$parent.mastCtrl.toast(message);
            $scope.$on( "toastMessage", message );
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
        mac.activeDay = 1;
        mac.activeWeek = true;
        if(w==0){
        	m.setDate(day+1);
        	mac.activeDay = 0;
        }
        if(w==2){
        	m.setDate(day-1);
        	mac.activeDay = 2;
        }
        if(w==3){
        	m.setDate(day-2);
        	mac.activeDay = 3;
        }
        if(w==4){
        	m.setDate(day-3);
        	mac.activeDay = 4;
        }
        if(w==5){
        	m.setDate(day-4);
        	mac.activeDay = 5;
        }
        if(w==6){
        	m.setDate(day-5);
        	mac.activeDay = 6;
        }
            
        //set global monday for day week change functions
        mac.thisCurrentMonday = m;
        
        //set all days of the week based on monday
        var setMonday = new Date();
        setMonday.setDate(m.getDate());
        mac.thisMonday = m;
        mac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        mac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        mac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        mac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
        
        //data bind all scope days to print on top of table
        mac.monday = (mac.thisMonday.getMonth()+1)+"/"+mac.thisMonday.getDate();
        mac.tuesday = (mac.thisTuesday.getMonth()+1)+"/"+mac.thisTuesday.getDate();
        mac.wednesday = (mac.thisWednesday.getMonth()+1)+"/"+mac.thisWednesday.getDate();
        mac.thursday = (mac.thisThursday.getMonth()+1)+"/"+mac.thisThursday.getDate();
        mac.friday = (mac.thisFriday.getMonth()+1)+"/"+mac.thisFriday.getDate();
        
        /* this is the end of the setting up the week block */
        
        
        
    	/*get all attendance for the week for all associates*/
        
        //run user service to get all users
        userService.getAll(function(response){
        	
        	//in the response filter out users that aren't associates
        	mac.users = $filter("associateFilter")(response);
        	//filter the associates to get the date objects that are only for the current week
        	mac.users = $filter("weekFilter")(mac.users, mac.thisMonday);
        	
        }, function(error){
        	mac.toast("Error in retrieving all associates.");
        });
        
        /*this is the end of getting the users*/
        
        /*create a legend for the table symbols*/
        
        //used as a legend to display what the icon data is
        mac.legend = [
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
        	thisDay = mac.thisMonday;
        	if(selectedDay==1){
            	thisDay = mac.thisTuesday;
            }
            if(selectedDay==2){
            	thisDay = mac.thisWednesday;
            }
            if(selectedDay==3){
            	thisDay = mac.thisThursday;
            }
            if(selectedDay==4){
            	thisDay = mac.thisFriday;
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
    			newAttendace = {};
    			
    			newAttendace.date = thisDay;
    			newAttendace.verified = true;
    			newAttendace.checkedIn = true;
    			newAttendace.note = "Checked in and validated by admin";
    			
    			user.attendance.push(newAttendace);
    		}
    		
    		//call user service to send the update to the database
        	userService.update(user, function(response){
        		mac.toast("Successful update");
        		mac.users = $filter("weekFilter")(mac.users, mac.thisMonday);
        	}, function(error){
        		mac.toast("Error updating user attendance");
        	})
        	
        }
        
        
        
        
        
    	/*change week functions*/
        //make a scope variable that holds the week number, so they can only go forward and back 2 weeks
         var weekNumber = 4;
        
    	$scope.goBackOneWeek = function() {
    		
    		//make sure user can't go back 2 weeks
    		if(weekNumber > 0){
    			mac.activeWeek = false;
    			weekNumber -= 1;
    			
    			/*set the new week up*/
    			m = new Date();
    	        m.setFullYear(setMonday.getFullYear(), setMonday.getMonth(), (setMonday.getDate()-7));
    	        
    	        setMonday.setFullYear(m.getFullYear(), m.getMonth(), m.getDate());
    	        mac.thisMonday = m;
    	        mac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
    	        mac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
    	        mac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
    	        mac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
    	        
    	        /*set all scope days to print on top of table*/
    	        mac.monday = (mac.thisMonday.getMonth()+1)+"/"+mac.thisMonday.getDate();
    	        mac.tuesday = (mac.thisTuesday.getMonth()+1)+"/"+mac.thisTuesday.getDate();
    	        mac.wednesday = (mac.thisWednesday.getMonth()+1)+"/"+mac.thisWednesday.getDate();
    	        mac.thursday = (mac.thisThursday.getMonth()+1)+"/"+mac.thisThursday.getDate();
    	        mac.friday = (mac.thisFriday.getMonth()+1)+"/"+mac.thisFriday.getDate();
    	        
    	        /*filter the week so only the current week is visible*/
    	        mac.users = $filter("weekFilter")(mac.users, mac.thisMonday);
    	        
    	        /*setting active days*/
    	        /*remove active day*/
    	        mac.activeDay = null;
    	        
    	        /*see if this week is the active day week*/
    	        if(mac.thisCurrentMonday.getDate()==mac.thisMonday.getDate() && mac.thisCurrentMonday.getMonth()==mac.thisMonday.getMonth()){
    	        	mac.activeDay = w;
    	        }
    		}
    		else{
    			mac.toast("Can't go back more than 4 weeks");
    		}
        };
        
        $scope.goForwardOneWeek = function() {
    	    
        	//make sure user can't go beyond the present week 
    		if(weekNumber < 4){
    		
    			weekNumber += 1;
    			if(weekNumber == 4){
    				mac.activeWeek = true;
    			}
        	
        		/*set the new week up*/
    	        m = new Date();
    	        m.setFullYear(setMonday.getFullYear(), setMonday.getMonth(), (setMonday.getDate()+7));
    	        
    	        setMonday.setFullYear(m.getFullYear(), m.getMonth(), m.getDate());
    	        mac.thisMonday = m;
    	        mac.thisTuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
    	        mac.thisWednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
    	        mac.thisThursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
    	        mac.thisFriday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
    	        
    	        /*set all scope days to print on top of table*/
    	        mac.monday = (mac.thisMonday.getMonth()+1)+"/"+mac.thisMonday.getDate();
    	        mac.tuesday = (mac.thisTuesday.getMonth()+1)+"/"+mac.thisTuesday.getDate();
    	        mac.wednesday = (mac.thisWednesday.getMonth()+1)+"/"+mac.thisWednesday.getDate();
    	        mac.thursday = (mac.thisThursday.getMonth()+1)+"/"+mac.thisThursday.getDate();
    	        mac.friday = (mac.thisFriday.getMonth()+1)+"/"+mac.thisFriday.getDate();
    	        
    	        /*filter the week so only the current week is visible*/
    	        mac.users = $filter("weekFilter")(mac.users, mac.thisMonday);
    	        
    	        /*setting active days*/
    	        /*remove active day*/
    	        mac.activeDay = null;
    	        
    	        /*see if this week is the active day week*/
    	        if(mac.thisCurrentMonday.getDate()==mac.thisMonday.getDate() && mac.thisCurrentMonday.getMonth()==mac.thisMonday.getMonth()){
    	        	mac.activeDay = w;
    	        }
    		}
            else{
    			mac.toast("Can't go to future weeks");
    		}
        };
    });