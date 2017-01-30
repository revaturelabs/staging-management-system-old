  
    var sms = angular.module("sms");
/**
 * @description AngularJs controller for adding associates in a batch.
 */
    sms.controller( "batchAddCtrl", function( $scope, $mdDialog, userService, batchTypeService, batchAddFactory ) {
	/**
     * @prop {function} Reference variable for this controller.
     */
    var bac = this;
    //bindables
    /**@prop {Date} Date to use for the batch's graduation date */
    bac.selectedDate = new Date();
    /** @prop {object} UserRole object. As we can only create associates, we have no need for anything else.     */
	var userRole = {name : "associate",
                    id : 1};
    /**@prop {array} Associates array, holds the created users */
    bac.associates = [];
    /**@prop {array} batchTypes array, holds all the types of batches that are available */
	bac.batchTypes = batchTypeService.getAll(function(response) {
		bac.batchTypes = response;
	}, function(error) {
		$mdDialog.cancel();
	});


	  // functions
	    bac.addNew = addNew;
        bac.onlyFridays = onlyFridays;
        bac.save = save;
        bac.saveHelper = saveHelper;
        bac.cancel = cancel;
        

    /**
     * @description Adds a new user to the batch array
     * @param {boolean} isValid Reference to the form's $valid.
     */
	function addNew(isValid) {
		if (isValid) {
			bac.associates.push({
				firstName : bac.firstName,
				lastName : bac.lastName
			});
			bac.firstName = "";
			bac.lastName = "";
			$scope.newAssociate.$setUntouched();
			$scope.newAssociate.$setPristine();
			angular.element("#firstName").focus();
		}
	}


	
	/**
     * @description Checks to see if the date given is a friday, as batches can only 
     * graduate on a friday.
     * @param {Date} date The date of graduation.
     */
	function onlyFridays(date) {
		var day = date.getDay();
		return day === 5;
	}
	
 
    /**
     * @description Saves the new associates into the database.
     * @param {boolean} isValid 
     */
    function save(isValid) {
        if ( isValid && bac.associates.length != 0 ) {
            var list = bac.associates;
            bac.saveHelper(list);
        }
    }

    /**
     * @description Helpter function for save.
     * @param {array} list the list of associates 
     */
	function saveHelper(list) {

        if (list.length != 0) {
            var addUser = list.shift();
            if (!addUser.username) {
                addUser.batchType = bac.selectedBatchType;
                addUser.graduationDate = bac.selectedDate;
                addUser.username = addUser.firstName[0].toLowerCase() + addUser.lastName.toLowerCase();
                addUser.userRole = userRole;
            }    
            addUser.hashedPassword = CryptoJS.SHA1(addUser.username).toString();
            
              // call REST controller to save user via userService
            userService.create(addUser, function(response) {
                batchAddFactory.addOneAssociate(response);
                bac.saveHelper(list);
            }, function(error) {
                if (error.status == 409) {
                    if (addUser.username.search("[0-9]+") == -1) {
                        addUser.username += "1";
                        list.unshift(addUser);
                        bac.saveHelper(list);
                    } else {
                        var num = parseInt(addUser.username.substring( addUser.username.search("[0-9]+"), addUser.username.length ) );
                        var baseUN = addUser.username.replace( num.toString(), "" );
                        baseUN += (num + 1).toString();
                        addUser.username = baseUN;
                        list.unshift(addUser);
                        bac.saveHelper(list);
                    }
                }
            });
        } else {
            $mdDialog.hide();
        }
	}

     /**
      * @description Cancel the currently opened dialog window.
      */
	function cancel() {
		$mdDialog.cancel();
	}

    //bac.recursion = 0;
});