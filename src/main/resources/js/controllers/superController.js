
    var sms = angular.module( "sms" );

    sms.controller( "superCtrl", function( $scope, $state, $mdSidenav, $mdDialog, loginService, userService ){
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
            suc.toast("Logged out.");
            $state.go("login");
        };

        suc.newAssociate = function() {
            $mdDialog.show({
                templateUrl: "html/templates/batchAdd.html",
                controller: "batchAddCtrl as bACtrl"
            });
        };

          // data
        suc.user = loginService.getUser();

        userService.getAll(function(response){
            suc.users = response;
        }, function(error){
            suc.toast("Error retrieving all users.");
            // console.log(error);
        });

    });