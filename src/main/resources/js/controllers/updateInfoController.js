 var sms = angular.module( "sms" );
sms.controller( "updateInfoCrtl", function( $scope, $state, $mdSidenav, loginService){
	var uic = this;
    	uic.oldPass = "";
    	uic.newPass = "";
    	uic.confirmPass = "";
    uic.submit = function(){
    	console.log("Data submitted");
    	console.log("oldPass: " + oldPass.value);
    	/*uic.oldPassH = CryptoJS.SHA1(oldPass.value).toString();
    	uic.newPassH = CryptoJS.SHA1(newPass.value).toString();
    	uic.confirmPassH = CryptoJS.SHA1(confirmPass.value).toString();*/
    	
 
    	
    	
    	if(uic.newPass == uic.confirmPass){
    		//new passwords match, make call to server
    		console.log("Passwords match");
    	}
    	else{
    		// passwords don't match
    		console.log("Passwords Don't Match");
    	}
    }
});