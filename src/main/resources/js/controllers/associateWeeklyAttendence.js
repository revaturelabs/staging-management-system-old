
    var sms = angular.module( "sms" );
    
    sms.controller( "associateWeeklyAttendenceCtrl", function( $scope, $state, loginService){
        
    	// refer to this controller
    	var ascatt = this;
    		
    	// create a list of all the days in the week
    	ascatt.days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
    	
    	//need this to hold which day of the week is witch 0 for sunday 6 for saturday etc
    	ascatt.daynumbercount = [0,1,2,3,4,5,6];
    	
        // get the logged in  user
        ascatt.user = loginService.getUser();
        
        // get the current ussers attendence and stores it in this variable
        var as = ascatt.user.attendance;

        //used as a legend to display what the icon data is
        ascatt.legend = [
            {name: 'done'  , color: "#00A", description: "if you checked in but were NOT yet verified" },
            {name: 'close', color: "#A00" , description: "if you were NOT checked in and NOT verified"},
            {name: 'done_all' , color: "rgb(89, 226, 168)" , description: "if you were verified" },
            {name: '    ' , color: "#777", description: "not available" }
         ];        
        
        // a list of all usable icons Global scope
        var iconData = [
            {name: 'done'  , color: "#00A" },
            {name: 'close', color: "#A00" },
            {name: 'done_all' , color: "rgb(89, 226, 168)" },
            {name: '     ' , color: "#777" }
         ];
        
        
        


        
        // pass in any date for a particular week 
        ascatt.changeweek = function(theDayForFunction){
        
        	// Global Scope Want to reset each time
            // used to save each day of the week was marked as
            ascatt.daypositionstring = [];
            
        	//set current day
        	var today = theDayForFunction;
        	
            //day is the day of the week
        	var day = today.getDate();
            
        	//w is the day of the month
        	var w = today.getDay();
            
            //set monday (m) based on what day of the week it is
        	var m = ((new Date(today.getFullYear(), today.getMonth(), today.getDate(),9,0,0,0)));
            
        	// find the last monday based on what day today is 
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
            
            //Should be Automativcally Global
            //set all days based on monday
            sunday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()-1),9,0,0,0);
            monday = m;
            tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1),9,0,0,0);
            wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2),9,0,0,0);
            thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3),9,0,0,0);
            friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4),9,0,0,0);
            saturday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+5),9,0,0,0);
            
            
            //Global regardless
            //set all scope days to print on top of table
            ascatt.sunday = (sunday.getMonth()+1)+"/"+sunday.getDate();
            ascatt.monday = (monday.getMonth()+1)+"/"+monday.getDate();
            ascatt.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
            ascatt.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
            ascatt.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
            ascatt.friday = (friday.getMonth()+1)+"/"+friday.getDate();
            ascatt.saturday = (saturday.getMonth()+1)+"/"+saturday.getDate();
            
            //Local Scope
            //holds the position of the 5 days -1 if it does not exist
            var daypositions = [-1,-1,-1,-1,-1,-1,-1];
            
            // loop through all of this users attendence and find this week
            for(var i  = 0; i < as.length; i++){
            	
            	// compared date if the date object for a users attendence
            	var comparedDate = new Date((as[i].date)); 
            	
            	// get the month number 
            	var comparedMonth = comparedDate.getMonth()+1;
            	
            	// get the day number
            	var comparedDayNumber = comparedDate.getDate();
            	
            	// get the year number
            	var comparedYear = comparedDate.getFullYear();
            	
            	// if the users attendence of this monday matches the monday we want
            	if((monday.getMonth()+1) == comparedMonth &&  monday.getDate() == comparedDayNumber && comparedYear == monday.getFullYear()){
            		
            		// save what the index of this day was from the users attendence list
            		daypositions[1] = i; 
            	}
            	// if the users attendence of this tuesday matches the monday we want
            	else if((tuesday.getMonth()+1) == comparedMonth &&  tuesday.getDate() == comparedDayNumber && comparedYear == tuesday.getFullYear()){
            	
            		// save what the index of this day was from the users attendence list
            		daypositions[2] = i;
            	}
            	// if the users attendence of this wednesday matches the monday we want
            	else if((wednesday.getMonth()+1) == comparedMonth &&  wednesday.getDate() == comparedDayNumber  && comparedYear == wednesday.getFullYear()){
            	
            		// save what the index of this day was from the users attendence list
            		daypositions[3] = i;
            	}
            	// if the users attendence of this thursday matches the monday we want
            	else if((thursday.getMonth()+1) == comparedMonth &&  thursday.getDate() == comparedDayNumber && comparedYear == thursday.getFullYear()){
            		
            		// save what the index of this day was from the users attendence list
            		daypositions[4] = i;
            	}
            	// if the users attendence of this friday matches the monday we want
            	else if((friday.getMonth()+1) == comparedMonth &&  friday.getDate() == comparedDayNumber && comparedYear == friday.getFullYear()){
            		
            		// save what the index of this day was from the users attendence list
            		daypositions[5] = i;
            	}
            }

        
         // for each of the chosen days
            for(var j  = 0; j < daypositions.length; j++){
            	
            	// if we didnt have an entry for that day in our database table
            	if(daypositions[j] == -1){
            		
            		// set this days icon data
            		ascatt.daypositionstring.push(iconData[3]);
            	}
            	// if you checked in and were verified or you were NOT checked in but were verified
            	else if((as[daypositions[j]].checkedIn && as[daypositions[j]].verified)||((!(as[daypositions[j]].checkedIn)) && as[daypositions[j]].verified)){
            		
            		// set this days icon data
            		ascatt.daypositionstring.push(iconData[2]);
                }
            	// if you checked in but were NOT yet verified
            	else if(as[daypositions[j]].checkedIn && (!(as[daypositions[j]].verified))){
            		
            		// set this days icon data
            		ascatt.daypositionstring.push(iconData[0]);
            	}
            	// if you were NOT checked in and NOT verified
            	else if((!(as[daypositions[j]].checkedIn)) && (!(as[daypositions[j]].verified))){
            		
            		// set this days icon data
            		ascatt.daypositionstring.push(iconData[1]);
            	}
            }
        };
        
        // set the week chart for this week by inputting today
        ascatt.changeweek(new Date());
        
        
        //set the week chart for the week after this week by getting the first day of the next week (sunday) 
        ascatt.getNextWeek = function() {
        	
        	// set a day from next week
        	var tomorrow = ((new Date(saturday.getFullYear(), saturday.getMonth(), (saturday.getDate()+1),9,0,0,0)));
        	
        	// call the function to set a new display week
        	ascatt.changeweek(tomorrow);
        };
        
        
        //set the  week chart for the week before this week by getting the last day of the last week (saturday)
        ascatt.getPreviousWeek = function() {
        	
        	//set a day from last week
        	var tomorrow = ((new Date(sunday.getFullYear(), sunday.getMonth(), (sunday.getDate()-1),9,0,0,0)));
        	
        	// call the function to set a new display week
        	ascatt.changeweek(tomorrow);
        };
        
    });