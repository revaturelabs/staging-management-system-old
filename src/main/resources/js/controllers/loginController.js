
    var sms = angular.module( "sms" );

    sms.controller( "loginCtrl", function( $scope, loginService ){
        var lc = this;

          // functions
        lc.login = function(isValid) {
            if (isValid) {
                var creds = {};
                creds.username = lc.username;
                creds.inputPass = lc.inputPass;
                loginService.login( creds, function(response){
                    lc.data = response;
                    console.log("Logged in.");
                }, function(error){
                    console.log("Error:", error.data.errorMessage);
                });
            }
        };

          // data

    });