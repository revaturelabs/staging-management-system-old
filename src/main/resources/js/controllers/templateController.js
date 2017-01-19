
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );

        function templateCtrl( $scope ) {
            var tc = this;

              // listeners
                // changes title and actions based on recieved data
            $scope.$on( "changeFunction", function( events, data ) {
                tc.title = data.title;
                tc.actions = data.actions;
            });
        }