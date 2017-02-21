
    angular
        .module( "sms" )
        .controller( "rootCtrl", rootCtrl );
<<<<<<< HEAD
        
=======
      /** 
       * @description Root controller for the Staging Management System application.
       */  
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
    function rootCtrl( $scope, $state, $location, $mdToast ) {
        var rc = this;
        
          // bindables
            // data
<<<<<<< HEAD
=======
            /**@prop{string} name Name of the application */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        rc.name = "Staging Management System";
            // functions

          // initializations
          // functions
          // listeners
<<<<<<< HEAD
            // calls root-level toast function
=======
         
         
            /**
             * @description Listener for deciding if a toast notifaction needs to be dispalyed.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        $scope.$on( "toastMessage", function( events, message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        })
    }