 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $scope, $state, $mdSidenav, loginService, $http,$mdDialog,needChangePass){
	var uic = this;
	$scope.needChangePass = needChangePass;
	//for notifications
    uic.toast = function(message){
        $scope.$parent.mastCtrl.toast(message);
    };
    
    //When user decides to cancel password update
    uic.cancel = function(){
    	console.log(needChangePass);
    	$mdDialog.cancel();
    	
    	//route to the appropriate homepage
/*		switch(loginService.getUser().userRole.name){
		case "associate":$state.go("assocAttendance"); break;
		case "admin" : $state.go("admin"); break;
		case "superAdmin" : $state.go("attendance"); break;
		}*/
    }
    
    //when user submits updated password
    uic.submit = function(){
    	//check for empty passwords
    	if(oldPass.value === ""){
    		uic.toast("Enter your password.");
    		return;
    	}
    	
    	if(newPass.value === ""){
    		uic.toast("Enter a new password.");
    		return;
    	}
    	
    	if(confirmPass.value === ""){
    		uic.toast("Confirm your new password.");
    		return;
    	}
    	//TODO: user change their password to their username
    	
    	//hash passwords
    	var oldPassH = CryptoJS.SHA1(oldPass.value).toString();
    	var newPassH = CryptoJS.SHA1(newPass.value).toString();
    	var confirmPassH = CryptoJS.SHA1(confirmPass.value).toString();
    	
    	if(newPassH == confirmPassH){
    		//new passwords match
    		if(oldPassH != newPassH){
	    		//old and new passwords are different
    			
	            uic.user = loginService.getUser();
	            uic.token = loginService.getToken();
	            $http({
	            	  method: 'PUT',
	            	  url: '/api/v1/login',
	            	  headers:{'Authorization' : uic.token,
	            		  "Content-Type":"application/json"
	            	  },
	            	  data:{"username": uic.user.username, 
	            		    "oldPassword": oldPassH, 
	            		    "newPassword":newPassH}
	            	}).then(function successCallback(response) {
	            		//password changed successfully
	            		$mdDialog.hide();
	            		
	            	}, function errorCallback(response) {
	            		// password change went wrong
	            		switch(response.status){
	            		case 404:uic.toast("Incorrect password.");break;
	            		case 401:uic.toast("Unauthorized user.");break;
	            		default: uic.toast("An error has occured."); break;
	            		}
	            	  });
	            uic.user = "";
	            uic.token = "";
	            
    		}else{
    			//old password and new password are the same
    			uic.toast("Old password and new password are the same.");
    		}
    	}
    	else{
    		// passwords don't match
    		uic.toast("Password confirmation does not match.");
    	}
    }
});