var sms = angular.module("sms");
sms.controller("adminAttendanceCtrl", function($scope, $state) {
	
	//set current day
	var today = new Date();
    var day = today.getDate();
    var w = today.getDay();
    /*day is the day of the week*/
    /*w is the day of the month*/
    
    
    /*set monday based on what day of the week it is*/
    var m = new Date();
    if(w==0){
    	m.setDate(day+1);
    }
    if(w==2){
    	m.setDate(day-1);
    }
    if(w==3){
    	m.setDate(day-2);
    }
    if(w==4){
    	m.setDate(day-3);
    }
    if(w==5){
    	m.setDate(day-4);
    }
    if(w==6){
    	m.setDate(day-5);
    }
    
    /*set all days based on monday*/
    monday = m;
    tuesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+1),9,0,0,0);
    wednesday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+2),9,0,0,0);
    thursday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+3),9,0,0,0);
    friday = new Date(m.getFullYear(), m.getMonth(), (m.getDate()+4),9,0,0,0);
    
    /*set all scope days to print on top of table*/
    console.log(monday);
    $scope.monday = (monday.getMonth()+1)+"/"+monday.getDate();
    $scope.tuesday = (tuesday.getMonth()+1)+"/"+tuesday.getDate();
    $scope.wednesday = (wednesday.getMonth()+1)+"/"+wednesday.getDate();
    $scope.thursday = (thursday.getMonth()+1)+"/"+thursday.getDate();
    $scope.friday = (friday.getMonth()+1)+"/"+friday.getDate();
    
	//get all attendance for the week for all associates
    
    
    //create a confirm function
    
    
	
	
});