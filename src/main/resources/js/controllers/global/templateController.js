
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );

        function templateCtrl( $scope, $state, loginService ) {
            var tc = this;

              // bindables
            tc.direct = direct;
            tc.toast = toast;
            tc.logout = logout;
            tc.settings = settings;

              // initialization
            tc.direct();

              // functions
                // directs to either associateAttendance or managerAttendance based on logged in user's role 
            function direct() {
               
                var userRole = loginService.getUser().userRole.name;    
               
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
            function settings() {

            };

              // listeners
                // changes title and actions based on recieved data
            $scope.$on( "setToolbar", function( events, data ) {
                tc.title = data.title;
                tc.actions = data.actions;
            });

        };