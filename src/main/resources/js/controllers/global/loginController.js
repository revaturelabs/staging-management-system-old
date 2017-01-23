var sms = angular.module("sms");

sms.controller("loginCtrl", function($scope, $state, $cookies, loginService) {
	var lc = this;

    //binding

    lc.login = login;
   
    lc.loginSuccess = loginSuccess;
    lc.toast = toast;
    lc.cookieCheck = cookieCheck;

	  // functions
	    // calls master controller's toast function

	function toast(message) {
		// $scope.$parent.mastCtrl.toast(message);
        $scope.$emit( "toastMessage", message );
	};

	function login(isValid) {
		if (isValid) {
			var creds = {};
			creds.username = lc.username;
			creds.inputPass = CryptoJS.SHA1(lc.inputPass).toString();
			loginService.login(creds, lc.loginSuccess, function(error) {
				lc.toast(error.data.errorMessage);
			});
		}
	};

    function cookieCheck() {
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


    function loginSuccess(response) {

        loginService.addUser(response.user);
        loginService.addToken(response.authToken);
        $cookies.put( "RevatureSMSUsername", loginService.getUser().username );
        $cookies.put( "RevatureSMSToken", loginService.getToken() );

        $state.go("attendance");
        lc.toast("Login Successful.");
    };

	  // data

      // initialization
    lc.cookieCheck();
});