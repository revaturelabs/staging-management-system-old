var sms = angular.module("sms");

sms.controller("loginCtrl", function($scope, $state, loginService) {
	var lc = this;

	// functions
	// calls master controller's toast function
	lc.toast = function(message) {
		$scope.$parent.mastCtrl.toast(message);
	};

	lc.login = function(isValid) {
		if (isValid) {
			var creds = {};
			creds.username = lc.username;
			creds.inputPass = CryptoJS.SHA1(lc.inputPass).toString();
			loginService.login(creds, function(response) {
				loginService.addUser(response.user);
				loginService.addToken(response.token);
				switch (response.user.userRole.name) {
				case "superAdmin":
					$state.go("superAttendance");
					lc.toast("Logged in.");
					break;
				case "admin":

					$state.go("adminAttendance");

					lc.toast("Logged in.");
					break;
				case "associate":

					lc.toast("Logged in and attendance logged.");
					$state.go("assocAttendance");

					break;
				default:
					break;
				}
			}, function(error) {
				lc.toast(error.data.errorMessage);
			});
		}
	};

	// data

});