var sms = angular.module("sms");
sms.controller("adminAttendanceCtrl", function($scope, $state, userService, $filter) {
	var aac = this;
	
	aac.toast = function(message){
		$scope.$parent.$parent.mastCtrl.toast(message);
	};
	
	/*SET DATE HEADERS IN TABLE*/
	//set current day
	var today = new Date();
	today = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    var day = today.getDate();
    var w = today.getDay();
    /*day is the day of the month*/
    /*w is the day of the week*/
    
    
    /*set monday based on what day of the week it is*/
    var m = new Date();
    aac.activeDay = 1;
    if(w==0){
    	m.setDate(day+1);
    	aac.activeDay = 0;
    }
    if(w==2){
    	m.setDate(day-1);
    	aac.activeDay = 2;
    }
    if(w==3){
    	m.setDate(day-2);
    	aac.activeDay = 3;
    }
    if(w==4){
    	m.setDate(day-3);
    	aac.activeDay = 4;
    }
    if(w==5){
    	m.setDate(day-4);
    	aac.activeDay = 5;
    }
    if(w==6){
    	m.setDate(day-5);
    	aac.activeDay = 6;
    }
    /*set global monday for day week change functions*/
    aac.thisMonday = m;
    
    /*set all days based on monday*/
    var setMonday = new Date();
    setMonday.setDate(m.getDate());
    monday = m;
    tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
    wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
    thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
    friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
    
    /*set all scope days to print on top of table*/
    console.log(monday);
    aac.monday = (monday.getMonth()+1)+"/"+monday.getDate();
    aac.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
    aac.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
    aac.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
    aac.friday = (friday.getMonth()+1)+"/"+friday.getDate();
    
	//get all attendance for the week for all associates
    
    /*aac.allThisWeekAttendance = [];*/
    
    userService.getAll(function(response){
    	
    	aac.users = $filter("associateFilter")(response);
    	console.log(aac.users);
    	aac.users = $filter("weekFilter")(aac.users, monday);
    	console.log(aac.users);
    	/*aac.users.forEach(function(user){
    		thisWeekAttendance = $filter("weekFilter")(user, monday);
    		aac.allThisWeekAttendance.push(thisWeekAttendance);
    	});*/
    	
    }, function(error){
    	aac.toast("Error in retrieving all associates.");
    });
    
    
    
    //create a confirm function
    
    
    
    
    
    
	//change week functions
	$scope.goBackOneWeek = function() {
		/*set the new week up*/
		m = new Date();
        m.setFullYear(setMonday.getFullYear(), setMonday.getMonth(), (setMonday.getDate()-7));
        
        setMonday.setFullYear(m.getFullYear(), m.getMonth(), m.getDate());
        monday = m;
        tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
        
        /*set all scope days to print on top of table*/
        console.log(monday);
        aac.monday = (monday.getMonth()+1)+"/"+monday.getDate();
        aac.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
        aac.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
        aac.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
        aac.friday = (friday.getMonth()+1)+"/"+friday.getDate();
        
        /*make data change for new week*/
        aac.users = $filter("weekFilter")(aac.users, monday);
        
        /*setting active days*/
        /*remove active day*/
        aac.activeDay = null;
        
        /*see if this week is the active day week*/
        if(aac.thisMonday.getDate()==monday.getDate() && aac.thisMonday.getMonth()==monday.getMonth()){
        	aac.activeDay = w;
        }
        
    };
    
    $scope.goForwardOneWeek = function() {
    	/*set the new week up*/
        m = new Date();
        m.setFullYear(setMonday.getFullYear(), setMonday.getMonth(), (setMonday.getDate()+7));
        
        setMonday.setFullYear(m.getFullYear(), m.getMonth(), m.getDate());
        monday = m;
        tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1));
        wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2));
        thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3));
        friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4));
        
        /*set all scope days to print on top of table*/
        console.log(monday);
        aac.monday = (monday.getMonth()+1)+"/"+monday.getDate();
        aac.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
        aac.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
        aac.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
        aac.friday = (friday.getMonth()+1)+"/"+friday.getDate();
        
        /*make data change for new week*/
        aac.users = $filter("weekFilter")(aac.users, monday);
        
        /*setting active days*/
        /*remove active day*/
        aac.activeDay = null;
        
        /*see if this week is the active day week*/
        if(aac.thisMonday.getDate()==monday.getDate() && aac.thisMonday.getMonth()==monday.getMonth()){
        	aac.activeDay = w;
        }
        
    };
});