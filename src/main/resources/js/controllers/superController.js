
    var sms = angular.module( "sms" );

    sms.controller( "superCtrl", function( $scope, $mdSidenav ){
        var suc = this;

          // functions
        suc.openMenu = function() {
            $mdSidenav("left").open();
        };

          // data

    });