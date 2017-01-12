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

	bac.save = function(isValid, list) {
		if (isValid) {
			if (list.length != 0) {
				
				var addUser = list.shift();
				addUser.batchType = bac.selectedBatchType;
				addUser.username = addUser.firstName.substring(0, 1)
						.toLowerCase()
						+ addUser.lastName.toLowerCase();
				addUser.hashedPassword = CryptoJS.SHA1(addUser.username)
						.toString();
				addUser.userRole = userRole;
				// call rest controller to save user via userService
				userService.create(addUser, function(response) {
				}, function(error) {
				});
				this.save(isValid,list);
			} else {
				$mdDialog.hide();
			}
		}
	}

	bac.cancel = function() {
		$mdDialog.cancel();
	}
});