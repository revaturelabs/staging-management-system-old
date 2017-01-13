var sms = angular.module("sms");

sms.controller("batchAddCtrl", function($scope, $mdDialog, userService,
		batchTypeService) {
	var bac = this;

	// functions
	// adds new associate to list

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

	// data
	bac.associates = [];
	bac.batchTypes = batchTypeService.getAll(function(response) {
		bac.batchTypes = response;
	}, function(error) {
		$mdDialog.cancel();
	});

	// Hard coded value for userRole object of associate
	var userRole = {};
	userRole.name = "associate";
	userRole.id = 1;

    bac.save = function(isValid) {
        if (isValid) {
            var list = bac.associates;
            bac.saveHelper(list);
        }
    };

	bac.saveHelper = function(list) {

        if (list.length != 0) {
            var addUser = list.shift();
            if (!addUser.username) {
                addUser.batchType = bac.selectedBatchType;
                addUser.username = addUser.firstName[0].toLowerCase() + addUser.lastName.toLowerCase();
                addUser.userRole = userRole;
            }    
            addUser.hashedPassword = CryptoJS.SHA1(addUser.username).toString();
            
            // call rest controller to save user via userService
            userService.create(addUser, function(response) {
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