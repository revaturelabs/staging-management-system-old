
    angular
        .module( "sms" )
        .controller( "associateAttendenceCtrl", associateAttendanceCtrl );
<<<<<<< HEAD

    function associateAttendanceCtrl( $mdDialog, $scope, $state, $filter, loginService, userService, weekdays ) {
=======
/**
 * @description AngularJs controller for associate attendance functionality.
 */
    function associateAttendanceCtrl( $mdDialog, $scope, $state, $filter, loginService, userService, weekdays ) {
       
       
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        var aac = this;

          // bindables
            // data
<<<<<<< HEAD
        aac.user = loginService.getUser();
        aac.curr = new Date();
        aac.today = aac.curr;
        aac.minWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() - 28 ); 
        aac.maxWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );

            // functions
        aac.calcWeek = calcWeek;
        aac.setToolbar = setToolbar;
        aac.openEvents = openEvents;
        aac.assocCertifications = assocCertifications;
        aac.getScheduledCert = getScheduledCert;
        aac.days_between = days_between;
        aac.isSameDate = isSameDate;
        aac.prevWeek = prevWeek;
        aac.nextWeek = nextWeek;
        aac.toast = toast;
        aac.checkIn = checkIn;

          // initialization
=======

        /**@prop {object} user Currently logged in user. */
        aac.user = loginService.getUser();
        /**@prop {object} curr Date for the current week being shown */
        aac.curr = new Date();
        /**@prop {object} today Todays current date. */
        aac.today = aac.curr;
        /**@prop {object} minWeek The earliest week that can be shown. */
        aac.minWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() - 28 ); 
         /**@prop {object} minWeek The latest week that can be shown. */
        aac.maxWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );

            // functions
        /**@var {function} calcWeek function reference variable. */
        aac.calcWeek = calcWeek;
         /**@var {function} setToolbar function reference variable. */
        aac.setToolbar = setToolbar;
         /**@var {function} openEvents function reference variable. */
        aac.openEvents = openEvents;
         /**@var {function} assocCertifications function reference variable. */
        aac.assocCertifications = assocCertifications;
         /**@var {function} getScheduledCert function reference variable. */
        aac.getScheduledCert = getScheduledCert;
        /**@var {function} getScheduledCert function reference variable. */
        aac.days_between = days_between;
        /**@var {function} getScheduledCert function reference variable. */
        aac.isSameDate = isSameDate;
        /**@var {function} getScheduledCert function reference variable. */
        aac.prevWeek = prevWeek;
        /**@var {function} getScheduledCert function reference variable. */
        aac.nextWeek = nextWeek;
        /**@var {function} getScheduledCert function reference variable. */
        aac.toast = toast;
        /**@var {function} getScheduledCert function reference variable. */
        aac.checkIn = checkIn;
        //aac.udateSkills = updateSkills;

        // initialization
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        aac.setToolbar();
        aac.calcWeek( aac.curr );
        if (getScheduledCert() != null) {
        	aac.toast(getScheduledCert());
        }
        
          // functions
            // returns list of date objects representing the week
<<<<<<< HEAD
=======

            /**
             * @description Creates a list of date objects to represent the week.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function calcWeek( date ) {
            
            var monday = new Date( date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 1 );
            aac.weekLabel = "Week of " + (monday.getMonth() + 1) + "/" + (monday.getDate());
            aac.week = [ { name: "Monday", date: monday } ];

            for (var i = 1; i < 5; i++) {
                var newDate = new Date( monday.getFullYear(), monday.getMonth(), monday.getDate() + i )
                aac.week.push( { name: weekdays[newDate.getDay()], date: newDate } );
            }

            aac.weekAttendance = $filter( "weekFilter" )( [aac.user], monday )[0].thisWeek;
            for (var j = 0; j < aac.week.length; j++) {
                if ( (aac.week[j].date.getTime() < aac.today.getTime()) && ( aac.weekAttendance[j] == undefined ) ) {
                    aac.weekAttendance[j] = {
                        verified: false,
                        checkedIn: false
                    }
                    aac.weekAttendance[j] = $filter( "iconFilter" )( aac.weekAttendance[j], "week" );
                }
            }
        }

          // SonarQube appeasement
<<<<<<< HEAD
=======

          /**
           * @description Changes the users status from CheckedIn to Absent. Not entirely sure why this is an option but it's here.
           */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function dialogYes() {
	    	//selected yes
	    	//checkout
	    	aac.x.checkedIn = false;
	    	userService.update(aac.user,function(){
	    		aac.toast("Checked out");
	    		aac.calcWeek( aac.curr );
	    		aac.setToolbar();
	    	});
		 }
<<<<<<< HEAD
		 
