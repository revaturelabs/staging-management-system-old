
    var sms = angular.module( "sms" );

    sms.controller( "rootCtrl", function( $scope, $state, $location, $mdToast ){
        var rc = this;
        rc.addToastListener = addToastListener;
        
          // functions
            // listener to call root-level toast function
            
       function addToastListener(){
           $scope.$on( "toastMessage", function( events, message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        } );
       }
        

          // data

          // initialization
          rc.addToastListener();

    });