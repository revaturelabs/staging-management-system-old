
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

        /**
         * @description Closes the window showing the events
         */
        function okay() {
            $mdDialog.hide();
        }
    }