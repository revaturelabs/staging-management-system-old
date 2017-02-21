<<<<<<< HEAD

=======
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
    
    angular
        .module( "sms" )
        .controller( "associateInfoCtrl", associateInfoCtrl );
        
    function associateInfoCtrl( $scope, $state, $mdDialog, loginService, userService, batchAddFactory ) {
    	var aic = this;
        
          // bindables
            // data
            // functions
<<<<<<< HEAD
        aic.showName = showName;
        aic.showTaskPanel = showTaskPanel;
        aic.showTaskCertification = showTaskCertification;
=======
		
        aic.showName = showName;
	    aic.showTaskPanel = showTaskPanel;
		aic.showTaskCertification = showTaskCertification;
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        aic.cancel = cancel;

          // initilization
          // functions
<<<<<<< HEAD
    	    // show associate full name
=======
    	    
		/**
		 * @description Shows the associate's full name.
		 */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
    	function showName() {
    		return aic.user.firstName + " " + aic.user.lastName;
    	}
    	
<<<<<<< HEAD
    	    // show the associate panel info
=======

		/**
		 * @description Shows the associate's panel information.
		 */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
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
<<<<<<< HEAD
=======
			/**
			 * @description Shows the associate's certification information.
			 */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
    	function showTaskCertification() {
    		if ((aic.user.tasks != null) && (aic.user.tasks.length > 0)) {
    			for (var i = 0; i < aic.user.tasks.length; i++) {
    				if (aic.user.tasks[i].taskType.type == "Certification") {
    					return aic.user.tasks[i].note;
    				}
    			}
    		}
    	}
    	
<<<<<<< HEAD
    	    // cancel dialog 
    	function cancel() {
    		$mdDialog.cancel();
    	}

=======
    /**
	 * @description Closes the certification dialog window.
	 */
    	function cancel() {
    		$mdDialog.cancel();
    	}
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
    }