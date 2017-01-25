
    angular
        .module( "sms" )
        .controller( "associateAttendenceCtrl", associateAttendanceCtrl );
        

    function associateAttendanceCtrl( $mdDialog, $scope, $state, $filter, loginService, userService, weekdays ) {

        var aac = this;

        // bindables
        // data
        aac.user = loginService.getUser();
        aac.curr = new Date();
        aac.today = aac.curr;
        aac.minWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() - 28 ); 
        aac.maxWeek = new Date( aac.curr.getFullYear(), aac.curr.getMonth(), aac.curr.getDate() + 7 );

        // functions
        aac.calcWeek = calcWeek;
        aac.setToolbar = setToolbar;
        aac.assocCertifications = assocCertifications;
        aac.getScheduledCert = getScheduledCert;
        aac.days_between = days_between;
        aac.isSameDate = isSameDate;
        aac.prevWeek = prevWeek;
        aac.nextWeek = nextWeek;
        aac.toast = toast;
        aac.checkIn = checkIn;

        // initialization
        aac.setToolbar();
        aac.calcWeek( aac.curr );
        
        
        if (getScheduledCert() != null) {
        	aac.toast(getScheduledCert());
        }
        

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
            for (var j = 0; j < aac.week.length; j++) {
                if ( aac.week[j].date.getTime() < aac.today.getTime() &&  aac.weekAttendance[j] == undefined) {
                  
                        aac.weekAttendance[j] = {
                            verified: false,
                            checkedIn: false
                        }
                        aac.weekAttendance[j] = $filter( "iconFilter" )( aac.weekAttendance[j] );
                   
                }
            }
        }

        function todayCheckedIn(){
        	var d = new Date();
        	for(var i=0; i< aac.user.attendance.length; i++){
        		var d2 = new Date(aac.user.attendance[i].date);
        		if(d.getDate() === d2.getDate() && d.getMonth() === d2.getMonth()){
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
/*        	aac.user.attendance.push({
                verified: false,
                checkedIn: false
            });
        	console.log(new Date().getTime());
        	console.log(aac.user.attendance);
        	
        	userService.update(aac.user,function(){},function(){});
			return {"function": aac.checkIn, "icon": "check", "tooltip": "Check in"};*/

        }
            // sets toobar icons and functions
        function setToolbar() {

        	
        	var cin = todayCheckedIn();
        	

            $scope.$emit( "setToolbar", { title: "Weekly attendance", actions: [cin,{ "function": aac.assocCertifications, "icon": "date_range", "tooltip": "Certifications"}] } );
        }

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
        
        function isSameDate(date) {
        	return (
        		aac.today.getFullYear() == date.getFullYear() &&
        		aac.today.getMonth() == date.getMonth() &&
        		aac.today.getDate() == date.getDate()
        	);
        }
        

        //If the user has a scheduled cert, return the formatted date of that cert, otherwise return null
        function getScheduledCert() {
        	for(var i = 0; i < aac.user.tasks.length; i++) {
        		var certDate = new Date(aac.user.tasks[i].date);
        		var cert = "Certification";
        		if (aac.isSameDate(certDate)) {
        			return "Certification date is today.";
        		}
        		else if ( certDate.getTime() >= (new Date().getTime()) && (aac.user.tasks[i].taskType.type == cert) ) {
        			var daysAway = days_between(aac.today, certDate) + 1;
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
        
        // marks an associate as checked in.
        function checkIn(){
        	var d = new Date();
        	//find cuurent attendance object
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
        			    
	        		    $mdDialog.show(confirm).then(dialogYes,aac.toast("Check out cancelled"));

        			}
				break;

        		}
        	}
        }

            // calls root-level toast function
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }
        
        //SonarQube appeasement
        var dialogYes = function() {
	    	//selected yes
	    	//checkout
	    	aac.x.checkedIn = false;
	    	userService.update(aac.user,function(){
	    		aac.toast("Checked out");
	    		aac.calcWeek( aac.curr );
	    		aac.setToolbar();
	    	});
		 };
		 
      var updateSuccess = function(){ 
			aac.toast("Successfully checked in.")
    		aac.calcWeek( aac.curr );
    		aac.setToolbar();
    		};
    }
