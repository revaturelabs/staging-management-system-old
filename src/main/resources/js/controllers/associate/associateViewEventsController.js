
    angular
        .module( "sms" )
        .controller( "associateViewEventsCtrl", associateViewEventsCtrl );

    function associateViewEventsCtrl( $scope, $mdDialog ) {
        var avec = this;

          // bindables
            // data
            // functions
         /**@var {function} okay function reference variable. */
        avec.okay = okay;
        
          // initialization

        /**
         * @description Closes the window showing the events
         */
        function okay() {
            $mdDialog.hide();
        }
    }