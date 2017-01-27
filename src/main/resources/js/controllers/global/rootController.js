
    angular
        .module( "sms" )
        .controller( "rootCtrl", rootCtrl );
      /** 
       * @description Root controller for the Staging Management System application.
       */  
    function rootCtrl( $scope, $state, $location, $mdToast ) {
        var rc = this;
        
          // bindables
            // data
            /**@prop{string} name Name of the application */
        rc.name = "Staging Management System";
            // functions

          // initializations
          // functions
          // listeners
         
         
            /**
             * @description Listener for deciding if a toast notifaction needs to be dispalyed.
             */
        $scope.$on( "toastMessage", function( events, message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        })
    }