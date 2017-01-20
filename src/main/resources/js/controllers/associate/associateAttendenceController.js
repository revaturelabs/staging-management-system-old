
    angular
        .module( "sms" )
        .controller( "associateAttendenceCtrl", associateAttendanceCtrl );
        
    function associateAttendanceCtrl( $scope, $state, $filter, loginService, weekdays ) {
        var aac = this;

          // bindables
            // data
        aac.user = loginService.getUser();
        aac.curr = new Date();
        aac.today = aac.curr;
        aac.minWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() - 21 ); 
        aac.maxWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );

            // functions
        aac.calcWeek = calcWeek;
        aac.setToolbar = setToolbar;
        aac.prevWeek = prevWeek;
        aac.nextWeek = nextWeek;
        aac.toast = toast;

          // initialization
        aac.calcWeek( aac.curr );
        aac.setToolbar();

          // functions
            // returns list of date objects representing the week
        function calcWeek( date ) {
            
            var monday = new Date( date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 1 );
            aac.weekLabel = "Week of " + (monday.getMonth() + 1) + "/" + (monday.getDate());
            aac.week = [ { name: "Monday", date: monday } ];

            for (var i = 1; i < 5; i++) {
                var newDate = new Date( monday.getFullYear(), monday.getMonth(), monday.getDate() + i )
                aac.week.push( { name: weekdays[newDate.getDay()], date: newDate } );
            }

            aac.weekAttendance = $filter( "weekFilter" )( [aac.user], monday )[0].thisWeek;
            for (var i = 0; i < aac.week.length; i++) {
                if ( aac.week[i].date.getTime() < aac.today.getTime() ) {
                    if ( aac.weekAttendance[i] == undefined ) {
                        aac.weekAttendance[i] = {
                            verified: false,
                            checkedIn: false
                        }
                        aac.weekAttendance[i] = $filter( "iconFilter" )( aac.weekAttendance[i] );
                    }
                }
            }
        };

            // sets toobar icons and functions
        function setToolbar() {
            $scope.$emit( "setToolbar", { title: "Weekly attendance", actions: {} } );
        }

            // checks if previous week is before minimum date and resets week dates if not
        function prevWeek() {
            var newDate = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() - 7 );
            if ( newDate.getTime() < aac.minWeek.getTime() ) {
                aac.toast( "Cannot view attendance older than four weeks." );
            } else {
                aac.curr = newDate;
                aac.calcWeek( aac.curr );
            }
        }

            // checks if next week is after maximum date and resets week dates if not
        function nextWeek() {
            var newDate = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );
            if ( newDate.getTime() > aac.maxWeek.getTime() ) {
                aac.toast( "Cannot view attendance in the future." );
            } else {
                aac.curr = newDate;
                aac.calcWeek( aac.curr );
            }
        }

            // calls root-level toast function
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }
    };

    	// // refer to this controller
    	// var ascatt = this;
    	
    	// //From associateController.js
    	
        // ascatt.user = loginService.getUser();

        // ascatt.toast = function(message){
        //     $scope.$emit( "toastMessage", message );
        // };

        // ascatt.logout = function() {
        //     ascatt.user = {};
        //     loginService.logout();
        //     ascatt.toast("Logged out.");
        //     $state.go("login");
        // };

        // function menu() {
        //     $scope.$emit( "changeFunction", { 
        //         title: "Weekly Attendance", 
        //         actions: [ {
        //             "function": ascatt.openMenu(),
        //             "icon"    : "alarm",
        //             "tooltip" : "Testing"
        //         }]
        //     });
        // }

        //     // functions
        // ascatt.openMenu = function() {
        //     $mdSidenav("left").open();
        // };

        // menu();

        // // ascatt.logout = function() {
        // //     ascatt.user = {};
        // //     ascatt.token = "";
        // //     loginService.logout();
        // //     ascatt.toast("Logged out.");
        // //     $state.go("login");
        // // };
        
        // ascatt.updateInformation = function(){
        //     $mdSidenav("left").close();
        //     $state.go("ASupdateInfo");
            
        // };

        // ascatt.associateAttendance= function(){
        //     $mdSidenav("left").close();
        //     $state.go("assocAttendance");
            
        // };
        
        //     // data
        // ascatt.user = loginService.getUser();
        
    	// // End from associateController.js
    	
    	
    	// //add title to scope
    	// $scope.tempCtrl.title = "Weekly Attendance";
    	
    	// //used to get todays date
    	// //var d = new Date();
    		
    	// // create a list of all the days in the week
    	// ascatt.days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
    	
    	// //need this to hold which day of the week is witch 0 for sunday 6 for saturday etc
    	// ascatt.daynumbercount = [0,1,2,3,4,5,6];
    	
    	// // TODO test to change text color by day of week doesnt work 
    	
    	// //test
    	// ascatt.firstname = "John";	
    	// /*//test
    	// ascatt.$parent.mastCtrl.toast("Here");*/
        
        // // get the logged in  user
        // ascatt.user = loginService.getUser();
        
        // // get the current ussers attendence and stores it in this variable
        // var as = ascatt.user.attendance;
        
       
        
        // //999999999999999999999999999999999999999999999999999999999999
        // //set current day
    	// var today = new Date();
    	
        // //day is the day of the week
    	// var day = today.getDate();
        
    	// //w is the day of the month
    	// var w = today.getDay();
        
        // //set monday (m) based on what day of the week it is
        // var m = new Date();
        
        // // find the last monday based on what day today is 
        // if(w==0){
        // 	m.setDate(day+1);
        // }
        // if(w==2){
        // 	m.setDate(day-1);
        // }
        // if(w==3){
        // 	m.setDate(day-2);
        // }
        // if(w==4){
        // 	m.setDate(day-3);
        // }
        // if(w==5){
        // 	m.setDate(day-4);
        // }
        // if(w==6){
        // 	m.setDate(day-5);
        // }
        
        // //set all days based on monday
        // sunday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()-1),9,0,0,0);
        // monday = m;
        // tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1),9,0,0,0);
        // wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2),9,0,0,0);
        // thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3),9,0,0,0);
        // friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4),9,0,0,0);
        // saturday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+5),9,0,0,0);
        
        // //test
        // //set all scope days to print on top of table
        // ascatt.sunday = (sunday.getMonth()+1)+"/"+sunday.getDate();
        // ascatt.monday = (monday.getMonth()+1)+"/"+monday.getDate();
        // ascatt.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
        // ascatt.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
        // ascatt.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
        // ascatt.friday = (friday.getMonth()+1)+"/"+friday.getDate();
        // ascatt.saturday = (saturday.getMonth()+1)+"/"+saturday.getDate();
        // //99999999999999999999999999999999999999999999999999999999999
        
        // //holds the position of the 5 days -1 if it does not exist
        // var daypositions = [-1,-1,-1,-1,-1,-1,-1];
        
        // // loop through all of this users attendence and find this week
        // for(var i  = 0; i < as.length; i++){
        	
        // 	// compared date if the date object for a users attendence
        // 	var comparedDate = new Date((as[i].date)); 
        	
        // 	// get the month number 
        // 	var comparedMonth = comparedDate.getMonth()+1;
        	
        // 	// get the day number
        // 	var comparedDayNumber = comparedDate.getDate();
        	
        // 	// get the year number
        // 	var comparedYear = comparedDate.getFullYear();
        	
        // 	// if the users attendence of this monday matches the monday we want
        // 	if((monday.getMonth()+1) == comparedMonth &&  monday.getDate() == comparedDayNumber && comparedYear == monday.getFullYear()){
        		
        // 		// save what the index of this day was from the users attendence list
        // 		daypositions[1] = i; 
        // 	}
        // 	// if the users attendence of this tuesday matches the monday we want
        // 	else if((tuesday.getMonth()+1) == comparedMonth &&  tuesday.getDate() == comparedDayNumber && comparedYear == tuesday.getFullYear()){
        	
        // 		// save what the index of this day was from the users attendence list
        // 		daypositions[2] = i;
        // 	}
        // 	// if the users attendence of this wednesday matches the monday we want
        // 	else if((wednesday.getMonth()+1) == comparedMonth &&  wednesday.getDate() == comparedDayNumber  && comparedYear == wednesday.getFullYear()){
        	
        // 		// save what the index of this day was from the users attendence list
        // 		daypositions[3] = i;
        // 	}
        // 	// if the users attendence of this thursday matches the monday we want
        // 	else if((thursday.getMonth()+1) == comparedMonth &&  thursday.getDate() == comparedDayNumber && comparedYear == thursday.getFullYear()){
        		
        // 		// save what the index of this day was from the users attendence list
        // 		daypositions[4] = i;
        // 	}
        // 	// if the users attendence of this friday matches the monday we want
        // 	else if((friday.getMonth()+1) == comparedMonth &&  friday.getDate() == comparedDayNumber && comparedYear == friday.getFullYear()){
        		
        // 		// save what the index of this day was from the users attendence list
        // 		daypositions[5] = i;
        // 	}
        // }
        
   
        

    
        // // used to save each day of the week was marked as
        // ascatt.daypositionstring = [];
        
        // // a list of all usable icons
        // var iconData = [
        //     {name: 'done'  , color: "#00A" },
        //     {name: 'close', color: "#A00" },
        //     {name: 'done_all' , color: "rgb(89, 226, 168)" },

        //     {name: '     ' , color: "#777" }

        //  ];
        
        // //used as a legend to display what the icon data is
        // ascatt.legend = [
        //     {name: 'done'  , color: "#00A", description: "if you checked in but were NOT yet verified" },
        //     {name: 'close', color: "#A00" , description: "if you were NOT checked in and NOT verified"},
        //     {name: 'done_all' , color: "rgb(89, 226, 168)" , description: "if you were verified" },

        //     {name: '    ' , color: "#777", description: "not available" }

        //  ]; 
        
        // // for each of the chosen days
        // for(var j  = 0; j < daypositions.length; j++){
        	
        // 	// if we didnt have an entry for that day in our database table
        // 	if(daypositions[j] == -1){
        		
        		
        		
        // 		// set this days icon data
        // 		ascatt.daypositionstring.push(iconData[3]);
        // 	}
        // 	// if you checked in and were verified or you were NOT checked in but were verified
        // 	else if((as[daypositions[j]].checkedIn && as[daypositions[j]].verified)||((!(as[daypositions[j]].checkedIn)) && as[daypositions[j]].verified)){
        // 		//TODO return a proper string
 
        		
        // 		// set this days icon data
        // 		ascatt.daypositionstring.push(iconData[2]);
        //     }
        // 	// if you checked in but were NOT yet verified
        // 	else if(as[daypositions[j]].checkedIn && (!(as[daypositions[j]].verified))){
        		
        	
        		
        // 		// set this days icon data
        // 		ascatt.daypositionstring.push(iconData[0]);
        // 	}
        // 	// if you were NOT checked in and NOT verified
        // 	else if((!(as[daypositions[j]].checkedIn)) && (!(as[daypositions[j]].verified))){
        		
        		
        // 		// set this days icon data
        // 		ascatt.daypositionstring.push(iconData[1]);
        // 	}
        // }
        
        // //***************************************************************
    // });