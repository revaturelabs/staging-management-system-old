
    var sms = angular.module( "sms" );

    sms.controller( "superCtrl", function( $scope, loginService ){
        var suc = this;

          // functions

          // data
        suc.user = loginService.getUser();
    });