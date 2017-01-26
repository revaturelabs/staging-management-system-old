
    angular
        .module( "sms" )
        .controller( "managerAttendanceCtrl", managerAttendanceCtrl );
        
    function managerAttendanceCtrl( $scope, $state, $filter, $mdDialog, loginService, userService, batchAddFactory, weekdays ) {
        var mac = this;

          // bindables
            // data
        mac.user = loginService.getUser();
        mac.curr = new Date();
        mac.today = mac.curr;
        mac.minWeek = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() - 28 ); 
        mac.maxWeek = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() + 7 );
        mac.infoOpen = false;

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

          // initialization
        mac.findDevice();
        mac.getUsers();
        mac.setToolbar();
        
          // functions
            // set browser size based on device
        function findDevice() {
            if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
                mac.smallDevice = true;
            } else {
                mac.smallDevice = false;
            }
        }
            // gets all users' information
        function getUsers( success ) {
            userService.getAll( function(response) {
                mac.users = $filter( "associateFilter" )( response );
                mac.users = $filter( "taskFilter" )( mac.users, mac.today );
                mac.calcWeek( mac.curr );
            }, function(error) {
                mac.toast("Error retrieving all users.");
            });
        }

            // sends options and actions to toolbar
		function addOptions() {
			var actions = [];
			
			if (mac.user.userRole.name == "superAdmin") {
				actions.push({
					"function": mac.newAssociates,
					"icon": "add",
					"tooltip": "Add Batch"
				});
			}

			$scope.$emit("setToolbar", {title: "Weekly Attendance", actions});
		}

            // adds object representing this week's attendance to each user
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

            // filters this week's attendance from the give user
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

            // closes associate info panel
        function closeInfo() {
            mac.infoOpen = false;
            mac.selectedUser = null;
        }

            // verifies/unverifies user's attendance
              // confirmation dialog pops up if unverifying attendance
        function verify( user, index ) {
            var selectedDay = mac.week[index].date;
            if (user.attendance) {
                var found = false;
                user.attendance.forEach( function(attendance) {
                    var attDate = new Date(attendance.date);
                    if ( (attDate.getFullYear() == selectedDay.getFullYear() && attDate.getMonth() == selectedDay.getMonth() && attDate.getDate() == selectedDay.getDate() ) ) {
                        if (attendance.verified) {
                                // issue with this not showing the updated attendance until another update is made
                                  // will work out later
                            // var confirm = $mdDialog.confirm()
                            //     .title("Are you sure you want to retract attendance verification?")
                            //     .ok("YES")
                            //     .cancel("CANCEL");
                            // $mdDialog.show(confirm).then(function() {
                            //     attendance.verified = false;
                            //     attendance.note = "Unverified";
                            // });
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
                    // mac.calcWeek( mac.curr );
                    mac.getUsers();
                }, function() {
                    mac.toast("Could not udpdate attendance.")
                });
            }    
        }

            // sets toobar icons and functions
        function setToolbar() {
            $scope.$emit( "setToolbar", { 
                title: "Weekly attendance", 
                actions: [{ 
                    "function": mac.newAssociates, 
                    "icon"    : "add", 
                    "tooltip" : "Add batch of new associates"}] } );
        }

            // checks if previous week is before minimum date and resets week dates if not
        function prevWeek() {
            var newDate = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() - 7 );
            if ( newDate.getTime() < mac.minWeek.getTime() ) {
                mac.toast( "Cannot view attendance older than four weeks." );
            } else {
                mac.curr = newDate;
                mac.calcWeek( mac.curr );
            }
        }

            // checks if next week is after maximum date and resets week dates if not
        function nextWeek() {
            var newDate = new Date( mac.curr.getFullYear(), mac.curr.getMonth(), mac.curr.getDate() + 7 );
            if ( newDate.getTime() > mac.maxWeek.getTime() ) {
                mac.toast( "Cannot view attendance in the future." );
            } else {
                mac.curr = newDate;
                mac.calcWeek( mac.curr );
            }
        }

            // calls root-level toast function
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

            // adds a leading zero to input if necessary
        function padZero( input ) {
            if (input < 10) {
                return "0" + input;
            } else {
                return "" + input;
            }
        }
    }