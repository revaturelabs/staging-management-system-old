
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );

        function templateCtrl( $scope, loginService, $state ) {
            var tc = this;

              // listeners
                // changes title and actions based on recieved data
            $scope.$on( "changeFunction", function( events, data ) {
                tc.title = data.title;
                tc.actions = data.actions;
            });

            tc.userRole = loginService.getUser().userRole.name;
            
            if (tc.userRole == "associate"){
                $state.go("associateAttendance");
            }
            else {
                $state.go("managerAttendance");
            }
        }