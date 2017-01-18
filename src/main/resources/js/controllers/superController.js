
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
        	
        	$mdDialog.show({
				templateUrl: "html/templates/updateInformation.html",
                controller: "updateInfoCrtl as uInfoctrl",
                locals: {needChangePass:false}
			}).then( function(){
				suc.toast("Password changed successfully.");
			},function(){
				suc.toast("Password change cancelled.");
			});
        	
        };
        suc.viewAttendance = function(){
        	$mdSidenav("left").close();
        	$state.go("superAttendance");
        	
        };

        suc.newAssociate = function() {
            
              // opens a dialog to allows addition of a new batch of associates
                // opens another dialog upon success to show added associates' info
            $mdDialog.show({
                templateUrl: "html/templates/batchAdd.html",
                controller: "batchAddCtrl as bACtrl"
            }).then( function() {
                $mdDialog.show({
                    templateUrl: "html/templates/batchAddSuccess.html",
                    controller: "batchAddSuccessCtrl as bASCtrl",
                    locals: { "newAssociates": batchAddFactory.getNewAssociates() },
                    bindToController: true
                }).then( function(){
                    batchAddFactory.resetAssociates();
                    alert("made it to batch creation event parent before function");
                    $scope.UserRefresh = function () {
                    	alert("made it to batch creation event parent");
                    	$scope.$broadcast('batchCreation', 'Send User Refresh');
                    }();
                    
                });
            }, function() {
                suc.toast("Batch addition cancelled.");
            });
        };

          // data
        suc.user = loginService.getUser();

        (function getUsers() {
        	userService.getAll(function(response){
        		suc.users = response;
	        }, function(error){
	            suc.toast("Error retrieving all users.");
	        });
        })();
        
      //set the title scope
        suc.title = "";

    });