 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $mdToast,$scope, $state, $mdSidenav, loginService, $mdDialog,needChangePass){
	var uic = this;
	$scope.needChangePass = needChangePass;
	//for notifications
    uic.toast = function(message){
        $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
    };
    
    //When user decides to cancel password update
    uic.cancel = function(){
    	$mdDialog.cancel();
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

    	if(loginService.getUser().username === newPass.value){
    		uic.toast("Your password cannot be your username.");
    		return;
    	}
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
	            var data={"username": uic.user.username, 
        		    "oldPassword": oldPassH, 
        		    "newPassword":newPassH};
	            
	            loginService.changePass(data,
	            		function(){
	            			//password changed successfully
            				$mdDialog.hide();
            			},function(response){
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