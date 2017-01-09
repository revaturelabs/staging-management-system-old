
    var sms = angular.module( "sms" );

    sms.controller( "superCtrl", function( $scope, loginService ){
        var suc = this;

          // functions
        suc.openMenu = function() {
            $mdSidenav("left").open();
        };
          // data
        suc.user = loginService.getUser();
        
    });