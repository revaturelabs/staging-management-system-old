 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $scope, $state, $mdSidenav, loginService, $http){
	var uic = this;
    	uic.oldPass = "";
    	uic.newPass = "";
    	uic.confirmPass = "";
    uic.submit = function(){
    	console.log("Data submitted");
    	console.log("oldPass: " + oldPass.value);
    	var oldPassH = CryptoJS.SHA1(oldPass.value).toString();
    	var newPassH = CryptoJS.SHA1(newPass.value).toString();
    	var confirmPassH = CryptoJS.SHA1(confirmPass.value).toString();
    	
 
    	
    	console.log(oldPassH + " : " +newPassH + " : " +  confirmPassH);
    	if(newPassH == confirmPassH){
    		if(oldPassH != newPassH){
	    		//new passwords match, make call to server
	    		console.log("Passwords match");
	    		
	    		// data
	            uic.user = loginService.getUser();
	            //uic.token = loginService.getToken();
	            uic.token = "7fp;^0Ssm2g[_RM8PI<rkBhPm6<5:1b9DA9A2N:<";
	            
	            //old and new passwords not be the same
	            
	            // URL: /api/v1/login
	            //use: PUT
	            //Header: Authorization: token
	            
	            //uic.userDTO = {"username": uic.user.username, "oldPassword": oldPassH, "newPassword":newPassH};
	            uic.userDTO = {"username": uic.user.username, "oldPassword": oldPassH, "newPassword":newPassH};
	           
	            console.log("token: " + uic.token);
	            console.log("username: " + uic.user.username);
	            
	            console.log("UserDto" + JSON.stringify(uic.userDTO));
	            
	            $http({
	            	  method: 'PUT',
	            	  url: '/api/v1/login',
	            	  headers:{'Authorization' : uic.token,
	            		  "Content-Type":"application/json"
	            	  },
	      
/*	            	  data:{
	            		  uic.userDTO
	            	  }*/
	            	  data:{"username": uic.user.username, "oldPassword": oldPassH, "newPassword":newPassH}
	            	}).then(function successCallback(response) {

	            		console.log("password changed successfully");
	            	  }, function errorCallback(response) {
	            		  console.log("password change went wrong. " + response.status + " : "+ response.statusText);
	            	  });
    		}else{
    			//old password and new password are the same
    			console.log("Old password and new pasword are the same.");
    		}
    	}
    	else{
    		// passwords don't match
    		console.log("password confirmation does not match.");
    	}
    	
    	oldPassH = "";
    	newPassH = "";
    	confirmPassH = "";
    }
});