=======
		 /**
          * @description Method that runs when update was successful.
          */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function updateSuccess(){ 
			aac.toast("Successfully checked in.")
    		aac.calcWeek( aac.curr );
    		aac.setToolbar();
    	}
          //end sonarQube appeasement
<<<<<<< HEAD
        
=======
        /**
         * @description Determines if the logged in user has checked in today or not,
         * and adjusts an options to be added to the actiont toolbar accordingly.
         * @return {object} Option for the toolbar.
         */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function todayCheckedIn(){
        	var d = new Date();
        	for(var i=0; i< aac.user.attendance.length; i++){
        		var d2 = new Date(aac.user.attendance[i].date);
        		if(d.getDate() == d2.getDate() && d.getMonth() == d2.getMonth()){
        			if(aac.user.attendance[i].verified){
        				return null;
        			}
        			
        			if(aac.user.attendance[i].checkedIn){
        				//checked in
        				return {"function": aac.checkIn, "icon": "clear", "tooltip": "Mark as absent"};
        			}
        			else{
        				//not checked in
        				return {"function": aac.checkIn, "icon": "check", "tooltip": "Check in"};
        			}
        		}
        	}
        	
           	// day doesn't exist create new day
        	aac.user.attendance.push({
        		verified: false,
        		checkedIn: false,
        		date: new Date().getTime(),
        		note: "",
        		id: null
        		});
        	
        	userService.update(aac.user,function(){});
        	return {"function": aac.checkIn, "icon": "check", "tooltip": "Check in"};
        }

<<<<<<< HEAD
            // sets toobar icons and functions
=======
            
            /**
             * @description Sets the tooblar functions for an associate.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function setToolbar() {
        	var actions=[];
        	
        	var cin = todayCheckedIn();
        	if(cin != null){
        		actions.push(cin);
        	}

<<<<<<< HEAD
              // view all events
            actions.push({
                "function": aac.openEvents,
                "icon"    : "event_note",
                "tooltip" : "View events"
            })
              // schedule certification
            actions.push({ 
                "function": aac.assocCertifications, 
                "icon"    : "date_range", 
                "tooltip" : "Certifications"
=======
            actions.push({
                "function": aac.assocCertifications,
                "icon": "date_range",
                "tooltip": "Certifications"
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
            });

            $scope.$emit( "setToolbar", { 
                title: "Weekly attendance", 
                actions });
        }

            // opens dialog to display user events
<<<<<<< HEAD
=======
            /**
             * @description Opens the dialog to display events for the logged in associate.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function openEvents() {
            $mdDialog.show({
                templateUrl: "html/templates/viewEvents.html",
                controller: "associateViewEventsCtrl as aVECtrl",
                locals: { events: aac.user.events },
                bindToController: true,
                clickOutsideToClose: true,
                escapeToClose: true
            });
        }
<<<<<<< HEAD

        function assocCertifications() {
            if (getScheduledCert() == null) {
                $mdDialog.show({
                    templateUrl: "html/templates/scheduleCertification.html",
                    controller: "associateCertificationsCtrl as assCertCtrl"
                }).then( function() {
                    aac.toast("Certification Scheduled");
                }, function() {
                    aac.toast("Certification Schedule Cancelled");
                });
            }
            else {
                aac.toast("You can only schedule one certification at a time.");
            }
        }
        
        function days_between(date1, date2) {

            // The number of milliseconds in one day
            var ONE_DAY = 1000 * 60 * 60 * 24

            // Convert both dates to milliseconds
            var date1_ms = date1.getTime()
            var date2_ms = date2.getTime()

            // Calculate the difference in milliseconds
            var difference_ms = Math.abs(date1_ms - date2_ms)

            // Convert back to days and return
            return Math.round(difference_ms/ONE_DAY)

        }
        
=======
        
        /**
         * @description Determines the difference betwen the two supplied dates.
         * @param {date} date1 First supplied date.
         * @param {date} date2 Second supplied date.
         * @returns {number} Number of days between the two dates
         */
        function days_between(date1, date2) {

            // The number of milliseconds in one day
            var ONE_DAY = 1000 * 60 * 60 * 24;

            // Convert both dates to milliseconds
            var date1_ms = (new Date(date1.getFullYear(), date1.getMonth(), date1.getDate())).getTime();
            var date2_ms = (new Date(date2.getFullYear(), date2.getMonth(), date2.getDate())).getTime();

            // Calculate the difference in milliseconds
            var difference_ms = Math.abs(date1_ms - date2_ms);

            // Convert back to days and return
            return Math.round(difference_ms/ONE_DAY);

        }
        
        /**
         * @description Checks to see if the date supplied is the same date is the same as today.
         * @returns {boolean} Result
         */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function isSameDate(date) {
        	return (
        		aac.today.getFullYear() == date.getFullYear() &&
        		aac.today.getMonth() == date.getMonth() &&
        		aac.today.getDate() == date.getDate()
        	);
        }
        
