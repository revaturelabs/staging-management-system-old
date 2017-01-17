
    var sms = angular.module( "sms" );

    sms.controller( "associateCtrl", function( $scope, $state, $mdSidenav, $mdDialog, loginService ){
        var asc = this;

          // functions
        asc.openMenu = function() {
            $mdSidenav("left").open();
        };

        asc.toast = function(message){
            $scope.$parent.mastCtrl.toast(message);
        };
        
        asc.logout = function() {
            asc.user = {};
            asc.token = "";
            loginService.logout();
            asc.toast("Logged out.");
            $state.go("login");
        };
        
        asc.updateInformation = function(){
        	$mdSidenav("left").close();
        	$state.go("ASupdateInfo");
        	
        };

        asc.associateAttendance= function(){
        	$mdSidenav("left").close();
        	$state.go("assocAttendance");
        	
        };
        
        asc.assocCertifications = function(){
        	$mdDialog.show({
        		templateUrl: "html/templates/scheduleCertification.html",
        		controller: "associateCertificationsCtrl as assCertCtrl"
        	}).then( function() {
        		alert("success");
               /* $mdDialog.show({
                    templateUrl: "html/templates/batchAddSuccess.html",
                    controller: "batchAddSuccessCtrl as bASCtrl",
                    locals: { "newAssociates": batchAddFactory.getNewAssociates() },
                    bindToController: true
                }).then( function(){
                    batchAddFactory.resetAssociates();
                });*/
                
            }, function() {
            	asc.toast("Certification Schedule Cancelled");
                /*suc.toast("Batch addition cancelled.");*/
            });
        };
        
          // data
        asc.user = loginService.getUser();
        asc.token = loginService.getToken();

    });