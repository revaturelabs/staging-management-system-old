 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $scope, $state, $mdSidenav, loginService){
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
    	
 
    	
    	console.log(newPassH + " : " +  confirmPassH);
    	if(newPassH == confirmPassH){
    		//new passwords match, make call to server
    		console.log("Passwords match");
    		
    		// data
            uic.user = loginService.getUser();
            uic.token = loginService.getToken();
            
            // URL: /api/v1/login
            //use: PUT
            uic.UserDTO = {"username": username, "hashedPassword": oldPassH};
            
            
    		
    	}
    	else{
    		// passwords don't match
    		console.log("Passwords Don't Match");
    	}
    	
    	oldPassH = "";
    	newPassH = "";
    	confirmPassH = "";
    }
});