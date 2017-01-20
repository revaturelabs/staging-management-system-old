
    var sms = angular.module( "sms" );

    sms.controller( "rootCtrl", function( $scope, $state, $location, $mdToast ){
        var rc = this;

          // functions
            // listener to call root-level toast function
        $scope.$on( "toastMessage", function( events, message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        } );

          // data

          // initialization

    });