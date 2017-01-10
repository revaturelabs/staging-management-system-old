
    var sms = angular.module( "sms" );

    sms.controller( "masterCtrl", function( $scope, $state, $location, $mdToast ){
        var mc = this;

          // functions
        mc.toast = function( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        };

            // removes role names from URL (now done by urlRouteProvider and state machine)
        // $scope.$on( "$locationChangeStart", function( event, newURL ) {
        //     var updatedURL = $location.url().replace("/super","");
        //     console.log(updatedURL.replace("/super",""));
        //     $location.url( updatedURL );
        // });

          // data

    });