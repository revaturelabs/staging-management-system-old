
    angular
        .module( "sms" )
        .controller( "loginCtrl", loginCtrl );
      /** 
       * @description AngularJS controller for logging in functionality. 
       *     
       */

    function loginCtrl( $scope, $state, $cookies, $mdToast, loginService ) {
        var lc = this;

          // bindables
            // data
            //functions
     

        lc.toast = toast;
        lc.login = login;
        lc.cookieCheck = cookieCheck;
        lc.loginSuccess = loginSuccess;
        lc.associateCertToast = associateCertToast;

          // initialization
        lc.cookieCheck();

          // functions

            /**
             * @description Displays a toast notification.
             * @param {string} message The value of the message to be shown.
             */
        function toast(message) {
            $scope.$emit( "toastMessage", message );
        }

          /**
           * @description Logs in the user based on entering in username and password.
           * @param {boolean} isValid Boolean that matches the results of $valid in the html form.
           */
        function login(isValid) {
            if (isValid) {
                var creds = {};
                creds.username = lc.username;
                creds.inputPass = CryptoJS.SHA1(lc.inputPass).toString();
                loginService.login(creds, lc.loginSuccess, function(error) {
                    lc.toast(error.data.errorMessage);
                });
            }
        }


          /**
           * @description Logs in the user if the two cookies related to being logged
           * in the app is found, which means the user was already logged in.
           */
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
        }

          /**
           * @description Implementation of logging the user in that runs if log in is successful.
           * @param {object} response The data given from the server on providing valid login credentials.
           */
        function loginSuccess( response ) {

            loginService.addUser(response.user);
            loginService.addToken(response.authToken);
            $cookies.put( "RevatureSMSUsername", loginService.getUser().username );
            $cookies.put( "RevatureSMSToken", loginService.getToken() );

            $state.go("attendance");
            lc.toast("Login Successful.");
            lc.associateCertToast();
        }

          /**
           * @description Displays a toast notification if the logged in user has a scheduled certification
           * exam within the next 2 weeks.
           */
        function associateCertToast() {
            var user = loginService.getUser();
            if ( user.userRole.name == "associate" ) {
                user.tasks.forEach( function(task) {
                    if (task.taskType == "Certification") {
                        var now = new Date();
                        if ( task.date.getTime() > now.getTIme() ) {
                            var message;
                            if ( task.date.getTime() > new Date( now.getFullYear(), now.getMonth(), now.getDate() + 14).getTime() ) {
                                message = task.note + " scheduled for " + task.date.getMonth() + "/" + task.date.getDate();
                            } else {
                                message = "You have your " + task.note + " scheduled for ";
                                if ( new Date( today.getFullYear(), today.getMonth(), today.getDate() + 1).getTime() > today.getTime() ) {
                                    message += "tomorrow.";
                                } else {
                                    message += "__ days from now.";
                                }
                            }
                            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true).hideDelay(0) );
                        }
                    }
                })
            }
        }
    }