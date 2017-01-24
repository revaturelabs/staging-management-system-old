
    angular
        .module( "sms" )
        .controller( "associateAttendenceCtrl", associateAttendanceCtrl );
        
    function associateAttendanceCtrl( $scope, $state, $filter, loginService, userService, weekdays ) {
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
        aac.prevWeek = prevWeek;
        aac.nextWeek = nextWeek;
        aac.toast = toast;
        aac.checkIn = checkIn;

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
            for (var j = 0; j < aac.week.length; j++) {
                if ( aac.week[j].date.getTime() < aac.today.getTime() ) {
                    if ( aac.weekAttendance[j] == undefined ) {
                        aac.weekAttendance[j] = {
                            verified: false,
                            checkedIn: false
                        }
                        aac.weekAttendance[j] = $filter( "iconFilter" )( aac.weekAttendance[j] );
                    }
                }
            }
        }

            // sets toobar icons and functions
        function setToolbar() {
        	var cin = {"function": aac.checkIn,
        					"icon": "check",
        					"tooltip": "Check in"};
            $scope.$emit( "setToolbar", { title: "Weekly attendance", actions: {cin} } );
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
        	//console.log(aac.user);
        	
        	var d = new Date();
        	for(var i=0; i< aac.user.attendance.length; i++){
        		var d2 = new Date(aac.user.attendance[i].date);
        		if(d.getDate() === d2.getDate() & d.getMonth() === d2.getMonth()){
        			aac.user.attendance[i].checkedIn = true;
        			console.log(aac.user.attendance[i].checkedIn);
        			//TODO: ********************************************************************************** ******************************* update user status and save to db
        			userService.update(aac.user,function(){},function(error){aac.toast(error)});
        			aac.calcWeek( aac.curr );
        		}
        	}
        	
        	
        	
        	
        }

            // calls root-level toast function
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }
    }