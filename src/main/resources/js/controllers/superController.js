
    var sms = angular.module( "sms" );

    sms.controller( "superCtrl", function( $scope, $state, $mdSidenav, $mdDialog, loginService, userService, batchAddFactory ){
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
        
        suc.updateInformation = function(){
        	$mdSidenav("left").close();
        	$state.go("SUupdateInfo");
        	
        };

        suc.newAssociate = function() {
            
              // opens a dialog to allows addition of a new batch of associates
                // opens another dialog upon success to show added associates' info
            $mdDialog.show({
                templateUrl: "html/templates/batchAdd.html",
                controller: "batchAddCtrl as bACtrl"
            }).then( function() {
                // console.log( batchAddFactory.getNewAssociates() );
                // $mdDialog.show({
                //     templateUrl: "html/templates/batchAddSuccess.html",
                //     locals: { "newAssociates": batchAddFactory.getNewAssociates }
                // });
                suc.toast("New associates added.");
            }, function() {
                suc.toast("Batch addition cancelled.");
            });
        };

            // returns array that represents the difference between the two input arrays
        suc.diffArray = function( bigArray, smallArray ){

            var diff = [];
            // var bigCopy = bigArray;
            // bigCopy.forEach( function(obj) {
            //     if (smallArray.indexOf(obj) != -1) {
            //         diff.push(obj);
            //     }
            // });
            diff = $(bigArray).not(smallArray).get();
            console.log(diff);
            return diff;
        };
          // data
        suc.user = loginService.getUser();

        userService.getAll(function(response){
            suc.users = response;
        }, function(error){
            suc.toast("Error retrieving all users.");
        });

    });