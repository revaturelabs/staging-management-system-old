
    angular
        .module( "sms" )
        .controller( "managerAttendanceCtrl", managerAttendanceCtrl );
     /**
      * @description AngularJs controller for Manager attendance module (both versions of Admins)
      */   

    function managerAttendanceCtrl( $scope, $state, $filter, $mdDialog, loginService, userService, skillEditFactory, taskTypeService, marketingStatusService, batchAddFactory, weekdays ) {
       /**@prop {function} Reference variable for this controller */
        var mac = this;

          // bindables
            // data
        /**@prop {object} user Currently logged in user. */
        mac.user = loginService.getUser();
        /**@prop [Array] of {object} taskTypes Types currently used in DB. */
        mac.taskTypes = [];
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
        /**@prop {boolean} panelDatePickerIsOpen Variable that tells if the panel calendar is open. */
        mac.panelDatePickerIsOpen = false;
        /**@prop {Date} panelDate The date of the panel, binded so it shows up on panel calendar */
        mac.panelDate = new Date();
        /**@prop {boolean} associateTableIsOpen Variable that tells if the associate table view is open. */
        $scope.associateTableIsOpen = false;
        /**@prop {String} tableOrder variable for sorting the associateTable. Set to sort by last name by default*/
        mac.tableOrderAttribute = "";
        mac.reverseOrder = false;
        
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
        mac.updateProjects = updateProjects;
        mac.deleteSelectedUser = deleteSelectedUser;



        /**@var {function} calcMarketingDays function reference variable. */

        mac.calcMarketingDays = calcMarketingDays;
        /**@var {function} calcMarketingDaysForAllUsers function reference variable*/
        mac.calcMarketingDaysForAllUsers = calcMarketingDaysForAllUsers;
        /**@var {function} days_between function reference variable. */
        mac.days_between = days_between;
        mac.updateCert = updateCert;
        /**@var {function} convert to date object. */
        mac.convertToDateObject = convertToDateObject;
        /**@var {function} togglePanelDatePicker function reference variable. */
        mac.togglePanelDatePicker = togglePanelDatePicker;
        /**@var {function} editPanel function reference variable. */
        mac.updatePanelDate = updatePanelDate;
        /**@var {function} togglePanelStatus function reference variable. */
        mac.togglePanelStatus = togglePanelStatus;
        /**@var {function} createPanel function reference variable. */
        mac.createPanel = createPanel;
        /**@var {function} getTaskTypes function reference variable. */
        mac.getTaskTypes = getTaskTypes;
        /**@var {function} assignProject function reference variable. */
        mac.assignProject = assignProject;
        mac.showFullJobInfo = showFullJobInfo;
        mac.editSkills = editSkills;
        mac.deleteSelectedJob = deleteSelectedJob;
        mac.makenewjob = makenewjob;
        mac.resetSelectedUsersJob = resetSelectedUsersJob;
        

          // initialization
        mac.findDevice();
        mac.getUsers();
        mac.getTaskTypes();
        mac.setToolbar("Weekly attendance");
        mac.marketingStatuses();
        
        // function
        /**
         * @description Updates user Marketing Status.
         */
        function changeStatus() {
        	mac.selectedUser.marketingStatus = mac.markBind;
        	var sentData = mac.selectedUser.toJSON();
        	userService.update(sentData,function(){
	    		mac.toast("Marketing status updated.");
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
         * @description Retrieves the information for all users from the server.
         */
        function getUsers() {
            userService.getAll( function(response) {
                mac.users = $filter( "associateFilter" )( response );
                mac.users = $filter( "taskFilter" )( mac.users, mac.today );
                mac.calcWeek( mac.curr );
            }, function() {
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
                if ( ( mac.selectedUser ) && ( user.username == mac.selectedUser.username ) ) {
                	mac.selectedUser = user;
                    if(user.panels[0] != undefined && user.panels[0].date){
                		mac.panelDate = new Date(user.panels[0].date);
                	}
                	else{
                		mac.panelDate = null;
                	}
                }
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
                    mac.panelDatePickerIsOpen = false;
                    if( (user.panels[0]) && (user.panels[0].date) ) {
	                    mac.panelDate = user.panels[0].date;
	                    mac.panelDate = new Date(user.panels[0].date);
                    }
                }
            } else {
                mac.infoOpen = true;
                mac.panelDatePickerIsOpen = false;
                mac.selectedUser = user;
                if( (user.panels[0]) && (user.panels[0].date) ) {
                    mac.panelDate = user.panels[0].date;
                    mac.panelDate = new Date(user.panels[0].date);
                }
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
                    mac.users = $filter( "taskFilter" )( mac.users, mac.today );
                }, function() {
                    mac.toast("Could not update attendance.")
                });
            }    
        }

        /**
         * @description Sets the toolbar options based on the role of the associate. 
         * At the moment, only relevant to add superAdmin options to superAdmin users when logged in,
         * as superAdmin should always have all the options that admins do.
         */
        function setToolbar(s) {
            var actions = [];
            var thisTitle = s;
            if (mac.user.userRole.name == "superAdmin") {


                actions.push( {
                    "function": mac.newAssociates,
                    "icon"   : "add",
                    "tooltip" : "Add batch of new associates"
                });

                actions.push( {
                    "function": mac.editSkills, 
                    "icon"    : "assessment", 
                    "tooltip" : "Edit available skills"
                });
                
                actions.push({
                	"function": mac.updateProjects,
                    "icon"   : "work",
                    "tooltip" : "Create or update an existing project"

                });

                // actions.push({
                    	 
                //         "function": mac.deleteAssociates,
                //         "icon"    : "transfer_within_a_station",
                //         "tooltip" : "Delete Associates" 
                // });

            }


            $scope.$emit( "setToolbar", { 
                title: thisTitle, 
                actions }
            );
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
                mac.toast( "Cannot view attendance more than 1 week in the future." );
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
		
		/**
         * @description Called when a superAdmin clicks on panel date text, opens a datePicker.
         */
		function togglePanelDatePicker(){
			mac.panelDatePickerIsOpen = !mac.panelDatePickerIsOpen;
		}
		
		/**
         * @description Called when a superAdmin selects a panel date on the datePicker.
         */
		function updatePanelDate(user, panel){
			user.tasks.forEach(function(task){
				if(task.id == panel.id){
					task.date = mac.panelDate;
					
					userService.update( user, function(){
						mac.toast("Panel date is updated");
						mac.getUsers();
						mac.panelDatePickerIsOpen = false;
					}, function(){
						mac.toast("Failed to update panel date");
					});
				}
			})
		}
		
		/**
         * @description Called when a superAdmin switches between panel statuses.
         */
		function togglePanelStatus(user, panel, status){
			if(status != panel.passed) {
				user.tasks.forEach(function(task){
					if(task.id == panel.id){
						task.passed = status;
						
						if(task.passed == true){
							task.date = new Date();
							task.date.setDate(task.date.getDate()-1);
						}
						
						userService.update( user, function(){
							mac.toast("Panel passed updated to "+status);
							mac.getUsers();
						}, function(){
							mac.toast("Failed to update panel status");
						});
					}
				})
			}
		}
		
		/**
         * @description Called when a superAdmin clicks on the create panel icon.
         */
		function createPanel(user){
			
			panel = {};
			panel.date = new Date();
			panel.note = "no note";
			panel.passed = false;
			
			panel.taskType = {};
			mac.taskTypes.forEach(function(taskType){
				if(taskType.type.toLowerCase() == "panel"){
					panel.taskType = taskType;
				}
			})
			
			user.tasks.push(panel);
			
			userService.update( user, function() {
                mac.toast("Panel created.");
                
                mac.getUsers();
            }, function() {
                mac.toast("Failed to create panel");
            });
		}
		
		/**
         * @description Retrieves the taskTypes from the DB.
         */
        function getTaskTypes() {
            taskTypeService.getAll( function(response) {
                mac.taskTypes = response;
            }, function() {
                mac.toast("Error retrieving all task types.");
            });
        }
        
    	/**
         * @description Called when a superAdmin clicks on assign project, opens a dialog.
         */
		function assignProject(user, project){
			//only superadmins can do this
			if(mac.user.userRole.name != "superAdmin"){
				return;
			}
			
			$mdDialog.show({
                templateUrl: "html/templates/assignProject.html",
                controller: "assignProjectCtrl as ap",
                locals:{
                	user,
                	project
                },
                clickOutsideToClose: true,
                escapeToClose: true
            });
		}
		

        //Function for adding skills, document properly after it's fully created.
        function editSkills(){

            $mdDialog.show({
                templateUrl: "html/templates/skillsEdit.html",
                controller: "skillEditCtrl as sECtrl",
                clickOutsideToClose: true,
                escapeToClose: true
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
         * @description calcMarketingDays function, adapted for iterating over all users. Takes in a user as a parameter
         * @returns {number} number of days between the grad date and today
         */
        function calcMarketingDaysForAllUsers(user){
	        if(user.graduationDate == null){
	        	return "N/A";
	        }
	        else{
	       		return " " + mac.days_between(mac.curr, ((new Date(user.graduationDate)))) + " days";	
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
        			//prompt
        			mac.toast("Job deleted.");
        			mac.selectedjob = null;
        		}, function() {
        			//prompt
        			mac.toast("Error deleting job.");
        		})
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
                locals: { "selectedUser": mac.selectedUser },
                bindToController: true,
                clickOutsideToClose: true,
                escapeToClose: true
            }).then( function() {
            	
            	//prompt
            	mac.toast("Job addition successful.");
            }, function() {
                mac.toast("Job addition cancelled.");
            });
        }
        //..............................
		
		function resetSelectedUsersJob(ev){
			// of we have selected a user
			if( mac.selectedUser != undefined) {
				//<<<<<<<<<<<
				 
                // Appending dialog to document.body to cover sidenav in docs app
                var confirm = $mdDialog.confirm()
                        .title('Reset selected users password?')
                        .textContent('Password will be reset to '+ mac.selectedUser.firstName +' '+ mac.selectedUser.lastName+'\'s username')
                        .ariaLabel('Lucky day')
                        .targetEvent(ev)
                        .ok('Please do it!')
                        .cancel('No, thank you.');

                $mdDialog.show(confirm).then(function() {
                    
                    //MM TODO Erase mm block use login controller to update pass, make new endpoint
                    // add a loading icon to show something is going on
                    angular.element("body").addClass("loading");
                    
                    // update the selected user
                    loginService.resetPass( mac.selectedUser, function() {
                        
                        // remove the loading icon
                        angular.element("body").removeClass("loading");
                        
                        //prompt the user
                        mac.toast("Password reset successful.");	
                    }, function() {
                        
                        // remove the loading icon
                        angular.element("body").removeClass("loading");
                        
                        //prompt the user
                        mac.toast("Error resetting Password.");
                    });
                    //MM
                }, 
                //on error
                function() {
                    
                    //prompt
                    mac.toast("Password reset cancelled.");
                });
				//<<<<<<<<<<<
			}
		}

		$scope.$on( "setView", function( events, data ) {
            mac.associateTableIsOpen = data.associateTableIsOpen;
            setToolbar(data.title);
        })

		/**
         * @description Called when a superAdmin clicks on update project icon, opens a dialog.
         */
		function updateProjects(){
			//only superadmins can do this
			if(mac.user.userRole.name != "superAdmin"){
				return;
			}
			
			$mdDialog.show({
                templateUrl: "html/templates/updateProjects.html",
                controller: "updateProjectsCtrl as up",
                clickOutsideToClose: false,
                escapeToClose: false
            }).then(function(){
                mac.toast("Projects updated");
            });
		}

		function deleteSelectedUser(ev) {
			if( mac.selectedUser != undefined) {
				var confirm = $mdDialog.confirm()
		          .title('Delete selected user?')
		          .textContent(mac.selectedUser.firstName +' '+ mac.selectedUser.lastName+ ' will be removed.' )
		          .ariaLabel('Lucky day')
		          .targetEvent(ev)
		          .ok('OKAY')
		          .cancel('CANCEL');
				
				 $mdDialog.show(confirm).then(function() {
				    	
				    	//MM TODO Erase mm block use login controller to update pass, make new endpoint
				    	// add a loading icon to show something is going on
				    	angular.element("body").addClass("loading");
				    	
				    	// update the selected user
			    		userService.remove( mac.selectedUser, function() {
			    			
			    			// remove the loading icon
			    			angular.element("body").removeClass("loading");
			    			
			    			//prompt the user
			    			mac.toast("User deleted.");	
			    			mac.getUsers();
		                    mac.users = $filter( "taskFilter" )( mac.users, mac.today );
			    		}, function(error) {
			    			// remove the loading icon
			    			angular.element("body").removeClass("loading");
			    			
			    			//prompt the user
			    			mac.toast("Error deleting user.");
			    		});
                },
                function() {
                    //prompt
                    mac.toast("User deletion cancelled.");
                });
            }
        }
		
          // watchers
        $scope.$watch( function() { return mac.selectedUser }, function( newUser, oldUser ) {
            if ( (newUser != oldUser) && (newUser) ) {
                $scope.$broadcast( "newSelectedUser", mac.selectedUser );
            }
        })

        $scope.$on( "repullUsers", function() {
            mac.getUsers();
        })
    }