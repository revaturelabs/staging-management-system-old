
    var sms = angular.module( "sms" );

    sms.controller( "adminCtrl", function( $scope, $state, $mdSidenav, loginService ){
        var adc = this;
    
          // functions
        adc.openMenu = function() {
            $mdSidenav("left").open();
        };

        adc.toast = function(message){
            $scope.$parent.mastCtrl.toast(message);
        };

        adc.logout = function() {
            adc.user = {};
            adc.token = "";
            loginService.logout();
            adc.toast("Logged out.");
            $state.go("login");
        };
        
        adc.updateInformation = function(){
        	$mdSidenav("left").close();
        	$state.go("ADupdateInfo");
        	
        };

          // data
        adc.user = loginService.getUser();
        adc.token = loginService.getToken();
        
        //set the title scope
        adc.title = "";

    });