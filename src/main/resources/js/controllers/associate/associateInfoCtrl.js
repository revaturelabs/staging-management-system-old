    
    angular
        .module( "sms" )
        .controller( "associateInfoCtrl", associateInfoCtrl );
        
    function associateInfoCtrl( $scope, $state, $mdDialog, loginService, userService, batchAddFactory ) {
    	var aic = this;
        
          // bindables
            // data
            // functions
		/**@var {function} showName function reference variable. */
        aic.showName = showName;
		/**@var {function} showTaskPanel function reference variable. */
        aic.showTaskPanel = showTaskPanel;
		/**@var {function} showTaskCertification function reference variable. */
        aic.showTaskCertification = showTaskCertification;
		/**@var {function} cancel function reference variable. */
        aic.cancel = cancel;

          // initilization
          // functions
    	    
		/**
		 * @description Shows the associate's full name.
		 */
    	function showName() {
    		return aic.user.firstName + " " + aic.user.lastName;
    	}
    	

		/**
		 * @description Shows the associate's panel information.
		 */
    	function showTaskPanel() {
    		if ((aic.user.tasks != null) && (aic.user.tasks.length > 0)) {
    			for (var i = 0; i < aic.user.tasks.length; i++) {
    				if (aic.user.tasks[i].taskType.type == "Panel") {
    					return aic.user.tasks[i].note;
    				}
    			}
    		}
    	}
    	
    	    // show the associate certification info
			/**
			 * @description Shows the associate's certification information.
			 */
    	function showTaskCertification() {
    		if ((aic.user.tasks != null) && (aic.user.tasks.length > 0)) {
    			for (var i = 0; i < aic.user.tasks.length; i++) {
    				if (aic.user.tasks[i].taskType.type == "Certification") {
    					return aic.user.tasks[i].note;
    				}
    			}
    		}
    	}
    	
    /**
	 * @description Closes the certification dialog window.
	 */
    	function cancel() {
    		$mdDialog.cancel();
    	}
    }