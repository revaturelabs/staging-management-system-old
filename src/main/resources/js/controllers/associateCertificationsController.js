var sms = angular.module("sms");

sms.controller("associateCertificationsCtrl", function($scope, $state, $mdDialog,
		loginService) {

	var acc = this;
	
	//fix this
	 acc.toast = function(message){
	        $scope.$parent.$parent.mastCtrl.toast(message);
	    };
	
	
	acc.user = loginService.getUser();
	acc.gradDate = new Date(acc.user.graduationDate);
	acc.formattedGradDate = ((acc.gradDate.getMonth()) + 1) + "/" + acc.gradDate.getDate() + "/" + acc.gradDate.getFullYear();
	/*alert(new Date(acc.user.graduationDate));*/

	acc.myDate = new Date();
	acc.minDate = new Date();
	acc.maxDate = new Date(acc.gradDate.getFullYear(), acc.gradDate
			.getMonth() + 1, acc.gradDate.getDate());
	acc.onlyWeekendsPredicate = function(date) {
		var day = date.getDay();
		return day === 0 || day === 6;
	}
	
	acc.cancel = function() {
		//acc.toast("Cancelled");
		$mdDialog.cancel();
	}
	
	acc.submit = function() {
		acc.toast("Sumbited");
		// update task
		
		
		//??????????????
		//check for empty passwords
    	/*if(oldPass.value === ""){
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
    	}*/
		//????????????
		
		
	};

});