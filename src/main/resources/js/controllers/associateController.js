
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
        
        asc.checkCertDates = function() {
        	for(var i = 0; i < asc.user.tasks.length; i++) {
        		var today = new Date();
        		var certDate = new Date(asc.user.tasks[i].date);
        		var cert = "Certification";
        		if ( certDate.getTime() >= today.getTime() && (asc.user.tasks[i].taskType.type == cert) )
        			return false;
        	}
        	return true;
        }
        
        asc.assocCertifications = function(){
        	if (asc.checkCertDates()) {
        		$mdDialog.show({
            		templateUrl: "html/templates/scheduleCertification.html",
            		controller: "associateCertificationsCtrl as assCertCtrl"
            	}).then( function() {
            		asc.toast("Certification Scheduled");
                }, function() {
                	asc.toast("Certification Schedule Cancelled");
                });
        	}
        	else {
        		asc.toast("You can only schedule one certification at a time.");
        	}
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

    });