<<<<<<< HEAD

          // if the user has a scheduled cert, return the formatted date of that cert, otherwise return null
=======
        function assocCertifications() {
            if (getScheduledCert() == null) {
                $mdDialog.show({
                    templateUrl: "html/templates/scheduleCertification.html",
                    controller: "associateCertificationsCtrl as assCertCtrl", 
                    clickOutsideToClose: true
                }).then( function() {
                    aac.toast("Certification Scheduled");
                }, function() {
                    //certification modal cancelled
                });
            }
            else {
                aac.toast("You can only schedule one certification at a time.");
            }
        }

          /**
           * @description Gets the date of the schedule certification if it exists.
           * @returns {string|null} Formatted date of the certification if it exists, null if one doesn't.
           */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function getScheduledCert() {
        	for(var i = 0; i < aac.user.tasks.length; i++) {
        		var certDate = new Date(aac.user.tasks[i].date);
        		var cert = "Certification";
<<<<<<< HEAD
        		if (aac.isSameDate(certDate)) {
        			return "Certification date is today.";
        		}
        		else if ( certDate.getTime() >= (new Date().getTime()) && (aac.user.tasks[i].taskType.type == cert) ) {
        			var daysAway = days_between(aac.today, certDate) + 1;
=======
        		if (aac.isSameDate(certDate) && (aac.user.tasks[i].taskType.type == cert) ) {
        			return "Certification date is today.";
        		}
        		else if ( certDate.getTime() >= (new Date().getTime()) && (aac.user.tasks[i].taskType.type == cert) ) {
        			var daysAway = days_between(aac.today, certDate);
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        			if (daysAway >= 14) {
        				return "Certification scheduled for: " +  ((certDate.getMonth()) + 1) + "/" + certDate.getDate() + "/" + certDate.getFullYear();
        			}
        			else if (daysAway < 14 && daysAway >= 2) {
        				return "Certification date is " + daysAway + " days away.";
        			}
        			else if (daysAway == 1) {
        				return "Certification date is tomorrow.";
        			}
        		}
        	}
        	return null;
        }

<<<<<<< HEAD
         // checks if previous week is before minimum date and resets week dates if not
=======
         /**
          * @description Goes to the previous week in the attendance display, unless the display is on the minimum
          * week already.
          */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function prevWeek() {
            var newDate = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() - 7 );
            if ( newDate.getTime() < aac.minWeek.getTime() ) {
                aac.toast( "Cannot view attendance older than four weeks." );
            } else {
                aac.curr = newDate;
                aac.calcWeek( aac.curr );
            }
        }

<<<<<<< HEAD
            // checks if next week is after maximum date and resets week dates if not
        function nextWeek() {
            var newDate = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );
            if ( newDate.getTime() > aac.maxWeek.getTime() ) {
                aac.toast( "Cannot view attendance in the future." );
=======
            
        /**
          * @description Goes to the next week in the attendance display, unless the display is on the maximum
          * week already.
          */
        function nextWeek() {
            var newDate = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );
            if ( newDate.getTime() > aac.maxWeek.getTime() ) {
                aac.toast( "Cannot view attendance more than 1 week in the future." );
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
            } else {
                aac.curr = newDate;
                aac.calcWeek( aac.curr );
            }
        }
        
<<<<<<< HEAD
        // marks an associate as checked in.
=======
        
        /**
         * @description Marks the associate as checked in, or shows a dialog asking if the associate wants to be marked as absent.
         */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function checkIn(){
        	var d = new Date();
        	//find current attendance object
        	for(var i=0; i< aac.user.attendance.length; i++){
        		var d2 = new Date(aac.user.attendance[i].date);
        		if(d.getDate() === d2.getDate() && d.getMonth() === d2.getMonth()){
        			//object found
        			
        			//not checked in
        			if(!aac.user.attendance[i].checkedIn){
        				//check in
        				aac.user.attendance[i].checkedIn = true;
        				userService.update(aac.user,updateSuccess);
        			}
        			// checked in
        			else{
        			    var confirm = $mdDialog.confirm()
        		          .title('Checkout')
        		          .textContent('Are you sure you want to mark yourself as absent?')
        		          .ok('Yes')
        		          .cancel('No');

        			    aac.x = aac.user.attendance[i];
        			    
	        		    $mdDialog.show(confirm).then(dialogYes);

        			}
				break;

        		}
        	}
        }

<<<<<<< HEAD
            // calls root-level toast function
=======
         /**
           * @description Displays a toast notification.
           * @param {string} message The value of the message to be shown.
           */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }
    }
