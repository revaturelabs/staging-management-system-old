  
    var sms = angular.module("sms");

    sms.controller( "jobAddCtrl", function( $scope, $mdDialog, userService,jobEventTypeService) {
	var jac = this;

	 // functions
	jac.getEventTypes = getEventTypes;
	
	 // initialization
	 jac.getEventTypes();
	 
	 // retrieves all Job Event Types
	 // hope you get a merge conflict kid
	   function getEventTypes() {
	    	jobEventTypeService.getAll(function(response) {
	        	jac.eventTypes = response;
	        	
	        }, function() {
	            
	        });} 
	 
	 
	  // date settings
	jac.selectedDate = new Date();
		

    jac.save = function(isValid) {
        // if we entered in all the required data
    	if (isValid) {
    		
    		// make a new user job object
    		var newJob = {
    				
    				assignment: {
    					companyName: jac.companyName,
    					location: jac.companylocation,
    					jobTitle: jac.jobTitle
    				}, 
    				type: jac.selectedEventType, 
    				date: new Date(jac.selectedDate),
    				note: jac.note,
    				location: jac.eventLocation
    		}
    		
    		// add the new job to the users list of jobs
    		jac.selectedUser.events.push(newJob);
    		
    		// update the selected user
    		userService.update( jac.selectedUser, function() {
    			$mdDialog.hide();
    		}, function(error) {
    			$mdDialog.cancel();
    		})
    		
        }
    };

	jac.cancel = function() {
		$mdDialog.cancel();
	}
});