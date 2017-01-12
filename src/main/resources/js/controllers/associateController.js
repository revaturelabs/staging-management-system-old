
    var sms = angular.module( "sms" );

    sms.controller( "associateCtrl", function( $scope, $state, $mdSidenav, loginService ){
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

          // data
        asc.user = loginService.getUser();
        asc.token = loginService.getToken();

    });