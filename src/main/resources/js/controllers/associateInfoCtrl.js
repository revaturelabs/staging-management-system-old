
    var sms = angular.module( "sms" );

    sms.controller( "associateInfoCtrl", function( $scope, $state, $mdDialog, loginService, userService, batchAddFactory ){
    	var aic = this;
    	
    	//show associate full name
    	aic.showName=function(){
    		return aic.user.firstName + " " + aic.user.lastName;
    	}
    	
    	// show the associate panel info
    	aic.showTaskPanel = function() {
    		if ((aic.user.tasks != null) && (aic.user.tasks.length > 0)) {

    			for (var i = 0; i < aic.user.tasks.length; i++) {
    				if (aic.user.tasks[i].taskType.type == "Panel") {

    					return aic.user.tasks[i].note;

    				}
    			}
    		}

    	}
    	
    	// show the associate certification info
    	aic.showTaskCertification = function() {
    		if ((aic.user.tasks != null) && (aic.user.tasks.length > 0)) {

    			for (var i = 0; i < aic.user.tasks.length; i++) {
    				if (aic.user.tasks[i].taskType.type == "Certification") {

    					return aic.user.tasks[i].note;

    				}
    			}
    		}

    	}
    	
    	//cancel dialog 
    	
    	aic.cancel=function(){
    		$mdDialog.cancel();
    	}

    });