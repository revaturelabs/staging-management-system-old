
    angular
        .module( "sms" )
        .controller( "loginCtrl", loginCtrl );
        
    function loginCtrl( $scope, $state, $cookies, loginService ) {
        var lc = this;

          // bindables
            // data
            //functions
        lc.toast = toast;
        lc.login = login;
        lc.cookieCheck = cookieCheck;
        lc.loginSuccess = loginSuccess;

          // initialization
        lc.cookieCheck();

          // functions
            // calls master controller's toast function
        function toast(message) {
            $scope.$emit( "toastMessage", message );
        };

          // logs in the user by conventional means
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

          // logs in the user by means of cookies stored in browser session
        function cookieCheck() {
            lc.cookieLoad = true;

            var usernameCookie = $cookies.get("RevatureSMSUsername");
            var tokenCookie = $cookies.get("RevatureSMSToken");
            // var usernameCookie = undefined;
            // var tokenCookie = undefined;

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



          // sets user and token data and changes state upon successful login
        function loginSuccess(response) {

            loginService.addUser(response.user);
            loginService.addToken(response.authToken);
            $cookies.put( "RevatureSMSUsername", loginService.getUser().username );
            $cookies.put( "RevatureSMSToken", loginService.getToken() );

            $state.go("attendance");
            lc.toast("Login Successful.");     
        };
    };