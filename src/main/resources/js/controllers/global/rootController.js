
    angular
        .module( "sms" )
        .controller( "rootCtrl", rootCtrl );
        
    function rootCtrl( $scope, $state, $location, $mdToast ) {
        var rc = this;
        
            // bindables
            // data
            // functions

            // initializations
            // functions
            // listeners
            // calls root-level toast function
        $scope.$on( "toastMessage", function( events, message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        });
    };