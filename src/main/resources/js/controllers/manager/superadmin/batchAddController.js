  
    var sms = angular.module("sms");

    sms.controller( "batchAddCtrl", function( $scope, $mdDialog, userService, trainerService, batchTypeService, batchAddFactory ) {
	var bac = this;
bac.trainer = "";
	  // functions
	bac.getTrainers = getTrainers
	
	// initialization
	bac.getTrainers();
	    // adds new associate to list

	
	function getTrainers() {
        trainerService.getAll(function(response) {
        	bac.trainers = response;
        	
        }, function() {
            
        });}
	
	
	
	
	bac.addNew = function(isValid) {
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
	};

	  // date settings
	bac.selectedDate = new Date();
	
	bac.onlyFridays = function(date) {
		var day = date.getDay();
		return day === 5;
	};
	
	// data
	bac.associates = [];
	bac.batchTypes = batchTypeService.getAll(function(response) {
		bac.batchTypes = response;
	}, function(error) {
		$mdDialog.cancel();
	});
	
	 

    bac.save = function(isValid) {
        if ( isValid && bac.associates.length != 0 ) {
            var list = bac.associates;
            bac.saveHelper(list);
        }
    };

	bac.saveHelper = function(list) {

        if (list.length != 0) {
            var addUser = list.shift();
            if (!addUser.username) {
                addUser.batchType = bac.selectedBatchType;
                addUser.graduationDate = bac.selectedDate;
                addUser.username = addUser.firstName[0].toLowerCase() + addUser.lastName.toLowerCase();
                addUser.trainer = bac.trainer;
               
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

	bac.cancel = function() {
		$mdDialog.cancel();
	}

    bac.recursion = 0;
});