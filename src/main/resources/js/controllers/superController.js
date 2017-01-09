
    var sms = angular.module( "sms" );

    sms.controller( "superCtrl", function( $scope, $state, $mdSidenav, loginService ){
        var suc = this;

          // functions
        suc.openMenu = function() {
            $mdSidenav("left").open();
        };

        suc.toast = function(message){
            $scope.$parent.mastCtrl.toast(message);
        };

        suc.logout = function() {
            suc.user = {};
            suc.token = "";
            loginService.logout();
            sup.toast("Logged out.");
            $state.go("login");
        };

          // data
        suc.user = loginService.getUser();
        suc.token = loginService.getToken();

    });