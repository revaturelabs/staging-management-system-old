
    angular
        .module( "sms" )
        .controller( "associateCtrl", associateCtrl );
        
    function associateCtrl( $scope, $state, $mdSidenav, loginService ) {
        var asc = this;

        console.log("This is running.");
        asc.toast = toast;
        asc.logout = logout;
        asc.user = loginService.getUser();

        $scope.$emit( "changeFunction", { 
            title: "Weekly Attendance", 
            actions: [ { 
                "function": asc.logout,
                "icon": "exit to app",
                "tooltip": "Logout"
            }]
        });

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