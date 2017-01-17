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
			loginService.login(creds, lc.loginSuccess, function(error) {
				lc.toast(error.data.errorMessage);
			});
		}
	};

    lc.cookieCheck = function() {
        lc.cookieLoad = true;
        var usernameCookie = $cookies.get("RevatureSMSUsername");
        var tokenCookie = $cookies.get("RevatureSMSToken");
        if ( usernameCookie && tokenCookie ) {
            loginService.addToken(tokenCookie);
            loginService.cookieLogin( usernameCookie, function( response ){
                lc.loginSuccess(response);
            }, function( error ){
                lc.cookieLoad = false;
            });
        } else {
            lc.cookieLoad = false;
        }
    };

    lc.loginSuccess = function(response) {
        loginService.addUser(response.user);
        loginService.addToken(response.authToken);
        $cookies.put( "RevatureSMSUsername", loginService.getUser().username );
        $cookies.put( "RevatureSMSToken", loginService.getToken() );
        switch (loginService.getUser().userRole.name) {
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
    };

	  // data

      // initialization
    lc.cookieCheck();
});