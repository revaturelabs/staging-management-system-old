
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );

        function templateCtrl( $scope, $state, loginService ) {
            var tc = this;

              // bindables
            tc.direct = direct;
            tc.toast = toast;
            tc.logout = logout;

              // initialization
            tc.direct();
            
              // functions
            function direct() {
                var userRole = loginService.getUser().userRole.name;    
            
                if (userRole == "associate"){
                    $state.go("associateAttendance");
                } else {
                    $state.go("managerAttendance");
                }
            };

            function toast( message ) {
                $scope.$emit( "toastMessage", message );
            };

            function logout() {
                loginService.logout();
                tc.toast("Logged out.");
                $state.go("login");
            };

              // listeners
                // changes title and actions based on recieved data
            $scope.$on( "changeFunction", function( events, data ) {
                tc.title = data.title;
                tc.actions = data.actions;
            });

        };