
    var sms = angular.module( "sms" );

    sms.controller( "associateCtrl", function($http, $scope, $state, $mdSidenav, loginService, $mdDialog ){
        var asc = this;

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
					templateUrl: "html/views/updateInformation.html",
	                controller: "updateInfoCrtl as uInfoctrl",
	                locals: {needChangePass:false}
				}).then( function(){
					asc.toast("Password changed successfully.");
				},function(){
					asc.toast("Password change cancelled.");
				});
        	
        	
        };

        asc.associateAttendance= function(){
        	$mdSidenav("left").close();
        	$state.go("assocAttendance");
        	
        };
        
          // data
        asc.user = loginService.getUser();
        asc.token = loginService.getToken();

        loginService.checkPass(asc.user.username,
        		function(response){
        	console.log(response);
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