
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );

    function templateCtrl( $scope, $state, $mdDialog, loginService ) {
        var tc = this;

          // bindables
            // data
        tc.user = loginService.getUser();

            // functions
        tc.checkPass = checkPass;
        tc.direct = direct;
        tc.toast = toast;
        tc.logout = logout;
        tc.settings = settings;

          // initialization
        tc.checkPass();
        tc.direct();

          // functions
            // pops up dialog if password is default username
        function checkPass() {
            loginService.checkPass( tc.user.username, function(response) {
                if ( response.update == true ) {
                    tc.settings( response.update );
                }
            }, function( error ) {
                tc.toast( "Could not check if password needs to be updated." );
            });
        }
        
            // directs to either associateAttendance or managerAttendance based on logged in user's role 
        function direct() {
            var userRole = tc.user.userRole.name;
        
            if (userRole == "associate"){
                $state.go("associateAttendance");
            } else {
                $state.go("managerAttendance");
            }

            tc.toast("Login Successful.");
        };

            // calls toast function in rootController
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        };

            // logs out
        function logout() {
            loginService.logout();
            tc.toast("Logged out.");
            $state.go("login");
        };

            // opens dialog to allow changing of password
        function settings( needChange ) {
            $mdDialog.show({
					templateUrl: "html/templates/updateInformation.html",
	                controller: "updateInfoCrtl",
                    controllerAs: "uInfoCtrl",
	                locals: { needChangePass: needChange },
                    bindToController: true,
                    escapeToClose: !needChange,
                    clickOutsideToClose: !needChange
				}).then( function() {
					tc.toast( "Password changed successfully." );
				}, function() {
					tc.toast( "Password change cancelled." );
				});
        };

          // listeners
            // changes title and actions based on recieved data
        $scope.$on( "setToolbar", function( events, data ) {
            tc.title = data.title;
            tc.actions = data.actions;
        });

    };