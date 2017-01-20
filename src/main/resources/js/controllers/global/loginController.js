var sms = angular.module("sms");

sms.controller("loginCtrl", function($scope, $state, $cookies, loginService) {
	var lc = this;

	  // functions
	    // calls master controller's toast function
	lc.toast = function(message) {
        $scope.$emit( "toastMessage", message );
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

        // var usernameCookie = $cookies.get("RevatureSMSUsername");
        // var tokenCookie = $cookies.get("RevatureSMSToken");
        var usernameCookie = undefined;
        var tokenCookie = undefined;

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
        $state.go("attendance");
    };

	  // data

      // initialization
    lc.cookieCheck();
});