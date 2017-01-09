
    var sms = angular.module( "sms" );

    sms.controller( "masterCtrl", function( $scope, $state, $location, $mdToast ){
        var mc = this;

            // functions
        mc.toast = function( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        };

        $scope.$on( "$locationChangeStart", function( event, newURL ) {
            // var updatedURL = $location.url();
            // $location.url( updatedURL );
        });

            // data

    });