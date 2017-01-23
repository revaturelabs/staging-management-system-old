
    angular
        .module( "sms" )
        .controller( "rootCtrl", rootCtrl );
        
    function rootCtrl( $scope, $state, $location, $mdToast ) {
        var rc = this;
        
          // bindables
            // data
        rc.name = "Staging Management System";
            // functions

          // initializations
          // functions
          // listeners
            // calls root-level toast function
        $scope.$on( "toastMessage", function( events, message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        })
    };