
    var sms = angular.module( "sms" );

    sms.controller( "loginCtrl", function( $scope, loginService ){
        var lc = this;

          // functions
        lc.login = function(isValid) {
            if (isValid) {
                var creds = { username: $scope.username, inputPass: $scope.inputPass };
                console.log(creds);
                loginService.login( creds, function(response){
                    $scope.data = response;
                    console.log("Logged in.");
                }, function(error){
                    console.log("Error:", error.data.message);
                });
            }
        };

          // data

    });