
    angular
        .module( "sms" )
        .controller( "managerAttendanceCtrl", managerAttendanceCtrl );
     /**
      * @description AngularJs controller for Manager attendance module (both versions of Admins)
      */   
    function managerAttendanceCtrl( $scope, $state, $filter, $mdDialog, loginService, userService, batchAddFactory, weekdays ) {
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

            // functions
        /**@var {function} findDevice function reference variable. */
        mac.findDevice = findDevice;
        /**@var {function} getUsers function reference variable. */
        mac.getUsers = getUsers;
        /**@var {function} calcWeek function reference variable. */
        mac.calcWeek = calcWeek;
        /**@var {function} filterWeek function reference variable. */
        mac.filterWeek = filterWeek;
        /**@var {function} toggleInfo function reference variable. */
        mac.toggleInfo = toggleInfo;
        /**@var {function} closeInfo function reference variable. */
        mac.closeInfo = closeInfo;
        /**@var {function} verify function reference variable. */
        mac.verify = verify;
        /**@var {function} setToolbar function reference variable. */
        mac.setToolbar = setToolbar;
        /**@var {function} prevWeek function reference variable. */
        mac.prevWeek = prevWeek;
        /**@var {function} nextWeek function reference variable. */
        mac.nextWeek = nextWeek;
        /**@var {function} toast function reference variable. */
        mac.toast = toast;
        /**@var {function} newAssociates function reference variable. */
        mac.newAssociates = newAssociates;

          // initialization
        mac.findDevice();
        mac.getUsers();
        mac.setToolbar();
        
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

        /**
         * @description Sets the toolbar options based on the role of the associate. 
         * At the moment, only relevant to add superAdmin options to superAdmin users when logged in,
         * as superAdmin should always have all the options that admins do.
         */
        function setToolbar() {
            var actions = [];
            if (mac.user.userRole.name == "superAdmin") {
                actions.push( {
                    "function": mac.newAssociates,
                    "icons"   : "add",
                    "tooltip" : "Add batch of new associates."
                })
            }

            $scope.$emit( "setToolbar", { 
                title: "Weekly attendance", 
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

            // adds a leading zero to input if necessary
        function padZero( input ) {
            if (input < 10) {
                return "0" + input;
            } else {
                return "" + input;
            }
        }
    }