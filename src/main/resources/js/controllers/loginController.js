var sms = angular.module("sms");

sms.controller("loginCtrl", function($scope, $state, $cookies, loginService) {
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
					lc.toast("Logged in.");
					$state.go("superAttendance");
					break;
				case "admin":
					lc.toast("Logged in.");
					$state.go("adminAttendance");
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

    lc.cookieCheck = function() {
        var usernameCookie = $cookies.get("revature.pro/username");
        var tokenCookie = $cookies.get("revature.pro/authToken");
        console.log(usernameCookie, tokenCookie);
        if ( usernameCookie && tokenCookie ) {
            loginService.addToken(tokenCookie);
            loginService.cookieLogin( usernameCookie, function(){
                lc.toast("cookieLogin works!");
            }, function(){
                lc.toast("cookieLogin doesn't work!");
            });
        }
    };

	  // data


      // initialization
    lc.cookieCheck();
});