// This file should be unnecessary, will keep for the time being
    angular
        .module( "sms" )
        .controller( "associateCtrl", associateCtrl );
        
    function associateCtrl( $scope, $state, $mdSidenav, loginService ) {
        var asc = this;

        asc.toast = toast;
        asc.logout = logout;
        asc.user = loginService.getUser();

            // functions
        asc.openMenu = function() {
            $mdSidenav("left").open();
        };

        asc.toast = function(message){
            // $scope.$parent.mastCtrl.toast(message);
            $scope.$emit( "toastMessage", message );
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
        // asc.user = loginService.getUser();

    };