
    angular
        .module( "sms" )
        .controller( "managerViewUserInfoCtrl", managerViewUserInfoCtrl );

    function managerViewUserInfoCtrl( $scope, $filter, $mdDialog, loginService, skillService, userService, taskTypeService ) {
        var mic = this;

          // bindables
            // data
        mic.user = $scope.$parent.manAttCtrl.user;
        mic.showChips = false;
        mic.selectedSkills = mic.user.skill;
        mic.curr = new Date();

            //functions
        mic.toast = toast;
        mic.getTaskTypes = getTaskTypes;
        mic.getSkills = getSkills;
        mic.divideTasks = divideTasks;
        mic.formatTaskListItemTitle = formatTaskListItemTitle;
        mic.formatTaskListItemContent = formatTaskListItemContent;
        mic.divideEvents = divideEvents;
        mic.addCert = addCert;
        mic.editSkills = editSkills;
        mic.updateSkills = updateSkills;
        mic.joinArrayBySkill = joinArrayBySkill;
        mic.transformChip = transformChip;
        mic.skillSearch = skillSearch;
        mic.createFilterFor = createFilterFor;
        mic.emitRepull = emitRepull;
        mic.deleteSelectedUser = deleteSelectedUser;
        mic.resetSelectedUsersPassword = resetSelectedUsersPassword;
        mic.calcMarketingDays = calcMarketingDays;
        mic.calcMarketingDaysForAllUsers = calcMarketingDaysForAllUsers;
        mic.days_between = days_between;

          // initialization
        mic.getTaskTypes();
        mic.getSkills();

          // functions
            // calls global toast function
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }   
        
            // retrieves list of all task types
        function getTaskTypes() {
            taskTypeService.getAll( function(response) {
                mic.taskTypes = response;
            }, function(error){
            });
        }

            // retrieves list of all skills
        function getSkills() {
            skillService.getAll( function(response) {
                mic.skills = response;
            }, function(){});
        }

            // divides user tasks into categories
              // sets flag if certification is scheduled for the future
        function divideTasks() {
            var allTasks = {};
            var newCert = true;
            var today = new Date();
            mic.user.tasks.forEach( function(task) {
                if ( Object.keys(allTasks).indexOf(task.taskType.type) == -1 ) {
                    allTasks[task.taskType.type] = [task];
                } else {
                    allTasks[task.taskType.type].push(task);
                }
                if (task.taskType.type.toLowerCase() == "certification") {
                    if (task.date > today.getTime()) {
                        newCert = false;
                    }
                }
            });
            mic.allTasks = allTasks;
            mic.newCert = newCert;
        }

            // formats task list title by type
        function formatTaskListItemTitle(task) {
            if ( task.taskType.type.toLowerCase() == "panel" ) {
                return (task.passed) ? "Passed" : "Failed";
            } else {
                return task.note;
            }
        }

            // formats task list content by type
        function formatTaskListItemContent(task) {
            /*var today = new Date();
            if ( task.taskType.type.toLowerCase() == "project" ) {*/
                return $filter( "date" )( task.date, "MMMM dd, yyyy" );
            /*} else {
                return $filter( "date" )( task.date, "MMMM dd, yyyy" ); 
            }*/
        }

            // divides user events into categories
        function divideEvents() {
            var allEvents = {};
            mic.user.events.forEach( function(event) {
                if ( Object.keys(allEvents).indexOf(event.type.type) == -1 ) {
                    allEvents[event.type.type] = [event];
                } else {
                    allEvents[event.type.type].push(event);
                }
            });
            mic.allEvents = allEvents;
        }

        function addCert() {
            // console.log( "Add cert here." );
        }

            // swaps showing skill list and skill chips
        function editSkills() {
            mic.showChips = !mic.showChips;
        }

            // update user object with new skillset
        function updateSkills() {
            userService.update( mic.user, function() {
                mic.toast( "Skills updated." );
                mic.showChips = false;
            }, function() {
                mic.toast( "Skills failed to update." );
            });
        }

            // reformats how an array of objects is joined
        function joinArrayBySkill(elem) {
            return elem.skill;
        }

            // for use by chips
        function transformChip(chip) {
            return chip;
        }

            // for use by skill search autocomplete
        function skillSearch(text) {
            var results = text ? mic.skills.filter( mic.createFilterFor( text ) ) : [];
            return results;
        }

            // filter for use by skill serach autocomplete
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(skill) {
                return (skill.skill.toLowerCase().indexOf(lowercaseQuery) === 0);
            }
        }

        function deleteSelectedUser(ev) {
			if( mic.user != undefined) {
				var confirm = $mdDialog.confirm()
		          .title('Delete selected user?')
		          .textContent(mic.user.firstName + ' ' + mic.user.lastName + ' will be removed.' )
		          .ariaLabel('Lucky day')
		          .targetEvent(ev)
		          .ok('OKAY')
		          .cancel('CANCEL');
				
				 $mdDialog.show(confirm).then(function() {
				    	
				    	//MM TODO Erase mm block use login controller to update pass, make new endpoint
				    	// add a loading icon to show something is going on
				    	angular.element("body").addClass("loading");
				    	
				    	// update the selected user
			    		userService.remove( mic.user, function() {
			    			
			    			// remove the loading icon
			    			angular.element("body").removeClass("loading");
			    			
			    			//prompt the user
			    			mic.toast("User deleted.");	
                            mic.emitRepull();
			    		}, function(error) {
			    			// remove the loading icon
			    			angular.element("body").removeClass("loading");
			    			
			    			//prompt the user
			    			mic.toast("Error deleting user.");
			    		});
                },
                function() {
                    //prompt
                    mic.toast("User deletion cancelled.");
                });
            }
        }

        function resetSelectedUsersPassword(ev){
			// of we have selected a user
			if( mic.user != undefined) {
				//<<<<<<<<<<<
				 
					    // Appending dialog to document.body to cover sidenav in docs app
					    var confirm = $mdDialog.confirm()
					          .title('Reset selected user\'s password?')
					          .textContent('Password will be reset to '+ mic.user.firstName +' '+ mic.user.lastName+'\'s username')
					          .ariaLabel('Lucky day')
					          .targetEvent(ev)
					          .ok('OKAY')
					          .cancel('CANCEL');

					    $mdDialog.show(confirm).then(function() {
					    	
					    	//MM TODO Erase mm block use login controller to update pass, make new endpoint
					    	// add a loading icon to show something is going on
					    	angular.element("body").addClass("loading");
					    	
					    	// update the selected user
				    		loginService.resetPass( mic.user, function() {
				    			
				    			// remove the loading icon
				    			angular.element("body").removeClass("loading");
				    			
				    			//prompt the user
				    			mic.toast("Password reset successful.");	
				    		}, function() {
				    			
				    			// remove the loading icon
				    			angular.element("body").removeClass("loading");
				    			
				    			//prompt the user
				    			mic.toast("Error resetting Password.");
				    		});
					    	//MM
					    }, 
					    //on error
					    function() {
					    	
					    	//prompt
					    	mic.toast("Password reset cancelled.");
					    });
				//<<<<<<<<<<<
			}
		}

        function emitRepull() {
            $scope.$emit( "repullUsers" );
        }

        /**
         * @description calls a function that Determines the difference between the two supplied dates.
         * @returns {number} Number of days between the graduation date and today
         */
        function calcMarketingDays(){

        	if (mic.user) {
	        	if(mic.user.graduationDate == null){
	        		return "N/A";
	        	}
	        	else{
	
	        		return " " + mic.days_between(mic.curr, ((new Date(mic.user.graduationDate)))) + " days";	
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
	       		return " " + mic.days_between(mic.curr, ((new Date(user.graduationDate)))) + " days";	
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

          // watcher
        $scope.$on( "newSelectedUser", function( event, data ) {
            mic.user = data;
            mic.divideTasks();
            mic.divideEvents();
        })

          // changes the functionality of the user info panel based on its position
            // allows user info panels to lock at upper corner upon scroll
        $(window).scroll(function () {
            var headerTop = $(".templateCard").offset().top + $(".templateCard").outerHeight();

            if ($(window).scrollTop() > headerTop) {
                //when the header reaches the top of the window change position to fixed
                $(".userInfoPanels").css( "position", "fixed" );
                $(".userInfoPanels").css( "top", "10px" );
                $(".userInfoPanels").css( "width", $(".userInfoPanels").css("width") );
            } else {
                //put position back to relative
                $(".userInfoPanels").css( "position", "relative" );
                $(".userInfoPanels").css( "top", "0px" );
                $(".userInfoPanels").css( "width", $(".userInfoPanels").css("width") );
            }
        })
    }