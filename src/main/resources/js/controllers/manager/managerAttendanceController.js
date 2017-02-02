
    angular
        .module( "sms" )
        .controller( "managerAttendanceCtrl", managerAttendanceCtrl );
     /**
      * @description AngularJs controller for Manager attendance module (both versions of Admins)
      */   
    function managerAttendanceCtrl( $scope, $state, $filter, $mdDialog, loginService, userService, marketingStatusService,jobEventTypeService, batchAddFactory, weekdays ) {
       /**@prop {function} Reference variable for this controller */
        var mac = this;

          // bindables
            // data
        /**@prop {object} user Currently logged in user. */
        mac.user = loginService.getUser();
        /**@prop {Date} curr Date of the currently selected week. */
        mac.curr = new Date();
        /**@prop {Date} today Today's date. */
        mac.today = mac.curr;
         /**@prop {Date} minWeek The earliest week that can be looked at.. */
        mac.minWeek = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() - 28 ); 
         /**@prop {Date} maxWeek The latest week that can be looked at. */
        mac.maxWeek = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() + 7 );
         /**@prop {boolean} infoOpen Variable that tells if the info tabs are open or not. */
        mac.infoOpen = false;
        mac.markBind = "";
        

            // functions
        mac.findDevice = findDevice;
        mac.getUsers = getUsers;
        mac.calcWeek = calcWeek;
        mac.filterWeek = filterWeek;
        mac.toggleInfo = toggleInfo;
        mac.closeInfo = closeInfo;
        mac.verify = verify;
        mac.setToolbar = setToolbar;
        mac.prevWeek = prevWeek;
        mac.nextWeek = nextWeek;
        mac.toast = toast;
        mac.newAssociates = newAssociates;
        mac.marketingStatuses = marketingStatuses;
        mac.changeStatus = changeStatus;
      

        /**@var {function} calcMarketingDays function reference variable. */
        mac.calcMarketingDays = calcMarketingDays;
        /**@var {function} days_between function reference variable. */
        mac.days_between = days_between;
        /**@var {function} editCert function reference variable. */
        mac.updateCert = updateCert;
        
        mac.convertToDateObject = convertToDateObject;


        mac.showFullJobInfo = showFullJobInfo;

        mac.deleteSelectedJob = deleteSelectedJob;
        
        mac.makenewjob = makenewjob;
        
          // initialization
        mac.findDevice();
        mac.getUsers();
        mac.setToolbar();
        mac.marketingStatuses();
  
        
        // function
        /**
         * @description Updates user Marketing Status.
         */
        function changeStatus() {
        	     	
        	
        	mac.selectedUser.marketingStatus
        	= mac.markBind;
        
        	var sentData = mac.selectedUser.toJSON();
                	
        	userService.update(sentData,function(){
	    		mac.toast("Marketing Status Updated");
	    	    	});
        	
        
        }
        
        
          // functions
            /**
             * @description Sets the browser size based on the device being used.
             */
        function findDevice() {
            if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
                mac.smallDevice = true;
            } else {
                mac.smallDevice = false;
            }
        }
            // gets all users' information
        /**
         * @description Retrieves the information for all users from teh server.
         */
        function getUsers( success ) {
            userService.getAll( function(response) {
                mac.users = $filter( "associateFilter" )( response );
                mac.users = $filter( "taskFilter" )( mac.users, mac.today );
                mac.calcWeek( mac.curr );
            }, function(error) {
                mac.toast("Error retrieving all users.");
            });
        }

            /**
             * @description Adds the information for this weeks attendance to each of the users.
             */
        function calcWeek( date ) {
            
            var monday = new Date( date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 1 );
            mac.weekLabel = "Week of " + (monday.getMonth() + 1) + "/" + (monday.getDate());
            mac.week = [ { name: "Monday", date: monday } ];

            for (var i = 1; i < 5; i++) {
                var newDate = new Date( monday.getFullYear(), monday.getMonth(), monday.getDate() + i )
                mac.week.push( { name: weekdays[newDate.getDay()], date: newDate } );
            }

            mac.users.forEach( function(user) {
                mac.filterWeek( monday, user );
            });
        }

            /**
             * @description Filters the current weeks attendance from the users.
             */
        function filterWeek( monday, user ) {
            var weekAttendance = $filter( "weekFilter" )( [user], monday )[0].thisWeek;
            for (var i = 0; i < mac.week.length; i++) {
                if ( mac.week[i].date.getTime() < mac.today.getTime() ) {
                    if ( weekAttendance[i] == undefined ) {
                        weekAttendance[i] = {
                            verified: false,
                            checkedIn: false
                        }
                        weekAttendance[i] = $filter( "iconFilter" )( weekAttendance[i], "week" );
                    }
                }
            }
            while ( weekAttendance.length != 5 ) {
                weekAttendance.push({});
            }
            user.weekAttendance = weekAttendance;
        }

            // sets selected user and opens/closes info panel
            /**
             * @description Opens/closes the info panel and sets the selected user to display the relevant info.
             */
        function toggleInfo( user ) {
            if (mac.infoOpen) {
                if (mac.selectedUser == user) {
                    mac.infoOpen = false;
                    mac.selectedUser = null;
                } else {
                    mac.selectedUser = user;
                
                }
            } else {
                mac.infoOpen = true;
                mac.selectedUser = user;
               
            }
        }

        /**
         * @description Closes the info panel.
         */
        function closeInfo() {
            mac.infoOpen = false;
            mac.selectedUser = null;
        }

        /**
         * @description Verifies and unverifies the clicked users attendance. Opens a confirmation dialog
         * if unverifing an already verified associate. 
         */
        function verify( user, index ) {
            var selectedDay = mac.week[index].date;
            if (user.attendance) {
                var found = false;
                user.attendance.forEach( function(attendance) {
                    var attDate = new Date(attendance.date);
                    if ( (attDate.getFullYear() == selectedDay.getFullYear() && attDate.getMonth() == selectedDay.getMonth() && attDate.getDate() == selectedDay.getDate() ) ) {
                        if (attendance.verified) {
                  
                            attendance.verified = false;
                        } else {
                            attendance.verified = true;
                            attendance.note = "Verified";
                        }
                        found = true;
                        attendance.note +=  " by " + mac.user.firstName + " " + mac.user.lastName + 
                            " at " + mac.today.getHours() + ":" + padZero(mac.today.getMinutes()) + 
                            " on " + mac.today.getMonth() + 1 + "/" + mac.today.getDate() + "/" + mac.today.getFullYear();
                    }
                })

                if (!found) {
                    var attendance = {
                        date: selectedDay,
                        checkedIn: false,
                        verified: true,
                        note: "Verified by " + mac.user.firstName + " " + mac.user.lastName + 
                            " at " + mac.today.getHours() + ":" + padZero(mac.today.getMinutes) + 
                            " on " + mac.today.getMonth() + 1 + "/" + mac.today.getDate() + "/" + mac.today.getFullYear()
                    }
                    user.attendance.push( attendance );
                }
                userService.update( user, function() {
                    mac.toast("Attendance updated.");
                   
                    mac.getUsers();
                }, function() {
                    mac.toast("Could not udpdate attendance.")
                });
            }    
        }

        /**
         * @description Sets the toolbar options based on the role of the associate. 
         * At the moment, only relevant to add superAdmin options to superAdmin users when logged in,
         * as superAdmin should always have all the options that admins do.
         */
        function setToolbar() {
            if (mac.user.userRole.name == "superAdmin") {
                $scope.$emit( "setToolbar", { 
                    title: "Weekly attendance", 
                    actions: [{ 
                        "function": mac.newAssociates, 
                        "icon"    : "add", 
                        "tooltip" : "Add batch of new associates"}] } );
            }
        }

            
            /**
             * @description Changes display to the previous week, unless the current week displayed
             * is the minimum week. In that case, an error notification is displayed. 
             */
        function prevWeek() {
            var newDate = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() - 7 );
            if ( newDate.getTime() < mac.minWeek.getTime() ) {
                mac.toast( "Cannot view attendance older than four weeks." );
            } else {
                mac.curr = newDate;
                mac.calcWeek( mac.curr );
            }
        }

            /**
             * @description Changes display to the next week, unless the current week displayed
             * is the maximum week. In that case, an error notification is displayed. 
             */
        function nextWeek() {
            var newDate = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() + 7 );
            if ( newDate.getTime() > mac.maxWeek.getTime() ) {
                mac.toast( "Cannot view attendance in the future." );
            } else {
                mac.curr = newDate;
                mac.calcWeek( mac.curr );
            }
        }

            /**
             * @description Displays a toast notification.
             * @param {string} message The value of the message to be shown.
             */
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }
        
        // adds associates by batch
		function newAssociates() {
            
              // opens a dialog to allows addition of a new batch of associates
                // opens another dialog upon success to show added associates' info
            $mdDialog.show({
                templateUrl: "html/templates/batchAdd.html",
                controller: "batchAddCtrl as bACtrl",
                clickOutsideToClose: true,
                escapeToClose: true
            }).then( function() {
                $mdDialog.show({
                    templateUrl: "html/templates/batchAddSuccess.html",
                    controller: "batchAddSuccessCtrl as bASCtrl",
                    locals: { "newAssociates": batchAddFactory.getNewAssociates() },
                    bindToController: true
                }).then( function(){
                    batchAddFactory.resetAssociates();
                });
            }, function() {
                mac.toast("Batch addition cancelled.");
            });
        }
		/**
         * @description Called when a superAdmin clicks on update certification button, opens a dialog.
         */
		function updateCert(cert, user){
			if(mac.user.userRole.name != "superAdmin"){
				return;
			}
			$mdDialog.show({
                templateUrl: "html/templates/updateCert.html",
                controller: "updateCertification as uc",
                locals:{
                	cert,
                	user
                },
                clickOutsideToClose: false,
                escapeToClose: false
            });
		}

            // adds a leading zero to input if necessary
        function padZero( input ) {
            if (input < 10) {
                return "0" + input;
            } else {
                return "" + input;
            }
        }
        

        function marketingStatuses() {
	        marketingStatusService.getAll(function(response) {
	        	mac.mStatuses = response;
	        	
	        }, function() {
	            
	        });}

        /**
         * @description calls a function that Determines the difference between the two supplied dates.
         * @returns {number} Number of days between the graduation date and today
         */
        function calcMarketingDays(){
        	if (mac.selectedUser) {
	        	if(mac.selectedUser.graduationDate == null){
	        		return "N/A";
	        	}
	        	else{
	
	        		return " " + mac.days_between(mac.curr, ((new Date(mac.selectedUser.graduationDate)))) + " days";	
	        	}
        	}
        }
        
        /**
         * @description Determines the difference betwen the two supplied dates.
         * @param {date} date1 First supplied date.
         * @param {date} date2 Second supplied date.
         * @returns {number} Number of days between the two dates
         */
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
        
        function convertToDateObject(adate){
        	 var condate =  new Date(adate);
        	return (condate.getMonth()+1)+ "/" + condate.getDate() + "/"+ condate.getFullYear();
        }
        
        function showFullJobInfo(event){
            
            // if the info boxes are open for a particular user
            if (mac.infoOpen) {
            	// if the selected job is already the open job of information 
                if (mac.selectedjob == event) {
                    
                	//set the selected to null
                	mac.selectedjob = null;
                }
                // if the selected is not == to the new job
                else {
                	
                	// set a new selected
                	mac.selectedjob = event;
                }
            }
        }
        
        /*mac.closeJobInfo = function(){
            mac.selectedjob =null;
        }*/
        function deleteSelectedJob(){
        	
        	// if we have a selected job and user
        	if( ( mac.selectedjob != undefined ) && ( mac.selectedUser != undefined ) ) {
        		
        		mac.selectedUser.events.splice( mac.selectedUser.events.indexOf(mac.selectedjob), 1 );
        		userService.update( mac.selectedUser, function() {
        			console.log("Good things.");
        		}, function(error) {
        			console.log(error);
        			console.log("Bad things.");
        		})
        		
//        		// loop through the selected users object length
//        		for(var i = 0; i < mac.selectedUser.events.length;i++){
//        			
//        			// if we found the selected object
//        			if(mac.selectedjob == mac.selectedUser.events[i]){
//        				
//        				// set the last element to  the current position
//        				mac.selectedUser.events[i] = mac.selectedUser.events[mac.selectedUser.events.length-1]; 
//        				
//        				//pop the last element as to delete the job
//        				mac.selectedUser.events.pop();
//        				
//        				//create a json object of the new user object
//        				var sentData = mac.selectedUser.toJSON();
//                    	
//        				//update user object //TODO get to work
//        	        	userService.update(sentData,function(){
//        	        		
//        	        		// set the selected job to null
//            				mac.selectedjob = null;
//        	        		
//        	        		// prompt user
//            				mac.toast("Job deleted");
//        		    	});
//        	        	
//        				// we can end the loop if we finished early
//        				break;
//        			}
//        		}
        	}
        }
        
        //................................
     // adds associates by batch
		function makenewjob() {
            
              // opens a dialog to allows addition of a new batch of associates
                // opens another dialog upon success to show added associates' info
            $mdDialog.show({
                templateUrl: "html/templates/jobAdd.html",
                controller: "jobAddCtrl as jACtrl",
                clickOutsideToClose: true,
                escapeToClose: true
            }).then( function() {
                $mdDialog.show({
                    templateUrl: "html/templates/jobAddSuccess.html",
                    controller: "jobAddSuccessCtrl as jASCtrl",
                    locals: { "newAssociates": batchAddFactory.getNewAssociates() },
                    bindToController: true
                }).then( function(){
                    batchAddFactory.resetAssociates();
                });
            }, function() {
                mac.toast("Job addition cancelled.");
            });
        }
        //..............................
        
    }