 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $scope, $state, $mdSidenav, loginService, $http){
	var uic = this;
	
	//for notifications
    uic.toast = function(message){
        $scope.$parent.mastCtrl.toast(message);
    };
    
    //When user decides to cancel password update
    uic.cancel = function(){
    	//route to the appropriate homepage
		switch(loginService.getUser().userRole.name){
		case "associate":$state.go("assoc"); break;
		case "admin" : $state.go("admin"); break;
		case "superAdmin" : $state.go("super"); break;
		}
    }
    
    //when user submits updated password
    uic.submit = function(){
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
	            		uic.toast("password changed successfully");
	            		
	            		//route to the appropriate homepage
	            		switch(response.data.userRole.name){
	            		case "associate":$state.go("assoc"); break;
	            		case "admin" : $state.go("admin"); break;
	            		case "superAdmin" : $state.go("super"); break;
	            		}
	            		
	            	}, function errorCallback(response) {
	            		// password change went wrong
	            		switch(response.status){
	            		case 404:uic.toast("Password mismatch");break;
	            		case 401:uic.toast("User is unauthorized");break;
	            		default: uic.toast("An error has occured"); break;
	            		}
	            	  });
	            uic.user = "";
	            uic.token = "";
	            
    		}else{
    			//old password and new password are the same
    			uic.toast("Old password and new pasword are the same.");
    		}
    	}
    	else{
    		// passwords don't match
    		uic.toast("password confirmation does not match.");
    	}
    }
});