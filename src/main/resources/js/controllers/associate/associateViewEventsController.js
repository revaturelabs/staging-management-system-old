
    angular
        .module( "sms" )
        .controller( "associateViewEventsCtrl", associateViewEventsCtrl );

    function associateViewEventsCtrl( $scope, $mdDialog ) {
        var avec = this;

          // bindables
            // data
            // functions
        avec.okay = okay;
        
          // initialization

          // functions
        function okay() {
            $mdDialog.hide();
        }
    }