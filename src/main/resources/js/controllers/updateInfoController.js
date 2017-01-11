 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $scope, $state, $mdSidenav, loginService, $http){
	var uic = this;
	
    uic.toast = function(message){
        $scope.$parent.mastCtrl.toast(message);
    };
    
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
	            //uic.token = loginService.getToken();
	            uic.token = "7fp;^0Ssm2g[_RM8PI<rkBhPm6<5:1b9DA9A2N:<";
	            
	            //old and new passwords not be the same
	            
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
	            		console.log(uic.user);
	            		$state.go("assoc");
	            	}, function errorCallback(response) {
	            		// password change went wrong
	            		switch(response.status){
	            		//should this be different?
	            		case 404:uic.toast("Password mismatch");break;
	            		case 401:uic.toast("User is unauthorized");break;
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