var sms = angular.module("sms");
sms.controller("adminAttendanceCtrl", function($scope, $state) {
	
	//set current day
	var d = new Date();
    var n = d.getDay();
    /*n is the day of the week*/
    
    var weekday = new Array(7);
    weekday[0] = "Sunday";
    weekday[1] = "Monday";
    weekday[2] = "Tuesday";
    weekday[3] = "Wednesday";
    weekday[4] = "Thursday";
    weekday[5] = "Friday";
    weekday[6] = "Saturday";

    var wd = weekday[n];
    /*wd will print the current day*/
    
    /*The 7 numbers specify the year, month, day, hour, minute, second, and millisecond, in that order:*/
    /*set the days of the week based on where monday is*/
    var m;
    if(n==0){
    	m = new Date(d.getYear(), d.getMonth(), (n+1),9,0,0,0);
    }
    if(n==1){
    	m = new Date(d.getYear(), d.getMonth(), n9,0,0,0);
    }
    if(n==2){
    	m = new Date(d.getYear(), d.getMonth(), (n-1),9,0,0,0);
    }
    if(n==3){
    	m = new Date(d.getYear(), d.getMonth(), (n-2),9,0,0,0);
    }
    if(n==4){
    	m = new Date(d.getYear(), d.getMonth(), (n-3),9,0,0,0);
    }
    if(n==5){
    	m = new Date(d.getYear(), d.getMonth(), (n-4),9,0,0,0);
    }
    if(n==6){
    	m = new Date(d.getYear(), d.getMonth(), (n-5),9,0,0,0);
    }
    
    
    monday = m;
    tuesday = new Date(m.getYear(), m.getMonth(), (m.getDate()+1),9,0,0,0);
    wednesday = new Date(m.getYear(), m.getMonth(), (m.getDate()+2),9,0,0,0);
    thursday = new Date(m.getYear(), m.getMonth(), (m.getDate()+3),9,0,0,0);
    friday = new Date(m.getYear(), m.getMonth(), (m.getDate()+4),9,0,0,0);
    
    $scope.monday = monday.getDate();
    
	//get all attendance for the week for all associates
    
    
    //create a confirm function
    
    
	
	
});