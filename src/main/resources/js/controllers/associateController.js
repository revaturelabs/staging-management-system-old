
    var sms = angular.module( "sms" );

    sms.controller( "associateCtrl", function($http, $scope, $state, $mdSidenav, loginService, $mdDialog ){
        var asc = this;
        asc.title = " ";
          // functions
        asc.openMenu = function() {
            $mdSidenav("left").open();
        };

        asc.toast = function(message){
            $scope.$parent.mastCtrl.toast(message);
        };
        
        asc.logout = function() {
            asc.user = {};
            asc.token = "";
            loginService.logout();
            asc.toast("Logged out.");
            $state.go("login");
        };
        
        asc.updateInformation = function(){
        	$mdSidenav("left").close();
        	
        	$mdDialog.show({
					templateUrl: "html/templates/updateInformation.html",
	                controller: "updateInfoCrtl as uInfoctrl",
	                locals: {needChangePass:false}
				}).then( function(){
					asc.toast("Password changed successfully.");
				},function(){
					asc.toast("Password change cancelled.");
				})
        };

        asc.associateAttendance= function(){
        	$mdSidenav("left").close();
        	$state.go("assocAttendance");
        	
        };
        
        asc.assocCertifications = function(){
        	$mdDialog.show({
        		templateUrl: "html/templates/scheduleCertification.html",
        		controller: "associateCertificationsCtrl as assCertCtrl"
        	}).then( function() {
        		asc.toast("Certification Scheduled");
            }, function() {
            	asc.toast("Certification Schedule Cancelled");
            });
        };
        
          // data
        asc.user = loginService.getUser();
        asc.token = loginService.getToken();

        loginService.checkPass(asc.user.username,
        		function(response){
    	   			if(response.update == true){
    	   				$mdDialog.show({
    	   					templateUrl: "html/templates/updateInformation.html",
    	   	                controller: "updateInfoCrtl as uInfoctrl",
    	   	                escapeToClose:false,
    	   	                locals: {needChangePass:true}
    	   				}).then( function(){
    	   					asc.toast("Password changed successfully.");
    	   				});
    	   			}
    	   			
    			},function(error){
    				asc.toast(error.data.errorMessage);
    			});
        

        // Associate checking in
        loginService.getStatus(function(success){
        	console.log(asc.user);
        	console.log(success.status);
        	
        	$scope.status = success.status;
        	
        },function(error){
        	console.log(error);
        });
        //id ="checkInMenu"
            asc.checkIn = function(status){
        	var d = new Date();
        	for(var i=0; i< asc.user.attendance.length; i++){
        		var d2 = new Date(asc.user.attendance[i].date);
        		if(d.getDate() === d2.getDate() & d.getMonth() === d2.getMonth()){
        			console.log(status);
        			console.log(asc.user.attendance[i]);
        			
        		}
        	}
        }
    });
