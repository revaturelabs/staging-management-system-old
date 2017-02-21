
    angular
        .module( "sms" )
        .controller( "associateViewEventsCtrl", associateViewEventsCtrl );

    function associateViewEventsCtrl( $scope, $mdDialog ) {
        var avec = this;

          // bindables
            // data
            // functions
<<<<<<< HEAD
=======
         /**@var {function} okay function reference variable. */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        avec.okay = okay;
        
          // initialization

<<<<<<< HEAD
          // functions
=======
        /**
         * @description Closes the window showing the events
         */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function okay() {
            $mdDialog.hide();
        }
    }