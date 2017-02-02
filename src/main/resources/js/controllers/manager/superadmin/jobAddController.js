  
    var sms = angular.module("sms");

    sms.controller( "jobAddCtrl", function( $scope, $mdDialog, userService, batchTypeService, batchAddFactory ) {
	var jac = this;

	 // functions
	jac.getEventTypes = getEventTypes;
	
	
	 // initialization
	 jac.getEventTypes();
	 
	 // retrieves all Job Event Types
	   function getEventTypes() {
	    	jobEventTypeService.getAll(function(response) {
	        	jac.eventType = response;
	        	
	        }, function() {
	            
	        });} 
	 
	 
	  // functions
	    // adds new associate to list

	jac.addNew = function(isValid) {
		if (isValid) {
			jac.associates.push({
				firstName : jac.firstName,
				lastName : jac.lastName
			});
			jac.firstName = "";
			jac.lastName = "";
			$scope.newAssociate.$setUntouched();
			$scope.newAssociate.$setPristine();
			angular.element("#firstName").focus();
		}
	};
	
	
	  // date settings
	jac.selectedDate = new Date();
	
	jac.onlyFridays = function(date) {
		var day = date.getDay();
		return day === 5;
	};
	
	// data
	jac.associates = [];
	jac.batchTypes = batchTypeService.getAll(function(response) {
		jac.batchTypes = response;
	}, function(error) {
		$mdDialog.cancel();
	});
	
	    // hard coded value for userRole object of associate
	var userRole = {};
	userRole.name = "associate";
	userRole.id = 1;

    jac.save = function(isValid) {
        if ( isValid && jac.associates.length != 0 ) {
            var list = jac.associates;
            jac.saveHelper(list);
        }
    };

	jac.saveHelper = function(list) {

        if (list.length != 0) {
            var addUser = list.shift();
            if (!addUser.username) {
                addUser.batchType = jac.selectedBatchType;
                addUser.graduationDate = jac.selectedDate;
                addUser.username = addUser.firstName[0].toLowerCase() + addUser.lastName.toLowerCase();
                addUser.userRole = userRole;
            }    
            addUser.hashedPassword = CryptoJS.SHA1(addUser.username).toString();
            
              // call REST controller to save user via userService
            userService.create(addUser, function(response) {
                batchAddFactory.addOneAssociate(response);
                jac.saveHelper(list);
            }, function(error) {
                if (error.status == 409) {
                    if (addUser.username.search("[0-9]+") == -1) {
                        addUser.username += "1";
                        list.unshift(addUser);
                        jac.saveHelper(list);
                    } else {
                        var num = parseInt(addUser.username.substring( addUser.username.search("[0-9]+"), addUser.username.length ) );
                        var baseUN = addUser.username.replace( num.toString(), "" );
                        baseUN += (num + 1).toString();
                        addUser.username = baseUN;
                        list.unshift(addUser);
                        jac.saveHelper(list);
                    }
                }
            });
        } else {
            $mdDialog.hide();
        }
	}

	jac.cancel = function() {
		$mdDialog.cancel();
	}

    jac.recursion = 0;
});