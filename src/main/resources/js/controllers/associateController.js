
    var sms = angular.module( "sms" );

    sms.controller( "associateCtrl", function( $scope, $state, $mdSidenav, loginService, $mdDialog ){
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
        	$state.go("ASupdateInfo");
        	
        };

        asc.associateAttendance= function(){
        	$mdSidenav("left").close();
        	$state.go("assocAttendance");
        	
        };
        
          // data
        asc.user = loginService.getUser();
        asc.token = loginService.getToken();
        //console.log(asc.user);
        

       loginService.checkPass(asc.user.username,
        		function(success){
    	   			console.log(success);
    			},function(error){
    				console.log(error);
    			});
    
        // check if user needs to change their password
        /*asc.checkPassword = function(){
        	
        	$http({
        		method: "GET",
        		url : "/api/v1/login/checkpass",
        		headers:{
        			"Authorization" : asc.token
        		},
        		params:{
        			"username": asc.user.username
        		}
        	}).then(function successCallback(response){
        		
        		
        	},function errorCallback(response) {
        	
        	});

        	
        }*/
        

    });